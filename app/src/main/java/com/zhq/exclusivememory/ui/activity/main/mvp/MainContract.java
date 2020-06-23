package com.zhq.exclusivememory.ui.activity.main.mvp;

import com.zhq.exclusivememory.base.mvp.BaseContract;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public interface MainContract {

    interface View extends BaseContract.BaseView{

    }
    interface Presenter extends BaseContract.BasePresenter<View>{

    }
}
