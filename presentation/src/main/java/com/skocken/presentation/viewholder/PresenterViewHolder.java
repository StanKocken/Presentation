package com.skocken.presentation.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.skocken.efficientadapter.lib.viewholder.EfficientViewHolder;
import com.skocken.presentation.definition.Base;

/**
 * PresenterViewHolder is an implementation of Base.IItemView which take cares of the V of the MVP.
 * It is design to be used with the library EfficientAdapter by the same author as this library.
 *
 * @param <P> the Presenter class that you want to use
 */
public abstract class PresenterViewHolder<T, P extends Base.IItemPresenter<T>>
        extends EfficientViewHolder<T> implements Base.IItemView<T> {

    private P mPresenter;

    public PresenterViewHolder(View itemView) {
        super(itemView);
        initController();
        setupPresenter();
    }

    @Override
    public void setPresenter(@Nullable Base.IPresenter presenter) {
        if(mPresenter == presenter) {
            return;
        }
        //noinspection unchecked
        mPresenter = (P) presenter;
        onPresenterChanged();
    }

    protected void initController() {
        // nothing to do by default
    }

    @NonNull
    protected abstract Class<? extends P> getPresenterClass();
    
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

    @Override
    protected void updateView(@NonNull Context context, @Nullable T item) {
        mPresenter.updateView(context, item);
    }

    protected void setupPresenter() {
        initPresenter();
        initViewProxy();
    }

    protected void initPresenter() {
        P presenter = createPresenter(getPresenterClass());
        setPresenter(presenter);
    }

    protected void initViewProxy() {
        mPresenter.setView(this);
    }

    /**
     * Called whenever the Presenter has been changed
     */
    protected void onPresenterChanged() {
        // nothing by default
    }

    P createPresenter(Class<? extends P> modelClass) {
        //noinspection TryWithIdenticalCatches
        try {
            return modelClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
