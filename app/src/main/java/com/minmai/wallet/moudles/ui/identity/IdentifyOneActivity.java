package com.minmai.wallet.moudles.ui.identity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.dialog.ProgressHUD;
import com.minmai.wallet.common.permission.Permission;
import com.minmai.wallet.common.qiniu.Auth;
import com.minmai.wallet.common.uitl.FileUtil;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.moudles.bean.request.IdentfiyOneReq;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.QuickPayResp;
import com.minmai.wallet.moudles.request.user.IdentifyContract;
import com.minmai.wallet.moudles.request.user.IdentifyPresenter;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完善信息第一步
 */
public class IdentifyOneActivity extends MyActivity implements IdentifyContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_font)
    ImageView imgFont;//身份证正面
    @BindView(R.id.ed_real_name)
    ClearEditText edRealName;//真实姓名
    @BindView(R.id.ed_id_no)
    ClearEditText edIdNo;//身份证号
    @BindView(R.id.img_back)
    ImageView imgBack;//身份证反面
    @BindView(R.id.ed_validity)
    ClearEditText edValidity;//
    @BindView(R.id.btn_next)
    Button btnNext;//提交

    private static final int REQUEST_CODE_CAMERA = 102;
    private static String filePath = "";

    public int type;

    private String name;//姓名
    private String nation;//名族
    private String num ;//身份证号
    private String sex;//性别
    private String address;//详细地址
    private String birthday;//出生年月日
    private String expiration_date;//签发机关
    private String validDate;//有效期
    private String fontUrl;//身份证正面照片
    private String backUrl;//身份证反面照片

    private IdentifyPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("完善信息");
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {
        getOcrSing();
        //百度身份证识别初始化
        CameraNativeHelper.init(this, OCR.getInstance().getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        toast("本地质量控制初始化错误，错误原因："+msg);
                    }
                });
        presenter=new IdentifyPresenter(this,this);
    }

    @OnClick({R.id.img_font, R.id.img_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_font:
                type=1;
                requestFilePermission();
                break;
            case R.id.img_back:
                type=2;
                requestFilePermission();
                break;
            case R.id.btn_next:
                startRequestInterface();
                break;
        }
    }

    //请求相机权限
    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        //调用相机权限
                        if (type==1){
                            photoFont();
                        }else if (type==2){
                            photoBack();
                        }
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            toast("没有相机权限，请手动授予权限");
                            XXPermissions.gotoPermissionSettings(IdentifyOneActivity.this, true);
                        } else {
                            toast("没有相机权限，请手动授予权限");
                            getWindow().getDecorView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    requestFilePermission();
                                }
                            }, 2000);
                        }
                    }
                });
    }
    //正面拍照
    public void photoFont(){
        File file = FileUtil.getSaveFile(getApplication(),"front");
        if (file == null){
            toast("未检测到内存卡");
            return;
        }
        filePath = file.getAbsolutePath();
        Intent intent = new Intent(IdentifyOneActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,filePath
        );
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    //反面拍照
    public void photoBack(){
        File file = FileUtil.getSaveFile(getApplication(),"front");
        if (file == null){
            toast("未检测到内存卡");
            return;
        }
        Intent intent = new Intent(IdentifyOneActivity.this, CameraActivity.class);
        filePath = file.getAbsolutePath();
        intent = new Intent(IdentifyOneActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                filePath);
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraNativeHelper.release();
    }
    /**
     * 上传二维码信息
     * @param idCardSideFront
     * @param filePath
     */
    private void recIDCard(final String idCardSideFront, final String filePath) {
        //调用骑牛上传图片
        //解析身份证图片 idCardSide:身份证正反面  filePath:图片路径
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSideFront);
        // 设置方向检测
        param.setDetectDirection(true);
        param.setImageQuality(20);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if (idCardSideFront.equals(IDCardParams.ID_CARD_SIDE_BACK)) {
                        String sing_data = "";//登记日期
                        String expiry_data = "";//截止日期
                        if (result.getIssueAuthority() != null) {
                            expiration_date = result.getIssueAuthority().toString();
                        }
                        if (result.getSignDate() != null) {
                            sing_data = result.getSignDate().toString();
                        }
                        if (result.getExpiryDate() != null) {
                            expiry_data = result.getExpiryDate().toString();
                        }
                        validDate=sing_data + expiry_data;
                        if (!TextUtils.isEmpty(expiration_date) && !TextUtils.isEmpty(sing_data) &&
                                !TextUtils.isEmpty(expiry_data)) {
                            edValidity.setText(sing_data + "-"+expiry_data);
                           // uploadImgBackQiNiuFont(mContext,filePath);
                            if (progressHUD!=null){
                                progressHUD.dismiss();
                                progressHUD=null;
                            }
                        }
                    } else {
                        if (result.getName() != null) {
                            name = result.getName().toString();
                        }
                        if (result.getGender() != null) {
                            sex = result.getGender().toString();
                        }
                        if (result.getEthnic() != null) {
                            nation = result.getEthnic().toString();
                        }
                        if (result.getIdNumber() != null) {
                            //身份证号
                            num = result.getIdNumber().toString();
                        }
                        if (result.getAddress() != null) {
                            //详细地址
                            address = result.getAddress().toString();
                        }
                        if (result.getBirthday() != null) {
                            //出生年月日
                            birthday = result.getBirthday().toString();
                        }
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(nation) &&
                                !TextUtils.isEmpty(num) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(birthday)) {
                            edRealName.setText(name);//姓名
                            edIdNo.setText(num);//身份证号
                            if (progressHUD!=null){
                                progressHUD.dismiss();
                                progressHUD=null;
                            }
                        }
                    }
                }
            }
            @Override
            public void onError(OCRError error) {
                int errorCode = error.getErrorCode();
                toast("获取身份证信息失败，请重新扫描，错误码:" + errorCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        imgFont.setImageBitmap(bitmap);
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        progressHUD = ProgressHUD.show(context);
                        if (progressHUD!=null){
                            progressHUD.setLabel(MainUtil.loadDistinguish);
                        }
                        uploadImgQiNiu("font",filePath);

                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        imgBack.setImageBitmap(bitmap);
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                        progressHUD = ProgressHUD.show(context);
                        if (progressHUD!=null){
                            progressHUD.setLabel(MainUtil.loadDistinguish);
                        }
                        uploadImgQiNiu("back",filePath);
                    }
                }
            }
        }
    }

    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        if (TextUtils.isEmpty(name)&&TextUtils.isEmpty(num)){
            toast("请识别身份证正面");
        }else if (TextUtils.isEmpty(validDate)){
            toast("请识别身份证反面");
        }else if (!ValidateUtils.IDcard(num)){
            toast("身份证号有误，请重新识别或手动填写");
        }else {
            commitData();
        }
    }

    /**
     * 图片上传工具类
     * @param filePath
     */
    public void uploadImgQiNiu(final String type, String filePath) {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
                .zone(FixedZone.zone0) // 设置区域，指默认 Zone.zone0 <span style="font-size:14px;"><strong><span style="color:#FF0000;">注：这步是最关键的 当初错的主要原因也是他 根据自己的地方选</span></strong></span>
                .build();
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date()) + ".png";
        String picPath = filePath;
        uploadManager.put(picPath, key, Auth.create(Constant.QINIU_AK, Constant.QINIU_SK).uploadToken(Constant.QINIU_BUCKET), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if (info.isOK()) {
                    String imgUrl  = Constant.QINIU_URL + key;
                    if ("font".equals(type)){
                        fontUrl=imgUrl;
                        Log.d("fontUrl",fontUrl);
                    }else if ("back".equals(type)){
                        backUrl=imgUrl;
                        Log.d("backUrl",backUrl);
                    }
                }
            }
        }, null);

    }
    @Override
    public void setIdentify(IdentityAuth identify) {}
    @Override
    public void setDebitCard(DebitCard debitCard) {}
    @Override
    public void fail(String msg) {
        toast(msg);
    }
    @Override
    public void onSuccess(QuickPayResp quickPayResp) {}
    @Override
    public void success(String msg) {
        toast(msg);
        modifyStatus(getUserId(),2);
        startActivityFinish(IdentifyTwoActivity.class);
    }

    //提交资料
    public void commitData(){
        IdentfiyOneReq identfiyOneReq=new IdentfiyOneReq();
        identfiyOneReq.setUserId(getUserId());
        identfiyOneReq.setRealName(name);
        identfiyOneReq.setIdCard(num);
        identfiyOneReq.setCardFrontPic(fontUrl);
        identfiyOneReq.setCardBackPic(backUrl);
        identfiyOneReq.setDetailedAddress(address);
        identfiyOneReq.setEffectiveDate(validDate);
        identfiyOneReq.setNation(nation);
        presenter.userRealNameAuthenticationOne(getUserId(),identfiyOneReq);
    }
}
