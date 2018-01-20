package com.skocken.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.util.PresenterOwner;

/**
 * Same as BaseFragment but for a DialogFragment
 */
public abstract class BaseDialogFragment<P extends BasePresenter, V extends Base.IView>
        extends DialogFragment
        implements PresenterOwner.Provider<P, V> {

    PresenterOwner<P, V> mPresenterOwner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenterOwner == null) {
            mPresenterOwner = new PresenterOwner<>(this);
        }
        onCreateDelegate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedDelegate(savedInstanceState);
    }

    @Nullable
    public P getPresenter() {
        return mPresenterOwner.getPresenter();
    }

    void onCreateDelegate() {
        mPresenterOwner.createPresenter();
    }

    void onViewCreatedDelegate(@Nullable Bundle savedInstanceState) {
        mPresenterOwner.initViewProxy(savedInstanceState);
    }
}
