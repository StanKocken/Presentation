package com.skocken.presentation.presenter;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.definition.TestDef;
import com.skocken.testing.CallCount;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BasePresenterTest extends TestCase {

    @Test
    public void testShouldKeepViewProxyAndDataProvider() {
        TestDef.IDataProvider provider = Mockito.mock(TestDef.IDataProvider.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);

        TestPresenter presenter = new TestPresenter(provider, view);

        assertEquals(provider, presenter.getProvider());
        assertEquals(view, presenter.getView());

        ArgumentCaptor<Base.IPresenter> presenterArgumentCaptor =
                ArgumentCaptor.forClass(Base.IPresenter.class);
        verify(provider).setPresenter(presenterArgumentCaptor.capture());
        assertEquals(presenter, presenterArgumentCaptor.getValue());
    }

    @Test
    public void testShouldNotCrashWithoutViewOrProvider() {
        TestPresenter presenter = new TestPresenter(null, null);

        assertNull(presenter.getProvider());
        assertNull(presenter.getView());
        assertNull(presenter.getActivity());
        assertNull(presenter.getContext());
        assertNull(presenter.getResources());
    }

    @Test
    public void testShouldCallSetPresenterDataProvider() {
        TestDef.IDataProvider provider = Mockito.mock(TestDef.IDataProvider.class);

        TestPresenter presenter = new TestPresenter(provider, null);

        ArgumentCaptor<Base.IPresenter> presenterArgumentCaptor =
                ArgumentCaptor.forClass(Base.IPresenter.class);
        verify(provider).setPresenter(presenterArgumentCaptor.capture());
        assertEquals(presenter, presenterArgumentCaptor.getValue());
    }

    @Test
    public void testShouldCallSetPresenterViewProxy() {
        TestDef.IView view = Mockito.mock(TestDef.IView.class);

        TestPresenter presenter = new TestPresenter(null, view);

        ArgumentCaptor<Base.IPresenter> presenterArgumentCaptor =
                ArgumentCaptor.forClass(Base.IPresenter.class);
        verify(view).setPresenter(presenterArgumentCaptor.capture());
        assertEquals(presenter, presenterArgumentCaptor.getValue());
    }

    @Test
    public void testShouldInitControllerWhileConstruct() {
        final CallCount callCount = new CallCount();

        new TestPresenter(null, null) {
            @Override
            protected void onInit() {
                callCount.called();
            }
        };

        assertTrue("The method initController was not called", callCount.hasBeenCalled());
    }

    @Test
    public void testShouldCallTrackActivityLifeCycle() {
        Application application = Mockito.mock(Application.class);
        Activity activity = Mockito.mock(Activity.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);
        when(view.getContext()).thenReturn(activity);
        when(activity.getApplication()).thenReturn(application);

        new TestPresenter(null, view);

        verify(application).registerActivityLifecycleCallbacks(
                any(Application.ActivityLifecycleCallbacks.class));
    }

    @Test
    public void testShouldNotReturnActivityIfNotActivity() {
        Context context = Mockito.mock(Context.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);
        when(view.getContext()).thenReturn(context);

        TestPresenter presenter = new TestPresenter(null, view);

        assertEquals(context, presenter.getContext());
        assertNull(presenter.getActivity());
    }

    @Test
    public void testShouldNotReturnActivityWithoutView() {
        TestPresenter presenter = new TestPresenter(null, null);
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

        TestPresenter presenter = new TestPresenter(null, view);
        assertEquals(contextWrapper, presenter.getContext());
        assertEquals(activity, presenter.getActivity());
    }

    @Test
    public void testShouldReturnActivity() {
        Activity activity = Mockito.mock(Activity.class);
        TestDef.IView view = Mockito.mock(TestDef.IView.class);
        when(view.getContext()).thenReturn(activity);

        TestPresenter presenter = new TestPresenter(null, view);
        assertEquals(activity, presenter.getContext());
        assertEquals(activity, presenter.getActivity());
    }

    @Test
    public void testShouldNotCrashOnDelegateSomeMethodsToViewNull() {
        TestPresenter presenter = new TestPresenter(null, null);
        assertNull(presenter.getContext());
        assertNull(presenter.getActivity());
        assertNull(presenter.getResources());
    }

    @Test
    public void testShouldDelegateSomeMethodsToView() {
        TestDef.IView view = Mockito.mock(TestDef.IView.class);

        TestPresenter presenter = new TestPresenter(null, view);

        verify(view, times(1)).getContext();
        presenter.getContext();
        verify(view, times(2)).getContext();

        presenter.getActivity();
        verify(view, times(3)).getContext();

        verify(view, times(0)).getResources();
        assertNull(presenter.getResources());
        verify(view, times(1)).getResources();
    }

    @Test
    public void testShouldCallActivityLifecycleNotImplemented() {
        TestPresenter presenter = new TestPresenter(null, null);
        presenter.onCreateOptionsMenu(null, null);
        presenter.onCreate(null);
        presenter.onStart();
        presenter.onResume();
        presenter.onPause();
        presenter.onStop();
        presenter.onSaveInstanceState(null);
        presenter.onDestroy();
        presenter.onActivityResult(0, 0, null);
    }

    @Test
    public void testShouldReturnFalseHandleBackPressed() {
        TestPresenter presenter = new TestPresenter(null, null);
        assertFalse(presenter.onBackPressed());
    }

    @Test
    public void testShouldReturnFalseHandleOnOptionsItemSelected() {
        TestPresenter presenter = new TestPresenter(null, null);
        assertFalse(presenter.onOptionsItemSelected(null));
    }

    private static class TestPresenter
            extends BasePresenter<TestDef.IDataProvider, TestDef.IView>
            implements TestDef.IPresenter {

        public TestPresenter(TestDef.IDataProvider provider, TestDef.IView view) {
            super(provider, view);
        }
    }
}