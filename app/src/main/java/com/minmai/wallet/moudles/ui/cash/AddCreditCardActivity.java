package com.minmai.wallet.moudles.ui.cash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.bumptech.glide.Glide;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.qiniu.Auth;
import com.minmai.wallet.common.uitl.FileUtil;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.TextUtil;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.common.uitl.ViewUtil;
import com.minmai.wallet.common.view.PhoneTextWatcher;
import com.minmai.wallet.common.watcher.BankCardNumAddSpaceWatcher;
import com.minmai.wallet.moudles.bean.request.CreditCardReq;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.db.BankBackGround;
import com.minmai.wallet.moudles.request.card.CreditCardContract;
import com.minmai.wallet.moudles.request.card.CreditCardPresenter;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 添加信用卡
 */
public class AddCreditCardActivity extends MyActivity implements CreditCardContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_photograph)
    ImageButton imgPhotograph;
    @BindView(R.id.tv_phone)
    ClearEditText tvPhone;
    @BindView(R.id.tv_bill_date)
    TextView tvBillDate;
    @BindView(R.id.tv_repayment_date)
    TextView tvRepaymentDate;
    @BindView(R.id.bank_img)
    ImageView bankImg;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.ed_credit_number)
    ClearEditText edCreditNumber;
    @BindView(R.id.ed_validity)
    ClearEditText edValidity;
    @BindView(R.id.ed_cvv2)
    ClearEditText edCvv2;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;
    @BindView(R.id.ly_bank_bg)
    AutoLinearLayout lyBankBg;

    private static final int REQUEST_CODE_BANKCARD = 111;
    private static String filePath = "";
    @BindView(R.id.ed_nickname)
    ClearEditText edNickname;
    private String bankImgUrl;//银行卡照片
    private CreditCardPresenter presenter;
    String bankNumber;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_card;
    }


    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tvPhone.addTextChangedListener(new PhoneTextWatcher(tvPhone));
        tbLoginTitle.setTitle("添加信用卡");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        edCreditNumber.addTextChangedListener(new BankCardNumAddSpaceWatcher(edCreditNumber));
        edCreditNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edCreditNumber.getText().toString().trim().replace(" ", "").length() == 16 || edCreditNumber.getText().toString().trim().replace(" ", "").length() == 19) {
                    getBankBackGround(edCreditNumber.getText().toString().trim().replace(" ", ""));
                }
            }
        });
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
        presenter=new CreditCardPresenter(this,this);
    }

    @OnClick({R.id.img_photograph, R.id.tv_bill_date, R.id.tv_repayment_date,R.id.btn_login_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_photograph:
                bankPhoto();
                break;
            case R.id.tv_bill_date:
                break;
            case R.id.tv_repayment_date:
                break;
            case R.id.btn_login_commit:
                startRequestInterface();
                break;
        }
    }

    //银行卡拍照识别
    public void bankPhoto() {
        File file = FileUtil.getSaveFile(getApplication(), "front");
        if (file == null) {
            toast("未检测到内存卡");
            return;
        }
        filePath = file.getAbsolutePath();
        Intent intent = new Intent(AddCreditCardActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                file.getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_BANK_CARD);
        startActivityForResult(intent, REQUEST_CODE_BANKCARD);
    }

    //获取银行卡背景颜色与logo
    public void getBankBackGround(String cardNumber) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().getBankBackgroundVo(cardNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BankBackGround>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<BankBackGround> t) throws Exception {
                        Glide.with(context).load(t.getData().getLogo()).into(bankImg);
                        lyBankBg.setBackground(ViewUtil.setDrawable(t.getData().getBackgroundColor()));
                        tvBankName.setText(t.getData().getBankName());
                        tvBankType.setText("信用卡");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError) {
                            toast("网络连接失败，请检查网络");
                        }
                    }

                    @Override
                    protected void onError(BaseEntry<BankBackGround> t) {
                        toast(t.getMsg());
                    }
                });
    }

    /**
     * 解析银行卡
     *
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
                    } else {
                        type = "不能识别";
                    }
                    if (!TextUtils.isEmpty(result.getBankCardNumber())) {
                        bankCardNumber = result.getBankCardNumber();
                        edCreditNumber.setText(bankCardNumber);
                        getBankBackGround(edCreditNumber.getText().toString().trim().replace(" ", ""));
                    }
                    if (!TextUtils.isEmpty(result.getBankName())) {
                        bankName = result.getBankName();
                        tvBankName.setText(bankName);
                    }
                    if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(bankCardNumber) &&
                            !TextUtils.isEmpty(bankName)) {
                        String str = bankName.replace(" ", "");
                        if (type.equals("借记卡") || type.equals("不能识别")) {

                        } else {

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


    /**
     * 图片上传工具类
     *
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
                    String imgUrl = Constant.QINIU_URL + key;
                    bankImgUrl = imgUrl;
                }
            }
        }, null);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，银行卡识别
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            recBankCard(filePath);
            uploadImgQiNiu(filePath);
        }
    }


    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        bankNumber= edCreditNumber.getText().toString().trim().replace(" ","");
        String validity = edValidity.getText().toString().trim();
        String cvv2 = edCvv2.getText().toString().trim();
        String phone = tvPhone.getText().toString().trim().replace(" ","");
        String nickName=edNickname.getText().toString().trim();
        String startDate=tvBillDate.getText().toString().trim();
        String repayMentDate=tvRepaymentDate.getText().toString().trim();
        if (TextUtils.isEmpty(bankNumber)) {
            toast("请输入信用卡号或扫描信用卡");
        } else if (TextUtils.isEmpty(validity)) {
            toast("请输入信用卡有效期");
        } else if (TextUtils.isEmpty(cvv2)) {
            toast("请输入信用卡签名栏后三位");
        } else if (!ValidateUtils.Mobile(phone)) {
            toast("请输入正确的手机号");
        } else {
            CreditCardReq req = new CreditCardReq();
            req.setBranch_bank(tvBankName.getText().toString().trim());
            req.setCarNumber(bankNumber);
            req.setEffectiveDate(validity);
            req.setCvv(cvv2);
            req.setPhone(phone);
            req.setNickName(nickName);
            req.setPhoto(bankImgUrl);
            req.setStatementDate(startDate);
            req.setEffectiveDate(repayMentDate);
            presenter.addCreditCard(getUserId(),req);
        }
    }

    @Override
    public void setCreditCard(List<CreditCard> list) {

    }

    @Override
    public void setDebitCard(List<DebitCard> list) {

    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void success(String msg) {
        toast(msg);
        finish();
    }
}
