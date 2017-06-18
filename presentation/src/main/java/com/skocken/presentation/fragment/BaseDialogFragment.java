package com.skocken.presentation.fragment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;

/**
 * Same as BaseFragment but for a DialogFragment
 */
public abstract class BaseDialogFragment<P extends BasePresenter, V extends Base.IView>
        extends DialogFragment implements LifecycleRegistryOwner {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private P mPresenter;

    protected abstract int getContentView();

    protected abstract Class<P> getPresenterClass();

    protected abstract V newViewProxy();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = ViewModelProviders.of(this)
                .get(getPresenterClass());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
