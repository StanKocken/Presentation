package com.skocken.presentation.sample.activity;

import com.skocken.presentation.activity.BaseActivity;
import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.presenter.FormPresenter;
import com.skocken.presentation.sample.viewproxy.FormViewProxy;

public class FormActivity extends BaseActivity<FormPresenter, FormViewProxy> {

    @Override
    protected int getContentView() {
        return R.layout.activity_form;
    }

    @Override
    protected Class<FormPresenter> getPresenterClass() {
        return FormPresenter.class;
    }

    @Override
    protected FormViewProxy newViewProxy() {
        return new FormViewProxy(this);
    }
}
