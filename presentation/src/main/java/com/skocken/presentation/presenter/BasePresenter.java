package com.skocken.presentation.presenter;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.skocken.presentation.definition.Base;

public abstract class BasePresenter<D extends Base.IDataProvider, V extends Base.IView>
        extends ViewModel
        implements Base.IPresenter {

    private D mProvider;
    private V mView;

    private AutoRemoveLifecycleObserver mAutoRemoveLifecycleObserver;

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
        registerLifecycleIfNeeded();
        onViewChanged();
    }

    private void registerLifecycleIfNeeded() {
        Lifecycle newLifecycle = getLifecycle();
        if (mAutoRemoveLifecycleObserver != null
                && mAutoRemoveLifecycleObserver.sameLifecycle(newLifecycle)) {
            return; // already registered
        }

        if (mAutoRemoveLifecycleObserver != null) {
            // remove previous observer
            mAutoRemoveLifecycleObserver.cleanup();
            mAutoRemoveLifecycleObserver = null;
        }

        if (newLifecycle == null || !(this instanceof LifecycleObserver)) {
            return; // no registration to do
        }
        mAutoRemoveLifecycleObserver =
                AutoRemoveLifecycleObserver.register(newLifecycle, (LifecycleObserver) this);
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

    protected Lifecycle getLifecycle() {
        Activity activity = getActivity();
        if (activity instanceof LifecycleRegistryOwner) {
            return ((LifecycleRegistryOwner) activity).getLifecycle();
        } else {
            return null;
        }
    }

    @Nullable
    protected Lifecycle.State getLifecycleState() {
        Lifecycle lifecycle = getLifecycle();
        if (lifecycle == null) {
            return null;
        } else {
            return lifecycle.getCurrentState();
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

    static class AutoRemoveLifecycleObserver implements LifecycleObserver {
        private final LifecycleObserver mObserver;
        private Lifecycle mLifecycle;

        private AutoRemoveLifecycleObserver(@NonNull Lifecycle lifecycle,
                                            @NonNull LifecycleObserver observer) {
            mLifecycle = lifecycle;
            mObserver = observer;
        }

        private static AutoRemoveLifecycleObserver register(@NonNull Lifecycle lifecycle,
                                                            @NonNull LifecycleObserver observer) {
            lifecycle.addObserver(observer);

            AutoRemoveLifecycleObserver instance =
                    new AutoRemoveLifecycleObserver(lifecycle, observer);
            lifecycle.addObserver(instance); // auto-register, so we can auto-cancel
            return instance;
        }

        private boolean sameLifecycle(Lifecycle lifecycle) {
            return mLifecycle == lifecycle;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void cleanup() {
            if (mLifecycle == null) {
                return; // already cleanup
            }
            mLifecycle.removeObserver(mObserver);
            mLifecycle.removeObserver(this);
        }
    }
}
