package com.skocken.presentation.provider;

import com.skocken.presentation.definition.TestDef;
import com.skocken.presentation.presenter.BasePresenter;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

public class BaseDataProviderTest extends TestCase {

    @Test
    public void testShouldSetAndGetPresenter() {
        TestDataProvider dataProvider = new TestDataProvider();

        TestDef.IPresenter presenter = Mockito.mock(TestDef.IPresenter.class);
        dataProvider.setPresenter(presenter);

        assertEquals("The presenter get from the DataProvider should be the same as the one set",
                presenter, dataProvider.getPresenter());
    }

    private static class TestDataProvider extends BaseDataProvider<TestDef.IPresenter> {

    }
}