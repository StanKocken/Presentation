package com.skocken.presentation.sample.presenter;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.sample.activity.FormActivity;
import com.skocken.presentation.sample.activity.FormWithFragmentActivity;
import com.skocken.presentation.sample.definition.FormDef;
import com.skocken.presentation.sample.fragment.FormDialogFragment;
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

    @Override
    public void onClickGoFormActivity() {
        Activity activity = getActivity();
        activity.startActivity(new Intent(activity, FormActivity.class));
        activity.finish();
    }

    @Override
    public void onClickGoFormFragmentActivity() {
        Activity activity = getActivity();
        activity.startActivity(new Intent(activity, FormWithFragmentActivity.class));
        activity.finish();
    }

    @Override
    public void onClickGoFormDialogFragment() {
        Activity activity = getActivity();
        if (activity instanceof FragmentActivity) {
            FragmentManager fragmentManager =
                    ((FragmentActivity) activity).getSupportFragmentManager();
            new FormDialogFragment().show(fragmentManager, "");
        }
    }

    private void refreshValueSaved() {
        String valueSaved = getProvider().getValueSaved();
        getView().setValueSaved(valueSaved);
    }
}
