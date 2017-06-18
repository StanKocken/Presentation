package com.skocken.presentation.sample.viewproxy;

import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.definition.FormDef;
import com.skocken.presentation.viewproxy.BaseViewProxy;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FormViewProxy
        extends BaseViewProxy<FormDef.IPresenter>
        implements FormDef.IView, View.OnClickListener {

    public FormViewProxy(Activity activity) {
        super(activity);
    }

    public FormViewProxy(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected void onInit() {
        super.onInit();
        findViewByIdEfficient(R.id.save_button).setOnClickListener(this);
        findViewByIdEfficient(R.id.go_to_form_activity).setOnClickListener(this);
        findViewByIdEfficient(R.id.go_to_form_dialog_fragment).setOnClickListener(this);
        findViewByIdEfficient(R.id.go_to_form_fragment_activity).setOnClickListener(this);
    }

    @Override
    public void setValueSaved(String text) {
        TextView textView = findViewByIdEfficient(R.id.save_value_textview);
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.save_button) {
            EditText editText = findViewByIdEfficient(R.id.value_edittext);
            getPresenter().onClickSaveButton(editText.getText().toString());
        } else if (id == R.id.go_to_form_activity) {
            getPresenter().onClickGoFormActivity();
        } else if (id == R.id.go_to_form_dialog_fragment) {
            getPresenter().onClickGoFormDialogFragment();
        } else if (id == R.id.go_to_form_fragment_activity) {
            getPresenter().onClickGoFormFragmentActivity();
        }
    }
}
