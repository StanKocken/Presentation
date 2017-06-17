package com.skocken.presentation.sample.presenter;

import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.sample.definition.FormDef;
import com.skocken.presentation.sample.provider.FormDataProvider;

public class FormPresenter
        extends BasePresenter<FormDef.IDataProvider, FormDef.IView>
        implements FormDef.IPresenter {

    public FormPresenter() {
        super();
        setProvider(new FormDataProvider());
    }

    @Override
    protected void onViewChanged() {
        super.onViewChanged();
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
