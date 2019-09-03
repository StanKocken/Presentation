package com.skocken.presentation.util;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.presenter.BasePresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by stan on 24/09/2017.
 */
public class PresenterOwnerTest {
    private ViewModelProvider mViewModelProvider;
    private PresenterOwner.Provider mProvider;
    private PresenterOwner mUnderTest;
    private Base.IView mView;

    @Before
    public void setUp() throws Exception {
        mViewModelProvider = Mockito.mock(ViewModelProvider.class);
        mProvider = Mockito.mock(PresenterOwner.Provider.class);
        mUnderTest = new PresenterOwner(mViewModelProvider, mProvider);
        mView = Mockito.mock(Base.IView.class);
    }

    @Test
    public void createPresenter() {
        doReturn(Base.IPresenter.class).when(mProvider).getPresenterClass();

        BasePresenter presenter = Mockito.mock(BasePresenter.class);
        doReturn(presenter).when(mViewModelProvider).get(any(Class.class));

        assertEquals(null, mUnderTest.getPresenter());
        mUnderTest.createPresenter();
        assertEquals(presenter, mUnderTest.getPresenter());

        doReturn(mView).when(mProvider).newViewProxy(
                any(BasePresenter.class), any(Bundle.class));

        Bundle bundle = new Bundle();
        mUnderTest.initViewProxy(bundle);

        verify(mProvider).newViewProxy(mUnderTest.getPresenter(), bundle);
        verify(presenter).setView(mView);

    }



}