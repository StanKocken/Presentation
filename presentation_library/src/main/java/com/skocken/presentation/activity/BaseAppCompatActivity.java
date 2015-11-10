package com.skocken.presentation.activity;

import com.skocken.presentation.presenter.BasePresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    private WeakReference<BasePresenter> mWeakReferencePresenter;

    protected abstract int getContentView();

    protected abstract BasePresenter newPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        initPresenter(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            boolean handle = presenter.onOptionsItemSelected(item);
            if (handle) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onCreateOptionsMenu(menu, getMenuInflater());
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        BasePresenter presenter = getPresenter();
        if (presenter != null && presenter.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public BasePresenter getPresenter() {
        if (mWeakReferencePresenter == null) {
            return null;
        } else {
            return mWeakReferencePresenter.get();
        }
    }

    void initPresenter(Bundle savedInstanceState) {
        BasePresenter presenter = newPresenter();
        mWeakReferencePresenter = new WeakReference<>(presenter);
        if (presenter != null) {
            // because the first onCreate won't be call otherwise (creation too late)
            presenter.onCreate(savedInstanceState);
        }
    }
}
