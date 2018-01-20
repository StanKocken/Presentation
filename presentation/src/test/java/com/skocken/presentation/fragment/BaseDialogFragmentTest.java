package com.skocken.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.presentation.util.PresenterOwner;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * Created by stan on 24/09/2017.
 */

public class BaseDialogFragmentTest extends TestCase {

    private static final int CONTENT_VIEW = 123;
    private PresenterOwner mPresenterOwner;
    private TestBaseDialogFragment mUnderTest;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mPresenterOwner = Mockito.mock(PresenterOwner.class);
        mUnderTest = Mockito.spy(new TestBaseDialogFragment());
        mUnderTest.mPresenterOwner = mPresenterOwner;
    }

    @Test
    public void testShouldNotReturnPresenterBeforeOnCreate() {
        assertNull(mUnderTest.getPresenter());
    }

    @Test
    public void testOnCreate() {
        mUnderTest.onCreateDelegate();
        verify(mPresenterOwner).createPresenter();
    }

    @Test
    public void testOnCreateView() {
        LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
        ViewGroup container = Mockito.mock(ViewGroup.class);
        mUnderTest.onCreateView(inflater, container, null);
        verify(inflater).inflate(CONTENT_VIEW, container, false);
    }

    @Test
    public void testOnViewCreated() {
        Bundle bundle = new Bundle();
        mUnderTest.onViewCreated(null, bundle);
        verify(mPresenterOwner).initViewProxy(bundle);
    }

    public static class TestBaseDialogFragment extends BaseDialogFragment {

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
