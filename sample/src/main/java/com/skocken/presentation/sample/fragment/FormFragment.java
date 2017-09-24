package com.skocken.presentation.sample.fragment;

import android.os.Bundle;

import com.skocken.presentation.fragment.BaseFragment;
import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.presenter.FormPresenter;
import com.skocken.presentation.sample.viewproxy.FormViewProxy;

public class FormFragment extends BaseFragment<FormPresenter, FormViewProxy> {

    @Override
    public int getContentView() {
        return R.layout.include_form;
    }

    @Override
    public Class<FormPresenter> getPresenterClass() {
        return FormPresenter.class;
    }

    @Override
    public FormViewProxy newViewProxy(FormPresenter presenter, Bundle savedInstanceState) {
        return new FormViewProxy(this);
    }

}
