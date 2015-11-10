package com.skocken.presentation.sample.activity;

import com.skocken.presentation.activity.BaseAppCompatActivity;
import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.definition.FormDef;
import com.skocken.presentation.sample.presenter.FormPresenter;
import com.skocken.presentation.sample.provider.FormDataProvider;
import com.skocken.presentation.sample.viewproxy.FormViewProxy;

public class FormActivity extends BaseAppCompatActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_form;
    }

    @Override
    protected BasePresenter newPresenter() {
        FormDef.IDataProvider provider = new FormDataProvider();
        FormDef.IView view = new FormViewProxy(this);
        return new FormPresenter(provider, view);
    }
}
