package com.skocken.presentation.viewproxy;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.skocken.efficientadapter.lib.util.EfficientCacheView;
import com.skocken.presentation.R;
import com.skocken.presentation.definition.Base;

/**
 * Base of BaseViewProxy which take care of keeping the Presenter for you.
 * <p>
 * It will also provide an EfficientCacheView, which keep reference to your view, so you don't have
 * to store them yourself.
 *
 * @param <P> the Presenter class that you want to use
 */
public abstract class BaseViewProxy<P extends Base.IPresenter> implements Base.IView {

    @NonNull
    private final EfficientCacheView mCacheView;

    @Nullable
    private P mPresenter;

    public BaseViewProxy(@NonNull Activity activity) {
        this(activity.findViewById(android.R.id.content));
    }

    public BaseViewProxy(@NonNull Fragment fragment) {
        this(fragment.getView());
    }

    public BaseViewProxy(@NonNull View rootView) {
        mCacheView = createCacheView(rootView);

        // the view keep a reference on the view proxy
        rootView.setTag(R.id.tag_view_proxy, this);

        onInit();
    }

    @NonNull
    EfficientCacheView createCacheView(View rootView) {
        return new EfficientCacheView(rootView);
    }

    protected void onInit() {
        // nothing to do by default
    }

    @Override
    public void setPresenter(@Nullable Base.IPresenter presenter) {
        if(mPresenter == presenter) {
            return;
        }
        //noinspection unchecked
        mPresenter = (P) presenter;
        onPresenterChanged();
    }

    /**
     * Get the Presenter previously provided.
     *
     * Note: this method is marked as @NonNull, so if you did not provide any presenter before this
     * will crash right away in Kotlin, or later in your code in Java.
     * To be safer, you should either check the current status with {@link #isPresenterAttached()}
     * or use the @Nullable method {@link #getPresenterOrNull()}.
     *
     * @return the presenter previously provided.
     */
    @NonNull
    protected P getPresenter() {
        return mPresenter;
    }

    /**
     * Get the Presenter if previously provided.
     *
     * @return the presenter previously provided or null if not set
     */
    @Nullable
    protected P getPresenterOrNull() {
        return mPresenter;
    }

    /**
     * @return true if a Presenter has been previously set
     */
    protected boolean isPresenterAttached() {
        return mPresenter != null;
    }

    @NonNull
    public <V extends View> V getRootView() {
        //noinspection unchecked
        return (V) mCacheView.getView();
    }

    @NonNull
    @Override
    public Context getContext() {
        return getRootView().getContext();
    }

    @NonNull
    @Override
    public Resources getResources() {
        return getRootView().getResources();
    }

    /**
     * Helper for EfficientCacheView#clearViewsCached()
     */
    public void clearViewsCached() {
        mCacheView.clearViewsCached();
    }

    /**
     * Helper for EfficientCacheView#clearViewCached(int)}
     */
    public void clearViewCached(int viewId) {
        mCacheView.clearViewCached(viewId);
    }

    /**
     * Helper for EfficientCacheView#clearViewCached(int, int)
     */
    public void clearViewCached(int parentId, int viewId) {
        mCacheView.clearViewCached(parentId, viewId);
    }

    /**
     * Helper for EfficientCacheView#findViewByIdEfficient(int)}
     */
    @Nullable
    public <T extends View> T findViewByIdEfficient(int id) {
        return mCacheView.findViewByIdEfficient(id);
    }

    /**
     * Helper for EfficientCacheView#findViewByIdEfficient(int, int)
     */
    @Nullable
    public <T extends View> T findViewByIdEfficient(int parentId, int id) {
        return mCacheView.findViewByIdEfficient(parentId, id);
    }

    /**
     * Called whenever the Presenter has been changed
     */
    protected void onPresenterChanged() {
        // nothing by default
    }

}
