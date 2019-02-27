package com.minmai.wallet.moudles.request.find;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;

import java.math.BigDecimal;
import java.util.List;

public interface FoundPageContract {

    interface View extends BaseView<presenter>{
        //设置内容
        void setContent(Object o);

        void fail(String msg);
    }

    interface presenter extends BasePresenter{
        void getFoundPageAllInitInfo(String userId);
    }
}
