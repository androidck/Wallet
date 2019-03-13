package com.minmai.wallet.moudles.ui.savings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.qiniu.Auth;
import com.minmai.wallet.common.uitl.FileUtil;
import com.minmai.wallet.common.uitl.TextUtil;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.moudles.bean.request.UserBankCardUpdateReq;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.CityResp;
import com.minmai.wallet.moudles.bean.response.DistBankCard;
import com.minmai.wallet.moudles.db.DbBankInfo;
import com.minmai.wallet.moudles.request.card.BankCardContract;
import com.minmai.wallet.moudles.request.card.BankCardPresenter;
import com.minmai.wallet.moudles.ui.identity.IdentifyThreeActivity;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加储蓄卡
 */
public class AddSavingCardActivity extends MyActivity implements BankCardContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_bank_card)
    ImageView imgBankCard;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.bank_number)
    ClearEditText bankNumber;
    @BindView(R.id.tv_open_member)
    TextView tvOpenMember;
    @BindView(R.id.tv_open_address)
    TextView tvOpenAddress;
    @BindView(R.id.ed_phone)
    ClearEditText edPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private String areaCode;//市区代码
    private String branchId;//支行代码
    private String bankNo;//银行卡号
    private String phone;//预留手机号
    private String bankImgUrl;//银行卡照片

    private BankCardPresenter presenter;
    private int visibleItems = 5;//显示item 的数量
    private boolean isProvinceCyclic = true;//是否显示省份
    private boolean isCityCyclic = true;//是否显示城市
    private boolean isDistrictCyclic = true;//是否显示地区
    private boolean isShowBg = true;//是否展示背景颜色
    private boolean isShowGAT = true;

    private static final int REQUEST_CODE_BANKCARD = 111;
    private static String filePath = "";

    private ArrayList<String> options1Items=new ArrayList<>();
    ArrayList<String> branchName=new ArrayList<>();


    public CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY_DIS;

    CityPickerView mCityPickerView = new CityPickerView();

    String openMember;
    String openAddress;
    String cardId;
    String isDefault;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_saving;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("变更储蓄卡");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void initData() {
        getOcrSing();
        presenter=new BankCardPresenter(this,this);
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
                        toast(msg);
                    }
                });
        //初始化三级联动
        mCityPickerView.init(this);

        //获取总行信息
        getBankInfo();

        cardId=this.getIntent().getStringExtra("cardId");
        isDefault=this.getIntent().getStringExtra("isDefault");
    }


    //提交储蓄卡资料
    private void changeDebitCard(){
        UserBankCardUpdateReq req=new UserBankCardUpdateReq();
        //原来卡的id
        req.setOldDebitCardId(cardId);
        req.setUserId(getUserId());
        req.setAreaCode(areaCode);
        req.setBankId(branchId);//支行id
        req.setCarNumber(bankNo.replace(" ",""));//银行卡号
        req.setIsDefault("1");
        req.setOpenBank(tvOpenMember.getText().toString().trim());//支行名称
        req.setPhone(phone);
        req.setPhoto(bankImgUrl);
        req.setIsDefault(isDefault);
        presenter.modifyDefaultDebitCard(getUserId(),req);

    }

    @OnClick({R.id.img_bank_card, R.id.tv_open_address,R.id.tv_open_member,  R.id.btn_commit})
    public void onViewClicked(View view) {
        bankNo=bankNumber.getText().toString().trim();
        switch (view.getId()) {
            case R.id.img_bank_card:
                bankPhoto();
                break;
            case R.id.tv_open_address:
                if (TextUtils.isEmpty(bankNumber.getText().toString().trim())){
                    toast("请输入银行卡号或扫描银行卡");
                }else {
                    selectAddress();
                }
                break;
            case R.id.tv_open_member:
                if (TextUtils.isEmpty(bankNo)){
                    toast("请扫描或输入银行卡号");
                }else {
                    String bankNo=bankNumber.getText().toString().trim().replace(" ","");
                    presenter.visBankCard(bankNo);
                }
                break;
            case R.id.btn_commit:
                startRequestInterface();
                break;
        }
    }


    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        bankNo=bankNumber.getText().toString().trim();
        openMember=tvOpenMember.getText().toString().trim();
        openAddress=tvOpenAddress.getText().toString().trim();
        phone=edPhone.getText().toString().trim().replace(" ","");
        if (TextUtils.isEmpty(bankNo)){
            toast("请输入银行卡号或扫描银行卡");
        }else if (TextUtils.isEmpty(openAddress)){
            toast("请选择开户地区");
        }else if (TextUtils.isEmpty(openMember)){
            toast("请选择开户行");
        }else if (TextUtils.isEmpty(phone)){
            toast("请输入银行卡预留手机号");
        }else if (!ValidateUtils.Mobile(phone)){
            toast("手机号格式不正确");
        }else{
            changeDebitCard();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，银行卡识别
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            recBankCard(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imgBankCard.setImageBitmap(bitmap);
            uploadImgQiNiu(filePath);
        }
    }

    //城市三级联动
    private void selectAddress() {
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//城市选择
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#323232")
                .cancelTextColor("#323232")
                .visibleItemsCount(visibleItems)
                .province("山东省")//默认省份
                .city("济南市")//默认城市
                .district("历下区")//默认地区
                .provinceCyclic(isProvinceCyclic)
                .cityCyclic(isCityCyclic)
                .districtCyclic(isDistrictCyclic)
                .setCityWheelType(mWheelType)
                .setShowGAT(isShowGAT)
                .showBackground(isShowBg)
                .build();
        mCityPickerView.setConfig(cityConfig);

        //监听方法，获取选择结果
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                sb.append("选择的结果：\n");
                if (province != null) {
                    sb.append(province.getName() + " " + province.getId() + "\n");
                }

                if (city != null) {
                    sb.append(city.getName() + " " + city.getId() + ("\n"));
                }

                if (district != null) {
                    sb.append(district.getName() + " " + district.getId() + ("\n"));
                }

                areaCode=city.getId();
                tvOpenAddress.setText(province.getName()+ " " +city.getName() + " " +district.getName());

            }

            @Override
            public void onCancel() {

            }
        });
        mCityPickerView.showCityPicker();
    }


    //银行卡拍照识别
    public void bankPhoto(){
        File file = FileUtil.getSaveFile(getApplication(),"front");
        if (file == null){
            toast("未检测到内存卡");
            return;
        }
        filePath = file.getAbsolutePath();
        Intent intent = new Intent(AddSavingCardActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                file.getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_BANK_CARD);
        startActivityForResult(intent, REQUEST_CODE_BANKCARD);
    }

    /**
     * 解析银行卡
     * @param filePath
     */
    private void recBankCard(final String filePath) {
        // 调用银行卡识别服务
        //解析银行卡图片
        BankCardParams param = new BankCardParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeBankCard(param, new OnResultListener<BankCardResult>() {
            @Override
            public void onResult(BankCardResult result) {
                if (result != null) {
                    String type = null;
                    String bankCardNumber = null;
                    String bankName = null;
                    if (result.getBankCardType() == BankCardResult.BankCardType.Credit) {
                        type = "信用卡";
                    } else if (result.getBankCardType() == BankCardResult.BankCardType.Debit) {
                        type = "借记卡";
                    }else {
                        type = "不能识别";
                    }
                    if (!TextUtils.isEmpty(result.getBankCardNumber())) {
                        bankCardNumber = result.getBankCardNumber();
                        bankNumber.setText(bankCardNumber);
                    }
                    if (!TextUtils.isEmpty(result.getBankName())) {
                        bankName = result.getBankName();
                        tvBankName.setText(bankName);
                    }
                    if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(bankCardNumber) &&
                            !TextUtils.isEmpty(bankName)) {
                        String str=bankName.replace(" ","");
                        if (type.equals("借记卡")||type.equals("不能识别")){

                        }else {

                        }
                    }
                } else {
                    toast("获取银行卡信息失败，请重新扫描");
                }
            }

            @Override
            public void onError(OCRError error) {
                int errorCode = error.getErrorCode();
                toast("获取银行卡信息失败，请重新扫描，错误码:" + errorCode);
            }
        });


    }

    //获取总行数据
    public void getBankInfo(){
        if (isExistenceBankInfo()==false){
            presenter.getBankInfoVo(getUserId());
        }
    }

    @Override
    public void onSuccess(String msg) {
        toast(msg);
        finishResult(200);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setBankInfo(List<BankInfo> bankInfo) {
        List<DbBankInfo> list=new ArrayList<>();
        for (int i=0;i<bankInfo.size();i++){
            DbBankInfo dbBankInfo=new DbBankInfo();
            dbBankInfo.setId(null);
            dbBankInfo.setBankId(bankInfo.get(i).getId());
            dbBankInfo.setBankName(bankInfo.get(i).getBankName());
            dbBankInfo.setBankShortName(bankInfo.get(i).getBankShortName());
            list.add(dbBankInfo);
        }
        bankInfoDao.insertInTx(list);
    }

    @Override
    public void setBranchInfo(List<CityResp> cityResps) {
        options1Items.clear();
        for (int i=0;i<cityResps.size();i++){
            options1Items.add(cityResps.get(i).getName()+","+cityResps.get(i).getId());
        }
        showPickerView();
    }

    @Override
    public void setDisBank(DistBankCard bank) {
        String bankId=getBankInfoAbbreviation(bank.getBank());
        presenter.getBranchBankInfo(bankId,areaCode);
    }


    //弹出窗
    private void showPickerView() {// 弹出选择器
        for (int i=0;i<options1Items.size();i++){
            String [] name=options1Items.get(i).split(",");
            branchName.add(name[0]);
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String [] branch=options1Items.get(options1).split(",");
                tvOpenMember.setText(branch[0]);
                branchId=branch[1];
            }
        })
                .setTitleText("支行选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(branchName);//一级选择器
        pvOptions.show();
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
                    String imgUrl  = Constant.QINIU_URL + key;
                    bankImgUrl=imgUrl;
                }
            }
        }, null);

    }
}
