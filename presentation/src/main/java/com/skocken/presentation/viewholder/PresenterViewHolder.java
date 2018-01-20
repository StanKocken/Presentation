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
        //noinspection unchecked
        mPresenter = (P) presenter;
    }

    protected void initController() {
        // nothing to do by default
    }

    @NonNull
    protected abstract Class<? extends P> getPresenterClass();

    @Nullable
    protected P getPresenter() {
        return mPresenter;
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
