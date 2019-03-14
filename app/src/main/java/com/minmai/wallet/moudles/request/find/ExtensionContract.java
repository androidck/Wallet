package com.minmai.wallet.moudles.request.find;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.AppSpread;

import java.util.List;

/**
 * 获取推广数据
 */
public interface ExtensionContract {

    interface View extends BaseView<presenter>{

        void success(String msg);

        void appSpread(List<AppSpread> list);

        void fail(String msg);
    }

    interface presenter extends BasePresenter{

        void getAppSpread(String userId);
    }
}
