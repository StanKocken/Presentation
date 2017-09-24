package com.skocken.presentation.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.definition.TestDef;
import com.skocken.testing.CallCount;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class BasePresenterTest extends TestCase {

    @Test
    public void testShouldKeepViewProxyAndDataProvider() {
        TestDef.IDataProvider provider = Mockito.mock(TestDef.IDataProvider.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);

        TestPresenter presenter = new TestPresenter();

        assertEquals(null, presenter.getProvider());
        presenter.setProvider(provider);
        assertEquals(provider, presenter.getProvider());

        assertEquals(null, presenter.getView());
        presenter.setView(view);
        assertEquals(view, presenter.getView());


        ArgumentCaptor<Base.IPresenter> presenterArgumentCaptor =
                ArgumentCaptor.forClass(Base.IPresenter.class);
        verify(provider).setPresenter(presenterArgumentCaptor.capture());
        assertEquals(presenter, presenterArgumentCaptor.getValue());
    }

    @Test
    public void testShouldNotCrashWithoutViewOrProvider() {
        TestPresenter presenter = new TestPresenter();

        assertNull(presenter.getProvider());
        assertNull(presenter.getView());
        assertNull(presenter.getActivity());
        assertNull(presenter.getContext());
        assertNull(presenter.getResources());
    }

    @Test
    public void testShouldCallSetPresenterDataProvider() {
        TestDef.IDataProvider provider = Mockito.mock(TestDef.IDataProvider.class);

        TestPresenter presenter = new TestPresenter();
        presenter.setProvider(provider);

        presenter.setProvider(provider); // another call will call the other methods only once

        ArgumentCaptor<Base.IPresenter> presenterArgumentCaptor =
                ArgumentCaptor.forClass(Base.IPresenter.class);
        verify(provider).setPresenter(presenterArgumentCaptor.capture());
        assertEquals(presenter, presenterArgumentCaptor.getValue());
    }

    @Test
    public void testShouldCallSetPresenterViewProxy() {
        TestDef.IView view = Mockito.mock(TestDef.IView.class);

        TestPresenter presenter = new TestPresenter();
        presenter.setView(view);

        presenter.setView(view); // another call will call the other methods only once

        ArgumentCaptor<Base.IPresenter> presenterArgumentCaptor =
                ArgumentCaptor.forClass(Base.IPresenter.class);
        verify(view).setPresenter(presenterArgumentCaptor.capture());
        assertEquals(presenter, presenterArgumentCaptor.getValue());
    }

    @Test
    public void testShouldNotReturnActivityIfNotActivity() {
        Context context = Mockito.mock(Context.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);
        when(view.getContext()).thenReturn(context);

        TestPresenter presenter = new TestPresenter();
        presenter.setView(view);

        assertEquals(context, presenter.getContext());
        assertNull(presenter.getActivity());
    }

    @Test
    public void testShouldNotReturnActivityWithoutView() {
        TestPresenter presenter = new TestPresenter();
        assertNull(presenter.getContext());
        assertNull(presenter.getActivity());
    }

    @Test
    public void testShouldReturnActivityFromContextWrapper() {
        ContextWrapper contextWrapper = Mockito.mock(ContextWrapper.class);
        Activity activity = Mockito.mock(Activity.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);
        when(view.getContext()).thenReturn(contextWrapper);
        when(contextWrapper.getBaseContext()).thenReturn(activity);

        TestPresenter presenter = new TestPresenter();
        presenter.setView(view);
        assertEquals(contextWrapper, presenter.getContext());
        assertEquals(activity, presenter.getActivity());
    }

    @Test
    public void testShouldReturnActivity() {
        Activity activity = Mockito.mock(Activity.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);
        when(view.getContext()).thenReturn(activity);

        TestPresenter presenter = new TestPresenter();
        presenter.setView(view);
        assertEquals(activity, presenter.getContext());
        assertEquals(activity, presenter.getActivity());
    }

    @Test
    public void testShouldNotCrashOnDelegateSomeMethodsToViewNull() {
        TestPresenter presenter = new TestPresenter();
        assertNull(presenter.getContext());
        assertNull(presenter.getActivity());
        assertNull(presenter.getResources());
    }

    @Test
    public void testShouldDelegateSomeMethodsToView() {
        TestDef.IView view = Mockito.mock(TestDef.IView.class);

        TestPresenter presenter = new TestPresenter();
        presenter.setView(view);

        verify(view, times(0)).getContext();
        presenter.getContext();
        verify(view, times(1)).getContext();

        presenter.getActivity();
        verify(view, times(2)).getContext();

        verify(view, times(0)).getResources();
        assertNull(presenter.getResources());
        verify(view, times(1)).getResources();
    }

    @Test
    public void testGetActivity_direct() {
        final Activity activity = Mockito.mock(Activity.class);

        TestPresenter presenter = new TestPresenter() {
            @Override
            public Context getContext() {
                return activity;
            }
        };

        assertEquals(activity, presenter.getActivity());
        verifyZeroInteractions(activity);
    }

    @Test
    public void testGetActivity_infiniteLoopReturnNull() {
        final ContextWrapper contextWrapper = Mockito.mock(ContextWrapper.class);
        doReturn(contextWrapper).when(contextWrapper).getBaseContext();

        TestPresenter presenter = new TestPresenter() {
            @Override
            public Context getContext() {
                return contextWrapper;
            }
        };

        assertEquals(null, presenter.getActivity());
        verify(contextWrapper, times(1)).getBaseContext();
    }

    @Test
    public void testGetActivity_wrappedIntoContextWrapper() {
        final ContextWrapper contextWrapper = Mockito.mock(ContextWrapper.class);
        Activity activity = Mockito.mock(Activity.class);
        doReturn(activity).when(contextWrapper).getBaseContext();

        TestPresenter presenter = new TestPresenter() {
            @Override
            public Context getContext() {
                return contextWrapper;
            }
        };

        assertEquals(activity, presenter.getActivity());
        verify(contextWrapper, times(1)).getBaseContext();
    }

    private static class TestPresenter
            extends BasePresenter<TestDef.IDataProvider, TestDef.IView>
            implements TestDef.IPresenter {
    }
}