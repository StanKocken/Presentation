package com.skocken.presentation.sample.presenter;

import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.sample.definition.FormDef;

import android.os.Bundle;

public class FormPresenter
        extends BasePresenter<FormDef.IDataProvider, FormDef.IView>
        implements FormDef.IPresenter {

    public FormPresenter(FormDef.IDataProvider provider, FormDef.IView view) {
        super(provider, view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        refreshValueSaved();
    }

    @Override
    public void onClickSaveButton(String value) {
        getProvider().saveValue(value);
        refreshValueSaved();
    }

    private void refreshValueSaved() {
        String valueSaved = getProvider().getValueSaved();
        getView().setValueSaved(valueSaved);
    }
}
