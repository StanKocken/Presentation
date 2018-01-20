package com.skocken.presentation.provider;

import android.support.annotation.Nullable;

import com.skocken.presentation.definition.Base;

/**
 * Base of DataProvider which take care of keeping the Presenter for you
 *
 * @param <P> the Presenter class that you want to use
 */
public abstract class BaseDataProvider<P extends Base.IPresenter> implements Base.IDataProvider {

    private P mPresenter;

    @Override
    public void setPresenter(@Nullable Base.IPresenter presenter) {
        //noinspection unchecked
        mPresenter = (P) presenter;
    }

    @Nullable
    public P getPresenter() {
        return mPresenter;
    }
}
