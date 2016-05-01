package com.skocken.presentation.viewholder;

import com.skocken.efficientadapter.lib.viewholder.EfficientViewHolder;
import com.skocken.presentation.definition.Base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class PresenterViewHolder<T, P extends Base.IItemPresenter<T>>
        extends EfficientViewHolder<T> implements Base.IView {

    private P mPresenter;

    public PresenterViewHolder(View itemView) {
        super(itemView);
        initController();
        setPresenter(createBaseItemPresenter());
    }

    @Override
    public void setPresenter(Base.IPresenter presenter) {
        mPresenter = (P) presenter;
    }

    protected void initController() {
        // nothing to do by default
    }

    @NonNull
    protected abstract P createBaseItemPresenter();

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void updateView(Context context, T object) {
        mPresenter.updateView(context, object);
    }

}
