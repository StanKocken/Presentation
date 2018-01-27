package com.skocken.presentation.provider;

import android.support.annotation.NonNull;
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
        if(mPresenter == presenter) {
            return;
        }
        //noinspection unchecked
        mPresenter = (P) presenter;
        onPresenterChanged();
    }

    /**
     * Get the Presenter previously provided.
     *
     * Note: this method is marked as @NonNull, so if you did not provide any presenter before this
     * will crash right away in Kotlin, or later in your code in Java.
     * To be safer, you should either check the current status with {@link #isPresenterAttached()}
     * or use the @Nullable method {@link #getPresenterOrNull()}.
     *
     * @return the presenter previously provided.
     */
    @NonNull
    protected P getPresenter() {
        return mPresenter;
    }

    /**
     * Get the Presenter if previously provided.
     *
     * @return the presenter previously provided or null if not set
     */
    @Nullable
    protected P getPresenterOrNull() {
        return mPresenter;
    }

    /**
     * @return true if a Presenter has been previously set
     */
    protected boolean isPresenterAttached() {
        return mPresenter != null;
    }

    /**
     * Called whenever the Presenter has been changed
     */
    protected void onPresenterChanged() {
        // nothing by default
    }
}
