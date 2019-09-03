package com.skocken.presentation.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.util.PresenterOwner;

/**
 * Base of the link between an Activity and the MVP of this library.
 * <p>
 * Note:
 * You don't have to use this BaseActivity if your activity already extends something else.
 * In this case, take this class as an example.
 */
public abstract class BaseActivity<P extends BasePresenter, V extends Base.IView>
        extends AppCompatActivity
        implements PresenterOwner.Provider<P, V> {

    PresenterOwner<P, V> mPresenterOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenterOwner == null) {
            mPresenterOwner = new PresenterOwner<>(this);
        }
        onCreateDelegate(savedInstanceState);
    }

    @Nullable
    public P getPresenter() {
        return mPresenterOwner.getPresenter();
    }

    protected void onPresenterSetup(@NonNull P presenter) {
        // nothing to do
    }

    void onCreateDelegate(Bundle savedInstanceState) {
        setContentView(getContentView());
        mPresenterOwner.createPresenter();
        mPresenterOwner.initViewProxy(savedInstanceState);
        onPresenterSetup(mPresenterOwner.getPresenter());
    }
}
