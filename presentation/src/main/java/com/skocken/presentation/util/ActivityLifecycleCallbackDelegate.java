package com.skocken.presentation.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class ActivityLifecycleCallbackDelegate implements Application.ActivityLifecycleCallbacks {

    private Activity mActivity;

    private WeakReference<ActivityLifeCycleListener> mListener;

    private ActivityLifecycleCallbackDelegate(Activity activity,
            ActivityLifeCycleListener listener) {
        mListener = new WeakReference<>(listener);
        mActivity = activity;
        Application application = mActivity.getApplication();
        if (application != null) {
            application.registerActivityLifecycleCallbacks(this);
        }
    }

    public static ActivityLifecycleCallbackDelegate track(Activity activity, ActivityLifeCycleListener listener) {
        if (activity == null || listener == null) {
            // impossible to track the activity
            return null;
        }
        return new ActivityLifecycleCallbackDelegate(activity, listener);
    }

    public void release() {
        if (mActivity != null && mActivity.getApplication() != null) {
            mActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
        mActivity = null;
        mListener = null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (!isForMe(activity) || mListener == null) {
            return;
        }
        ActivityLifeCycleListener listener = mListener.get();
        if (listener != null) {
            listener.onDestroy();
        }
        release();
    }

    private boolean isForMe(Activity activity) {
        return mActivity == activity;
    }

}
