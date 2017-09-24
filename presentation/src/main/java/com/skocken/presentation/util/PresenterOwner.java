package com.skocken.presentation.util;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;

/**
 * Keep a reference on the Presenter and:
 * - retrieve an existing Presenter thanks to ViewModel
 * <p>
 * Override this class if you want to do something specific while creating the Presenter or the ViewProxy.
 */
public class PresenterOwner<P extends BasePresenter, V extends Base.IView> {

    private final ViewModelProvider mViewModelProvider;
    private final Provider<P, V> mProvider;

    private P mPresenter;

    public <A extends AppCompatActivity & Provider<P, V>> PresenterOwner(@NonNull A provider) {
        this(ViewModelProviders.of(provider), provider);
    }

    public <F extends Fragment & Provider<P, V>> PresenterOwner(@NonNull F provider) {
        this(ViewModelProviders.of(provider), provider);
    }

    public PresenterOwner(@NonNull ViewModelProvider viewModelProvider,
                          @NonNull Provider<P, V> provider) {
        mViewModelProvider = viewModelProvider;
        mProvider = provider;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void createPresenter() {
        mPresenter = mViewModelProvider.get(mProvider.getPresenterClass());
    }

    public void initViewProxy(Bundle savedInstanceState) {
        V viewProxy = mProvider.newViewProxy(mPresenter, savedInstanceState);
        mPresenter.setView(viewProxy);
    }

    public interface Provider<P extends BasePresenter, V extends Base.IView> {
        int getContentView();

        Class<P> getPresenterClass();

        V newViewProxy(P presenter, Bundle savedInstanceState);
    }


}
