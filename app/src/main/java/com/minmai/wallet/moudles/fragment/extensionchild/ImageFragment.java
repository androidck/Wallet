package com.minmai.wallet.moudles.fragment.extensionchild;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.greendao.DbCenterInfoDao;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.qiniu.Auth;
import com.minmai.wallet.common.uitl.EncodingUtils;
import com.minmai.wallet.moudles.adapter.ImageShareAdapter;
import com.minmai.wallet.moudles.bean.request.AppSpread;
import com.minmai.wallet.moudles.dialog.ShareDialog;
import com.minmai.wallet.moudles.request.find.ExtensionContract;
import com.minmai.wallet.moudles.request.find.ExtensionPresenter;
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

/**
 * 图片
 */
public class ImageFragment extends MyLazyFragment implements ExtensionContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ly_share_bg)
    ImageView lyShareBg;
    @BindView(R.id.img_qrcode)
    ImageView imgQrcode;
    @BindView(R.id.img_big_logo)
    ImageView imgBigLogo;

    private ExtensionPresenter presenter;
    private DbUserInfoDao userInfoDao;
    private ImageShareAdapter adapter;

    //背景图片
    private Bitmap backBitMap;

    //底部图片
    private Bitmap fontBitMap;

    private String bigImgUrl;//大图的Url


    /**
     * 当前展示的图片信息
     */
    private AppSpread appSpread;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }


    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }


    @Override
    protected void initView() {
        tbLoginTitle.setTitle("推广");
        tbLoginTitle.setRightIcon(R.mipmap.share_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
    }

    @Override
    protected void initData() {
        userInfoDao = MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        String userId = userInfoDao.loadAll().get(0).getUserId();
        presenter = new ExtensionPresenter(getActivity(), this);
        presenter.getAppSpread(userId);
        tbLoginTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) { }

            @Override
            public void onTitleClick(View v) { }

            @Override
            public void onRightClick(View v) {
                backBitMap=((BitmapDrawable) (imgBigLogo).getDrawable()).getBitmap();
                new ShareDialog(getContext(),false).show();
                Bitmap bm=EncodingUtils.combineBitmap(backBitMap,zoomImg(fontBitMap,146,146));
                File file=EncodingUtils.compressImage(bm);
                uploadImgQiNiu(file.getAbsolutePath());
            }
        });

        //设置二维码
        imgQrcode.setImageBitmap(genRateQrCode());

        //当图片不为空的时候
        if (!(TextUtils.isEmpty(bigImgUrl))){
            Glide.with(getActivity()).load(bigImgUrl).into(imgBigLogo);
        }

    }

    public static ImageFragment newInstance() {
        return new ImageFragment();
    }


    @Override
    public void success(String msg) {

    }

    @Override
    public void appSpread(List<AppSpread> list) {
       if (list!=null){
           appSpread=list.get(0);
           appSpread.setSelect(true);
           adapter = new ImageShareAdapter(getActivity());
           recyclerView.setAdapter(adapter);
           adapter.setData(list);
           adapter.notifyDataSetChanged();
           //背景图片bit
           adapter.setOnItemClickListener(new ImageShareAdapter.OnItemClickListener() {
               @Override
               public void onItemShareClick(int position, String bigImg) {
                   bigImgUrl=bigImg;
                   Glide.with(getActivity()).load(bigImgUrl).into(imgBigLogo);
               }
           });
           //初始化设置第一张图片，在加载一次
           adapter.setOnItemOneListener(new ImageShareAdapter.OnItemOneListener() {
               @Override
               public void onOneClick(String bigImg) {
                   bigImgUrl=bigImg;
               }
           });

       }
    }


    @Override
    public void fail(String msg) {
        toast(msg);
    }


    //生成二维码
    private Bitmap genRateQrCode(){
      //  String userNo=centerInfoDao.loadAll().get(0).getUserNo();
        fontBitMap=EncodingUtils.createQRCode("http://www.baidu.com", 750, 750,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        return fontBitMap;
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
                    Log.d("imgurl",imgUrl);
                }
            }
        }, null);

    }






    // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        backBitMap.recycle();

    }
}
