package com.skocken.presentation.presenter;

import com.skocken.presentation.definition.TestDef;
import com.skocken.presentation.definition.TestItemDef;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

public class BaseItemPresenterTest extends TestCase {

    @Test
    public void testShouldKeepItemViewProxy() {
        TestItemDef.IItemView view = Mockito.mock(TestItemDef.IItemView.class);
        TestPresenter presenter = new TestPresenter();
        presenter.setView(view);

        assertEquals(view, presenter.getView());
    }

    private static class TestPresenter
            extends BaseItemPresenter<TestDef.IDataProvider, TestItemDef.IItemView>
            implements TestDef.IPresenter {
    }
}