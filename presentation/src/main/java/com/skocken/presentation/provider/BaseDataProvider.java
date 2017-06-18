package com.skocken.presentation.provider;

import com.skocken.presentation.definition.Base;

/**
 * Base of DataProvider which take care of keeping the Presenter for you
 *
 * @param <P> the Presenter class that you want to use
 */
public abstract class BaseDataProvider<P extends Base.IPresenter> implements Base.IDataProvider {

    private P mPresenter;

    @Override
    public void setPresenter(Base.IPresenter presenter) {
        mPresenter = (P) presenter;
    }

    public P getPresenter() {
        return mPresenter;
    }
}
