package com.minmai.wallet.moudles.request.card;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.CityResp;

import java.util.List;

/**
 * 银行相关
 */
public interface BankCardContract {

    interface View extends BaseView<presenter>{

        void onSuccess(String msg);

        void fail(String msg);

        //返回总行信息
        void setBankInfo(List<BankInfo> bankInfo);

        void setProvince(List<CityResp> list);

        void setCity(List<CityResp> list);

        void setArea(List<CityResp> list);
    }

    interface presenter extends BasePresenter{

        //获取总行信息
        void getBankInfoVo(String userId);

        //获取省份
        void getProvince(String parentId);

        //获取城市
        void getCity(String parentId);

        //获取区
        void getArea(String parentId);
    }
}
