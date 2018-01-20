package com.skocken.presentation.sample.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.skocken.presentation.activity.BaseActivity;
import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.presenter.FormPresenter;
import com.skocken.presentation.sample.viewproxy.FormViewProxy;

public class FormActivity extends BaseActivity<FormPresenter, FormViewProxy> {

    @Override
    public int getContentView() {
        return R.layout.activity_form;
    }

    @NonNull
    @Override
    public Class<FormPresenter> getPresenterClass() {
        return FormPresenter.class;
    }

    @NonNull
    @Override
    public FormViewProxy newViewProxy(FormPresenter presenter, Bundle savedInstanceState) {
        return new FormViewProxy(this);
    }
}
