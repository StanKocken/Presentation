package com.skocken.presentation.presenter;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.util.ActivityLifeCycleListener;
import com.skocken.presentation.util.ActivityLifecycleCallbackDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<P extends Base.IDataProvider, V extends Base.IView>
        implements Base.IPresenter, ActivityLifeCycleListener {

    private WeakReference<ActivityLifecycleCallbackDelegate> mActivityLifecycleCallbackDelegate;

    private final P mProvider;

    private final V mView;

    public BasePresenter(P provider, V view) {
        mProvider = provider;
        mView = view;

        if (mProvider != null) {
            mProvider.setPresenter(this);
        }
        if (mView != null) {
            mView.setPresenter(this);
        }

        subscribeLifeCycle();

        onInit();
    }

    protected void onInit() {
        // nothing to do by default
    }

    protected P getProvider() {
        return mProvider;
    }

    protected V getView() {
        return mView;
    }

    @Override
    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            // get the base activity and loop.
            // fix to get Activity from android.support.v7.app.MediaRouteButton#getActivity()
            context = ((ContextWrapper)context).getBaseContext();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // nothing to do by default
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // nothing to do by default
    }

    @Override
    public void onStart() {
        // nothing to do by default
    }

    @Override
    public void onResume() {
        // nothing to do by default
    }

    @Override
    public void onPause() {
        // nothing to do by default
    }

    @Override
    public void onStop() {
        // nothing to do by default
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // nothing to do by default
    }

    @Override
    public void onDestroy() {
        if (mActivityLifecycleCallbackDelegate != null) {
            ActivityLifecycleCallbackDelegate activityLifecycleCallbackDelegate =
                    mActivityLifecycleCallbackDelegate.get();
            if (activityLifecycleCallbackDelegate != null) {
                activityLifecycleCallbackDelegate.release();
            }
            mActivityLifecycleCallbackDelegate = null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // nothing to do by default
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     *
     * @return true if the presenter handle this back
     */
    public boolean onBackPressed() {
        return false;
    }

    protected void subscribeLifeCycle() {
        Activity activity = getActivity();
        if (activity != null) {
            mActivityLifecycleCallbackDelegate =
                    new WeakReference<>(ActivityLifecycleCallbackDelegate.track(activity, this));
        }
    }

}
