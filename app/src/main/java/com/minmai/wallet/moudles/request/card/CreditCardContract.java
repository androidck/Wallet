package com.minmai.wallet.moudles.request.card;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.CreditCardReq;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.ListBaseData;

import java.util.List;

/**
 * 信用卡接口
 */
public interface CreditCardContract {

    interface View extends BaseView<presenter>{

        void setCreditCard(List<CreditCard> list);

        void setDebitCard(List<DebitCard> list);

        void fail(String msg);

        void success(String msg);
    }

    interface presenter extends BasePresenter{

        void queryCreditCard(String userId,ListBaseData listBaseData);

        void queryDebitCard(String userId,ListBaseData listBaseData);

        void addCreditCard(String userId, CreditCardReq creditCardReq);
    }
}
