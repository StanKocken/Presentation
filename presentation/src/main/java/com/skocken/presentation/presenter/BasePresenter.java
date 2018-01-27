package com.skocken.presentation.presenter;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.skocken.presentation.definition.Base;

/**
 * Base of a Presenter. It is an implementation of Base.IPresenter which take cares of the
 * boiler-plate and do more for you:
 * - this class supports extends ViewModel to be used with the Android Components
 * - if your Presenter, which extends this BasePresenter also implements LifecycleObserver, then
 * it will automatically register/unregister to the lifecycle
 * <p>
 * Note: you don't have to use this BasePresenter, but it will help you
 */
public abstract class BasePresenter<D extends Base.IDataProvider, V extends Base.IView>
        extends ViewModel
        implements Base.IPresenter {

    private D mProvider;
    private V mView;

    public BasePresenter() {
    }

    public void setProvider(@Nullable D provider) {
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
    public void setView(@Nullable Base.IView view) {
        if (mView == view) {
            return; // no changes
        }
        //noinspection unchecked
        mView = (V) view;
        if (mView != null) {
            mView.setPresenter(this);
        }
        onViewChanged();
    }

    @Nullable
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

    @Nullable
    @Override
    public Context getContext() {
        if (mView == null) {
            return null;
        } else {
            return mView.getContext();
        }
    }

    @Nullable
    @Override
    public Resources getResources() {
        if (mView == null) {
            return null;
        } else {
            return mView.getResources();
        }
    }

    /**
     * Get the Provider previously provided.
     *
     * Note: this method is marked as @NonNull, so if you did not provide any provider before this
     * will crash right away in Kotlin, or later in your code in Java.
     * To be safer, you should either check the current status with {@link #isProviderAttached()}
     * or use the @Nullable method {@link #getProviderOrNull()}.
     *
     * @return the provider previously provided.
     */
    @NonNull
    protected D getProvider() {
        return mProvider;
    }

    /**
     * Get the Provider if previously provided.
     *
     * @return the provider previously provided or null if not set
     */
    @Nullable
    protected D getProviderOrNull() {
        return mProvider;
    }

    /**
     * @return true if a Provider has been previously set
     */
    protected boolean isProviderAttached() {
        return mProvider != null;
    }

    /**
     * Get the View previously provided.
     *
     * Note: this method is marked as @NonNull, so if you did not provide any provider before this
     * will crash right away in Kotlin, or later in your code in Java.
     * To be safer, you should either check the current status with {@link #isViewAttached()}
     * or use the @Nullable method {@link #getViewOrNull()}.
     *
     * @return the view previously provided.
     */
    @NonNull
    protected V getView() {
        return mView;
    }

    /**
     * Get the View if previously provided.
     *
     * @return the view previously provided or null if not set
     */
    @Nullable
    protected V getViewOrNull() {
        return mView;
    }

    /**
     * @return true if a View has been previously set
     */
    protected boolean isViewAttached() {
        return mView != null;
    }

    protected void onViewChanged() {
        // nothing by default
    }

    protected void onProviderChanged() {
        // nothing by default
    }
}
