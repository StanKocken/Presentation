package com.skocken.presentation.viewproxy;

import com.skocken.efficientadapter.lib.util.EfficientCacheView;
import com.skocken.presentation.R;
import com.skocken.presentation.definition.TestDef;
import com.skocken.testing.CallCount;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseViewProxyTest extends TestCase {

    @Test
    public void testShouldStoreViewProxyOnViewTag() {
        View rootView = Mockito.mock(View.class);
        BaseViewProxy baseViewProxy = new BaseViewProxy(rootView) {
        };

        verify(rootView).setTag(R.id.tag_view_proxy, baseViewProxy);
    }

    @Test
    public void testConstructorActivityShouldGetContentView() {
        Activity activity = Mockito.mock(Activity.class);
        View rootView = Mockito.mock(View.class);
        when(activity.findViewById(android.R.id.content)).thenReturn(rootView);

        BaseViewProxy baseViewProxy = new BaseViewProxy(activity) {
        };

        assertEquals("The view is not properly get from the Activity",
                rootView, baseViewProxy.getRootView());
    }

    @Test
    public void testConstructorViewShouldReturnView() {
        View rootView = Mockito.mock(View.class);
        BaseViewProxy baseViewProxy = new BaseViewProxy(rootView) {
        };

        assertEquals("The view is not properly get",
                rootView, baseViewProxy.getRootView());
    }

    @Test
    public void testShouldSetAndGetPresenter() {
        View rootView = Mockito.mock(View.class);
        BaseViewProxy baseViewProxy = new BaseViewProxy(rootView) {
        };

        TestDef.IPresenter presenter = Mockito.mock(TestDef.IPresenter.class);
        baseViewProxy.setPresenter(presenter);

        assertEquals("The presenter get from the ViewProxy should be the same as the one set",
                presenter, baseViewProxy.getPresenter());
    }

    @Test
    public void testShouldReturnContextFromView() {
        Context context = Mockito.mock(Context.class);
        View rootView = Mockito.mock(View.class);
        when(rootView.getContext()).thenReturn(context);

        BaseViewProxy baseViewProxy = new BaseViewProxy(rootView) {
        };

        assertEquals("The context should be get from the view",
                context, baseViewProxy.getContext());
    }

    @Test
    public void testShouldReturnResourcesFromView() {
        Resources resources = Mockito.mock(Resources.class);
        View rootView = Mockito.mock(View.class);
        when(rootView.getResources()).thenReturn(resources);

        BaseViewProxy baseViewProxy = new BaseViewProxy(rootView) {
        };

        assertEquals("The resources should be get from the view",
                resources, baseViewProxy.getResources());
    }

    @Test
    public void testShouldCallEfficientCacheViewMethod() {
        View rootView = Mockito.mock(View.class);
        final EfficientCacheView efficientCacheView = Mockito.mock(EfficientCacheView.class);

        BaseViewProxy baseViewProxy = new BaseViewProxy(rootView) {
            @NonNull
            @Override
            EfficientCacheView createCacheView(View rootView) {
                return efficientCacheView;
            }
        };

        verify(efficientCacheView, times(0)).clearViewsCached();
        baseViewProxy.clearViewsCached();
        verify(efficientCacheView, times(1)).clearViewsCached();

        int viewId = 1234;
        verify(efficientCacheView, times(0)).clearViewCached(viewId);
        baseViewProxy.clearViewCached(viewId);
        verify(efficientCacheView, times(1)).clearViewCached(viewId);

        int parentId = 456;
        verify(efficientCacheView, times(0)).clearViewCached(parentId, viewId);
        baseViewProxy.clearViewCached(parentId, viewId);
        verify(efficientCacheView, times(1)).clearViewCached(parentId, viewId);

        verify(efficientCacheView, times(0)).findViewByIdEfficient(viewId);
        baseViewProxy.findViewByIdEfficient(viewId);
        verify(efficientCacheView, times(1)).findViewByIdEfficient(viewId);

        verify(efficientCacheView, times(0)).findViewByIdEfficient(parentId, viewId);
        baseViewProxy.findViewByIdEfficient(parentId, viewId);
        verify(efficientCacheView, times(1)).findViewByIdEfficient(parentId, viewId);
    }


    @Test
    public void testShouldInitControllerWhileConstruct() {
        View rootView = Mockito.mock(View.class);

        final CallCount callCount = new CallCount();

        new BaseViewProxy(rootView) {
            @Override
            protected void onInit() {
                callCount.called();
            }
        };
        assertTrue("The method initController was not called", callCount.hasBeenCalled());
    }
}