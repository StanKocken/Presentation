package com.skocken.presentation.activity;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity
        <P extends BasePresenter, V extends Base.IView>
        extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private P mPresenter;

    protected abstract int getContentView();

    protected abstract Class<P> getPresenterClass();

    protected abstract V newViewProxy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mPresenter = ViewModelProviders.of(this)
                .get(getPresenterClass());
        initPresenter(mPresenter, savedInstanceState);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected void initPresenter(P presenter, Bundle savedInstanceState) {
        // set the ViewProxy
        presenter.setView(newViewProxy());
    }
}
