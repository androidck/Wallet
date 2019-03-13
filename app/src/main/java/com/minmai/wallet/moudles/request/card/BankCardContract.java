package com.minmai.wallet.moudles.request.card;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.UserBankCardReq;
import com.minmai.wallet.moudles.bean.request.UserBankCardUpdateReq;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.CityResp;
import com.minmai.wallet.moudles.bean.response.DistBankCard;

import java.util.List;

import retrofit2.http.Field;

/**
 * 银行相关
 */
public interface BankCardContract {

    interface View extends BaseView<presenter>{

        void onSuccess(String msg);

        void fail(String msg);

        //返回总行信息
        void setBankInfo(List<BankInfo> bankInfo);

        //获取支行信息
        void setBranchInfo(List<CityResp> cityResps);

        //识别银行卡
        void setDisBank(DistBankCard bank);




    }

    interface presenter extends BasePresenter{

        //获取总行信息
        void getBankInfoVo(String userId);

        //获取支行信息
        void getBranchBankInfo(String parentId, String cityId);

        //银行卡号
        void visBankCard(String cardNo);

        //第三步信息完善
        void userBankCardBinding(String userId, UserBankCardReq userBankCardReq);

        //修改储蓄卡信息
        void modifyDefaultDebitCard(String userId, UserBankCardUpdateReq userBankCardUpdateReq);
    }
}
