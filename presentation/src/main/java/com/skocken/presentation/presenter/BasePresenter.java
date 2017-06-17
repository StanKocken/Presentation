package com.skocken.presentation.presenter;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

import com.skocken.presentation.definition.Base;

public abstract class BasePresenter<D extends Base.IDataProvider, V extends Base.IView>
        extends ViewModel
        implements Base.IPresenter {

    private D mProvider;
    private V mView;

    public BasePresenter() {
    }

    public void setProvider(D provider) {
        if (mProvider == provider) {
            return; // no changes
        }
        mProvider = provider;
        if (mProvider != null) {
            mProvider.setPresenter(this);
        }
        onProviderChanged();
    }

    @Override
    public void setView(Base.IView view) {
        if (mView == view) {
            return; // no changes
        }
        mView = (V) view;
        if (mView != null) {
            mView.setPresenter(this);
        }
        onViewChanged();
    }

    @Override
    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            // get the base activity and loop.
            // fix to get Activity from android.support.v7.app.MediaRouteButton#getActivity()
            Context previousContext = context;
            context = ((ContextWrapper) context).getBaseContext();
            if (previousContext.equals(context)) {
                break; // no more base context, stack overflow possible, leave the while
            }
        }
        return null;
    }

    @Override
    public Context getContext() {
        if (mView == null) {
            return null;
        } else {
            return mView.getContext();
        }
    }

    @Override
    public Resources getResources() {
        if (mView == null) {
            return null;
        } else {
            return mView.getResources();
        }
    }

    protected D getProvider() {
        return mProvider;
    }

    protected V getView() {
        return mView;
    }

    protected void onViewChanged() {
        // nothing by default
    }

    protected void onProviderChanged() {
        // nothing by default
    }
}
