package com.minmai.wallet.moudles.ui.identity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.uitl.FileUtil;
import com.minmai.wallet.common.uitl.TextUtil;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.common.view.PhoneTextWatcher;
import com.minmai.wallet.common.watcher.BankCardNumAddSpaceWatcher;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.CityResp;
import com.minmai.wallet.moudles.request.banner.BannerPresenter;
import com.minmai.wallet.moudles.request.card.BankCardContract;
import com.minmai.wallet.moudles.request.card.BankCardPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完善信息第三部
 */
public class IdentifyThreeActivity extends MyActivity implements BankCardContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.btn_commit)
    Button btnCommit;
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

    private String bankImgUrl;//银行卡照片url

    private String bankNo;//银行卡号
    private String openMember;//开户行
    private String openAddress;//开户地区
    private String phone;//预留手机号

    private static final int REQUEST_CODE_BANKCARD = 111;
    private static String filePath = "";

    private BankCardPresenter presenter;

    private Thread thread;

    private ArrayList<String> options1Items=new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items=new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
    ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
    ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

    //省份数据

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identify_three;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("完善信息");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        edPhone.addTextChangedListener(new PhoneTextWatcher(edPhone));
        bankNumber.addTextChangedListener(new BankCardNumAddSpaceWatcher(bankNumber));
    }

    @Override
    protected void initData() {
        getOcrSing();
        //  初始化本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
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
                    }
                });
        presenter=new BankCardPresenter(this,this);

        //获取省份数据
        getProvince();


    }

    @OnClick({R.id.img_bank_card, R.id.tv_open_member, R.id.tv_open_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bank_card:
                bankPhoto();
                break;
            case R.id.tv_open_member:

                break;
            case R.id.tv_open_address:
                showPickerView();
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

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，银行卡识别
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            recBankCard(filePath);
        }
    }

    //银行卡拍照识别
    public void bankPhoto(){
        File file = FileUtil.getSaveFile(getApplication(),"front");
        if (file == null){
            toast("未检测到内存卡");
            return;
        }
        filePath = file.getAbsolutePath();
        Intent intent = new Intent(IdentifyThreeActivity.this, CameraActivity.class);
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
                    }
                    if (!TextUtils.isEmpty(result.getBankName())) {
                        bankName = result.getBankName();
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

    @Override
    public void onSuccess(String msg) {

    }

    //获取省份
    public void getProvince(){
        presenter.getProvince("0");
    }

    //获取城市
    public void getCity(String fatherId){
        presenter.getCity(fatherId);
    }

    //获取区
    private void getArea(String fatherId){
        presenter.getArea(fatherId);
    }

    @Override
    public void fail(String msg) {

    }

    @Override
    public void setBankInfo(List<BankInfo> bankInfo) {

    }

    @Override
    public void setProvince(List<CityResp> list) {
        for (int i=0;i<list.size();i++){
            getCity(list.get(i).getId());
            options1Items.add(list.get(i).getName());
        }
    }

    //城市
    @Override
    public void setCity(List<CityResp> list) {
        for (int i=0;i<list.size();i++){
            cityList.add(list.get(i).getName());
            getArea(list.get(i).getId());
        }
        options2Items.add(cityList);
    }

    //城市数据
    @Override
    public void setArea(List<CityResp> list) {
        for (int i=0;i<list.size();i++){
            city_AreaList.add(list.get(i).getName());
        }
        province_AreaList.add(cityList);
        options3Items.add(province_AreaList);
    }

    //弹出窗
    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1): "";
             /*   String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";*/

                String tx = opt1tx;
             /*   Toast.makeText(JsonDataActivity.this, tx, Toast.LENGTH_SHORT).show();*/
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        //pvOptions.setPicker(provinceList);//一级选择器
        //  pvOptions.setPicker(options1Items, options2Items);
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }



}


