package com.skocken.presentation.sample.viewproxy;

import com.skocken.presentation.sample.R;
import com.skocken.presentation.sample.definition.FormDef;
import com.skocken.presentation.viewproxy.BaseViewProxy;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FormViewProxy
        extends BaseViewProxy<FormDef.IPresenter>
        implements FormDef.IView, View.OnClickListener {

    public FormViewProxy(Activity activity) {
        super(activity);

        findViewByIdEfficient(R.id.save_button).setOnClickListener(this);
    }

    @Override
    public void setValueSaved(String text) {
        TextView textView = findViewByIdEfficient(R.id.save_value_textview);
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button) {
            EditText editText = findViewByIdEfficient(R.id.value_edittext);
            getPresenter().onClickSaveButton(editText.getText().toString());
        }
    }
}
