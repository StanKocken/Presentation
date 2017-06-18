package com.skocken.presentation.sample.fragment;

import com.skocken.presentation.fragment.BaseFragment;
import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.presenter.FormPresenter;
import com.skocken.presentation.sample.viewproxy.FormViewProxy;

public class FormFragment extends BaseFragment<FormPresenter, FormViewProxy> {

    @Override
    protected int getContentView() {
        return R.layout.include_form;
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
