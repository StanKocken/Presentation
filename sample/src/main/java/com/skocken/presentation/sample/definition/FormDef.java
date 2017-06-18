package com.skocken.presentation.sample.definition;

import com.skocken.presentation.definition.Base;

public interface FormDef {

    interface IPresenter extends Base.IPresenter {

        void onClickSaveButton(String value);

        void onClickGoFormActivity();

        void onClickGoFormFragmentActivity();

        void onClickGoFormDialogFragment();
    }

    interface IDataProvider extends Base.IDataProvider {

        String getValueSaved();

        void saveValue(String value);
    }

    interface IView extends Base.IView {

        void setValueSaved(String text);
    }

}
