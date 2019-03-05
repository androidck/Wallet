package com.minmai.wallet.moudles.ui.identity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.permission.Permission;
import com.minmai.wallet.common.qiniu.Auth;
import com.minmai.wallet.common.uitl.PhotoUtils;
import com.minmai.wallet.common.uitl.TextUtil;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完善信息第二部
 */
public class IdentifyTwoActivity extends MyActivity implements IdentifyContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_hold)
    ImageView imgHold;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private String imgUrl;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;


    private IdentifyPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_identify_two;
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
        presenter=new IdentifyPresenter(this,this);
    }

    @OnClick({R.id.img_hold, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_hold:
                requestFilePermission();
                break;
            case R.id.btn_commit:
                startRequestInterface();
                break;
        }
    }

    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        if (TextUtils.isEmpty(imgUrl)){
            toast("请拍摄手持身份证照片");
        }else {
            IdentfiyOneReq identfiyOneReq=new IdentfiyOneReq();
            identfiyOneReq.setHandIdCard(imgUrl);
            presenter.userRealNameAuthenticationTwo(getUserId(),identfiyOneReq);
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
                        goCamera();
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            toast("没有相机权限，请手动授予权限");
                            XXPermissions.gotoPermissionSettings(IdentifyTwoActivity.this, true);
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

    /**
     * 图片上传工具类
     * @param filePath
     */
    public void uploadImgQiNiu(String filePath) {
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
                    imgUrl  = Constant.QINIU_URL + key;
                    Log.d("imgUrl",imgUrl);
                    Glide.with(IdentifyTwoActivity.this).load(imgUrl).into(imgHold);
                }
            }
        }, null);
    }


    //激活相机操作
    private void goCamera() {
        imageUri = Uri.fromFile(fileUri);
        //通过FileProvider创建一个content类型的Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.minmai.wallet.fileprovider", fileUri);
        }
        //调用系统相机
        Intent intentCamera = new Intent();
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //相机返回
            case CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(fileCropUri);
                cropImageUri(imageUri);
                break;
            //相册返回
            //裁剪返回
            case CODE_RESULT_REQUEST:
                if (fileCropUri != null) {
                    uploadImgQiNiu(fileCropUri.getPath());
                }
                break;
        }
    }

    /**
     * @param orgUri 剪裁原图的Uri
     */
    public void cropImageUri(Uri orgUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        //发送裁剪信号
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 190);
        intent.putExtra("outputY", 260);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        //1-false用uri返回图片
        //2-true直接用bitmap返回图片（此种只适用于小图片，返回图片过大会报错）
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void setIdentify(IdentityAuth identify) {

    }

    @Override
    public void setDebitCard(DebitCard debitCard) {

    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void onSuccess(QuickPayResp quickPayResp) {

    }

    @Override
    public void success(String msg) {
        toast(msg);
        modifyStatus(getUserId(),3);
        startActivityFinish(IdentifyThreeActivity.class);
    }
}
