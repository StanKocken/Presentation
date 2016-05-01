package com.skocken.presentation.viewproxy;

import com.skocken.efficientadapter.lib.util.EfficientCacheView;
import com.skocken.presentation.R;
import com.skocken.presentation.definition.Base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class BaseViewProxy<P extends Base.IPresenter> implements Base.IView {

    private final EfficientCacheView mCacheView;

    private P mPresenter;

    public BaseViewProxy(Activity activity) {
        this(activity.findViewById(android.R.id.content));
    }

    public BaseViewProxy(View rootView) {
        mCacheView = createCacheView(rootView);

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
    public void setPresenter(Base.IPresenter presenter) {
        mPresenter = (P) presenter;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public <V extends View> V getRootView() {
        return (V) mCacheView.getView();
    }

    @Override
    public Context getContext() {
        return getRootView().getContext();
    }

    @Override
    public Resources getResources() {
        return getRootView().getResources();
    }

    /**
     * Helper for {@link EfficientCacheView#clearViewsCached()}
     */
    public void clearViewsCached() {
        mCacheView.clearViewsCached();
    }

    /**
     * Helper for {@link EfficientCacheView#clearViewCached(int)}
     */
    public void clearViewCached(int viewId) {
        mCacheView.clearViewCached(viewId);
    }

    /**
     * Helper for {@link EfficientCacheView#clearViewCached(int, int)}
     */
    public void clearViewCached(int parentId, int viewId) {
        mCacheView.clearViewCached(parentId, viewId);
    }

    /**
     * Helper for {@link EfficientCacheView#findViewByIdEfficient(int)}
     */
    public <T extends View> T findViewByIdEfficient(int id) {
        return mCacheView.findViewByIdEfficient(id);
    }

    /**
     * Helper for {@link EfficientCacheView#findViewByIdEfficient(int, int)}
     */
    public <T extends View> T findViewByIdEfficient(int parentId, int id) {
        return mCacheView.findViewByIdEfficient(parentId, id);
    }

}
