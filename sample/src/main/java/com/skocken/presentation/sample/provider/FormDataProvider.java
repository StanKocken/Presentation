package com.skocken.presentation.sample.provider;

import com.skocken.presentation.provider.BaseDataProvider;
import com.skocken.presentation.sample.definition.FormDef;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FormDataProvider
        extends BaseDataProvider<FormDef.IPresenter>
        implements FormDef.IDataProvider {

    private static final String PREF_SAVED_VALUE = "_pref_saved_value";

    @Override
    public String getValueSaved() {
        return getSharedPreferences().getString(PREF_SAVED_VALUE, null);
    }

    @Override
    public void saveValue(String value) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SAVED_VALUE, value);
        editor.apply();
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getPresenter().getContext());
    }
}
