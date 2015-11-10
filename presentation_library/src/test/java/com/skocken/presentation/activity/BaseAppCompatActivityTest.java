package com.skocken.presentation.activity;

import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.testing.CallCount;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseAppCompatActivityTest extends TestCase {

    @Test
    public void testShouldNotReturnPresenterBeforeOnCreate() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity();
        assertNull(baseActivity.getPresenter());
    }

    @Test
    public void testShouldCallOverrideMethods() {

        final CallCount getContentViewCallCount = new CallCount();
        final CallCount newPresenterCallCount = new CallCount();

        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @Override
            protected int getContentView() {
                getContentViewCallCount.called();
                return 0;
            }

            @Override
            protected BasePresenter newPresenter() {
                newPresenterCallCount.called();
                return null;
            }
        };

        baseActivity.onCreate(null);

        assertEquals(1, getContentViewCallCount.getNbCalled());
        assertEquals(1, newPresenterCallCount.getNbCalled());
    }

    @Test
    public void testShouldCallPresenterOnCreate() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity();

        Bundle bundle = Mockito.mock(Bundle.class);

        baseActivity.onCreate(bundle);
        verify(baseActivity.getPresenter(), times(1)).onCreate(bundle);
    }

    @Test
    public void testShouldCallOnBackPressed() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity();

        baseActivity.onCreate(null);

        BasePresenter presenter = baseActivity.getPresenter();
        when(presenter.onBackPressed()).thenReturn(false);

        verify(presenter, times(0)).onBackPressed();
        baseActivity.onBackPressed();
        verify(presenter, times(1)).onBackPressed();

        when(presenter.onBackPressed()).thenReturn(true);

        baseActivity.onBackPressed();
        verify(presenter, times(2)).onBackPressed();
    }

    @Test
    public void testShouldCallOnBackPressedWithoutPresenter() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @Override
            protected BasePresenter newPresenter() {
                return null;
            }
        };

        baseActivity.onCreate(null);

        assertNull(baseActivity.getPresenter());

        baseActivity.onBackPressed();
    }

    @Test
    public void testShouldCallOnOptionsItemSelected() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity();

        baseActivity.onCreate(null);

        BasePresenter presenter = baseActivity.getPresenter();
        when(presenter.onOptionsItemSelected(any(MenuItem.class))).thenReturn(false);

        MenuItem menuItem = Mockito.mock(MenuItem.class);

        verify(presenter, times(0)).onOptionsItemSelected(menuItem);
        assertFalse(baseActivity.onOptionsItemSelected(menuItem));
        verify(presenter, times(1)).onOptionsItemSelected(menuItem);

        when(presenter.onOptionsItemSelected(any(MenuItem.class))).thenReturn(true);

        assertTrue(baseActivity.onOptionsItemSelected(menuItem));
        verify(presenter, times(2)).onOptionsItemSelected(menuItem);
    }

    @Test
    public void testShouldCallOnOptionsItemSelectedWithoutPresenter() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @Override
            protected BasePresenter newPresenter() {
                return null;
            }
        };

        baseActivity.onCreate(null);

        assertNull(baseActivity.getPresenter());

        assertFalse(baseActivity.onOptionsItemSelected(null));
    }

    @Test
    public void testShouldWeakReferenceOnPresenter() {
        BasePresenter basePresenter = new BasePresenter(null, null) {
        };

        final WeakReference<BasePresenter> weakReferencePresenter =
                new WeakReference<>(basePresenter);

        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @Override
            protected BasePresenter newPresenter() {
                return weakReferencePresenter.get();
            }
        };

        baseActivity.onCreate(null);

        assertEquals(basePresenter, baseActivity.getPresenter());

        basePresenter = null;

        for (int i = 0; i < 10 && weakReferencePresenter.get() != null; i++) {
            System.gc();
        }

        assertNull(basePresenter);
        assertNull(weakReferencePresenter.get());
        assertNull(baseActivity.getPresenter());
    }

    @Test
    public void testShouldCallOnCreateOptionsMenuWithoutPresenter() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @Override
            protected BasePresenter newPresenter() {
                return null;
            }
        };

        baseActivity.onCreate(null);

        assertNull(baseActivity.getPresenter());
        assertFalse(baseActivity.onCreateOptionsMenu(null));
    }

    @Test
    public void testShouldCallOnCreateOptionsMenu() {
        final MenuInflater menuInflater = Mockito.mock(MenuInflater.class);
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @NonNull
            @Override
            public MenuInflater getMenuInflater() {
                return menuInflater;
            }
        };

        baseActivity.onCreate(null);

        BasePresenter presenter = baseActivity.getPresenter();

        Menu menu = Mockito.mock(Menu.class);

        verify(presenter, times(0)).onCreateOptionsMenu(menu, menuInflater);
        baseActivity.onCreateOptionsMenu(menu);
        verify(presenter, times(1)).onCreateOptionsMenu(menu, menuInflater);
    }

    @Test
    public void testShouldCallOnActivityResultWithoutPresenter() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity() {
            @Override
            protected BasePresenter newPresenter() {
                return null;
            }
        };

        baseActivity.onCreate(null);

        assertNull(baseActivity.getPresenter());
        Intent intent = Mockito.mock(Intent.class);
        baseActivity.onActivityResult(0, 0, intent);
    }

    @Test
    public void testShouldCallOnActivityResult() {
        BaseAppCompatActivity baseActivity = new TestBaseAppCompatActivity();

        baseActivity.onCreate(null);

        BasePresenter presenter = baseActivity.getPresenter();

        int requestCode = 432;
        int resultCode = 542;
        Intent intent = Mockito.mock(Intent.class);

        verify(presenter, times(0)).onActivityResult(requestCode, resultCode, intent);
        baseActivity.onActivityResult(requestCode, resultCode, intent);
        verify(presenter, times(1)).onActivityResult(requestCode, resultCode, intent);
    }

    private static class TestBaseAppCompatActivity extends BaseAppCompatActivity {

        private AppCompatDelegate mAppCompatDelegate;

        @Override
        protected int getContentView() {
            return 0;
        }

        @Override
        protected BasePresenter newPresenter() {
            return Mockito.mock(BasePresenter.class);
        }

        // To let the tests run on the JVM

        @Override
        public AppCompatDelegate getDelegate() {
            if (mAppCompatDelegate == null) {
                mAppCompatDelegate = Mockito.mock(AppCompatDelegate.class);
            }
            return mAppCompatDelegate;
        }

        @NonNull
        @Override
        public LayoutInflater getLayoutInflater() {
            return Mockito.mock(LayoutInflater.class);
        }
    }
}