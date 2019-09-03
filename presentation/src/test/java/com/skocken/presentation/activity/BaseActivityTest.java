package com.skocken.presentation.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.util.PresenterOwner;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class BaseActivityTest extends TestCase {

    private static final int CONTENT_VIEW = 123;
    private PresenterOwner mPresenterOwner;
    private TestBaseActivity mUnderTest;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mPresenterOwner = Mockito.mock(PresenterOwner.class);
        mUnderTest = Mockito.spy(new TestBaseActivity());
        mUnderTest.mPresenterOwner = mPresenterOwner;
        doNothing().when(mUnderTest).setContentView(anyInt());
    }

    @Test
    public void testShouldNotReturnPresenterBeforeOnCreate() {
        assertNull(mUnderTest.getPresenter());
    }

    @Test
    public void testOnCreate() {
        Bundle bundle = new Bundle();
        mUnderTest.onCreateDelegate(bundle);

        verify(mPresenterOwner).createPresenter();
        verify(mPresenterOwner).initViewProxy(bundle);
        verify(mUnderTest).getContentView();
        verify(mUnderTest).setContentView(CONTENT_VIEW);
    }

    private static class TestBaseActivity extends BaseActivity {

        @Override
        public int getContentView() {
            return CONTENT_VIEW;
        }

        @NonNull
        @Override
        public Class getPresenterClass() {
            return BasePresenter.class;
        }

        @NonNull
        @Override
        public Base.IView newViewProxy(BasePresenter presenter, Bundle savedInstanceState) {
            return Mockito.mock(Base.IView.class);
        }
    }
}