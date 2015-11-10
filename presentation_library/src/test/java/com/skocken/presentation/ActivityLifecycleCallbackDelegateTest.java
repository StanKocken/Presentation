package com.skocken.presentation;

import com.skocken.presentation.util.ActivityLifeCycleListener;
import com.skocken.presentation.util.ActivityLifecycleCallbackDelegate;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.app.Application;

import java.lang.ref.WeakReference;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActivityLifecycleCallbackDelegateTest extends TestCase {

    @Mock
    private Activity mActivity;

    @Mock
    private Application mApplication;

    @Mock
    private ActivityLifeCycleListener mListener;

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        MockitoAnnotations.initMocks(this);
        when(mActivity.getApplication()).thenReturn(mApplication);
    }

    @Test
    public void testShouldKeepOnlyWeekReferenceOnListener() {
        ActivityLifecycleCallbackDelegate.track(mActivity, mListener);

        WeakReference<ActivityLifeCycleListener> weakReference = new WeakReference<>(mListener);
        assertNotNull(weakReference.get());

        ArgumentCaptor<Application.ActivityLifecycleCallbacks> argumentCaptor =
                ArgumentCaptor.forClass(Application.ActivityLifecycleCallbacks.class);
        verify(mApplication).registerActivityLifecycleCallbacks(argumentCaptor.capture());

        Application.ActivityLifecycleCallbacks activityLifecycleCallbacks =
                argumentCaptor.getValue();

        verify(mListener, times(0)).onResume();
        activityLifecycleCallbacks.onActivityResumed(mActivity);
        activityLifecycleCallbacks.onActivityResumed(mActivity);
        verify(mListener, times(2)).onResume();

        mListener = null;
        for (int i = 0; i < 10 && weakReference.get() != null; i++) {
            System.gc();
        }

        assertNull(weakReference.get());

        // detect no crash if null references
        activityLifecycleCallbacks.onActivityResumed(mActivity);
    }

    @Test
    public void testShouldNotTrackIfNullListener() {
        ActivityLifecycleCallbackDelegate.track(mActivity, null);

        verify(mApplication, times(0)).registerActivityLifecycleCallbacks(
                any(Application.ActivityLifecycleCallbacks.class)
        );
    }

    @Test
    public void testShouldRelease() {
        ActivityLifecycleCallbackDelegate.track(mActivity, mListener);

        ArgumentCaptor<Application.ActivityLifecycleCallbacks> argumentCaptor =
                ArgumentCaptor.forClass(Application.ActivityLifecycleCallbacks.class);
        verify(mApplication).registerActivityLifecycleCallbacks(argumentCaptor.capture());

        ActivityLifecycleCallbackDelegate activityLifecycleCallbackDelegate =
                (ActivityLifecycleCallbackDelegate) argumentCaptor.getValue();

        verify(mApplication, times(0))
                .unregisterActivityLifecycleCallbacks(activityLifecycleCallbackDelegate);

        verify(mListener, times(0)).onResume();
        activityLifecycleCallbackDelegate.onActivityResumed(mActivity);
        verify(mListener, times(1)).onResume();

        activityLifecycleCallbackDelegate.release();

        verify(mApplication, times(1))
                .unregisterActivityLifecycleCallbacks(activityLifecycleCallbackDelegate);

        // test no more listener callback
        verify(mListener, times(1)).onResume();
        activityLifecycleCallbackDelegate.onActivityResumed(mActivity);
        verify(mListener, times(1)).onResume();
    }

    @Test
    public void testShouldCallListener() {
        Activity otherActivity = Mockito.mock(Activity.class);
        ActivityLifecycleCallbackDelegate.track(mActivity, mListener);

        ArgumentCaptor<Application.ActivityLifecycleCallbacks> argumentCaptor =
                ArgumentCaptor.forClass(Application.ActivityLifecycleCallbacks.class);
        verify(mApplication).registerActivityLifecycleCallbacks(argumentCaptor.capture());

        ActivityLifecycleCallbackDelegate activityLifecycleCallbackDelegate =
                (ActivityLifecycleCallbackDelegate) argumentCaptor.getValue();

        verify(mListener, times(0)).onCreate(null);
        activityLifecycleCallbackDelegate.onActivityCreated(otherActivity, null);
        verify(mListener, times(0)).onCreate(null);
        activityLifecycleCallbackDelegate.onActivityCreated(mActivity, null);
        verify(mListener, times(1)).onCreate(null);

        verify(mListener, times(0)).onStart();
        activityLifecycleCallbackDelegate.onActivityStarted(otherActivity);
        verify(mListener, times(0)).onStart();
        activityLifecycleCallbackDelegate.onActivityStarted(mActivity);
        verify(mListener, times(1)).onStart();

        verify(mListener, times(0)).onResume();
        activityLifecycleCallbackDelegate.onActivityResumed(otherActivity);
        verify(mListener, times(0)).onResume();
        activityLifecycleCallbackDelegate.onActivityResumed(mActivity);
        verify(mListener, times(1)).onResume();

        verify(mListener, times(0)).onPause();
        activityLifecycleCallbackDelegate.onActivityPaused(otherActivity);
        verify(mListener, times(0)).onPause();
        activityLifecycleCallbackDelegate.onActivityPaused(mActivity);
        verify(mListener, times(1)).onPause();

        verify(mListener, times(0)).onStop();
        activityLifecycleCallbackDelegate.onActivityStopped(otherActivity);
        verify(mListener, times(0)).onStop();
        activityLifecycleCallbackDelegate.onActivityStopped(mActivity);
        verify(mListener, times(1)).onStop();

        verify(mListener, times(0)).onSaveInstanceState(null);
        activityLifecycleCallbackDelegate.onActivitySaveInstanceState(otherActivity, null);
        verify(mListener, times(0)).onSaveInstanceState(null);
        activityLifecycleCallbackDelegate.onActivitySaveInstanceState(mActivity, null);
        verify(mListener, times(1)).onSaveInstanceState(null);

        verify(mListener, times(0)).onDestroy();
        activityLifecycleCallbackDelegate.onActivityDestroyed(otherActivity);
        verify(mListener, times(0)).onDestroy();
        activityLifecycleCallbackDelegate.onActivityDestroyed(mActivity);
        verify(mListener, times(1)).onDestroy();

    }
}