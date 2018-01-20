package com.skocken.presentation.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.skocken.presentation.fragment.BaseDialogFragment;
import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.presenter.FormPresenter;
import com.skocken.presentation.sample.viewproxy.FormViewProxy;

public class FormDialogFragment extends BaseDialogFragment<FormPresenter, FormViewProxy> {

    @Override
    public int getContentView() {
        return R.layout.include_form;
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
