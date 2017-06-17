package com.skocken.presentation.viewholder;

import android.content.Context;
import android.view.View;

import com.skocken.efficientadapter.lib.viewholder.EfficientViewHolder;
import com.skocken.presentation.definition.Base;

public abstract class PresenterViewHolder<T, P extends Base.IItemPresenter<T>>
        extends EfficientViewHolder<T> implements Base.IItemView<T> {

    private P mPresenter;

    public PresenterViewHolder(View itemView) {
        super(itemView);
        initController();
        createItemPresenter();
    }

    @Override
    public void setPresenter(Base.IPresenter presenter) {
        mPresenter = (P) presenter;
    }

    protected void initController() {
        // nothing to do by default
    }

    protected abstract Class<P> getPresenterClass();

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void updateView(Context context, T object) {
        mPresenter.updateView(context, object);
    }

    private void createItemPresenter() {
        P presenter;
        try {
            presenter = getPresenterClass().newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("");
        }
        presenter.setView(this);
        setPresenter(presenter);
    }
}
