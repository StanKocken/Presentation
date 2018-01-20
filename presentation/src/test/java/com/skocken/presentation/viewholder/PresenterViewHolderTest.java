package com.skocken.presentation.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.skocken.presentation.definition.Base;
import com.skocken.presentation.definition.TestItemDef;
import com.skocken.presentation.model.TestObject;
import com.skocken.presentation.presenter.BaseItemPresenter;
import com.skocken.testing.CallCount;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;


public class PresenterViewHolderTest extends TestCase {

    @Test
    public void testShouldSetAndGetPresenter() {
        View view = Mockito.mock(View.class);

        TestPresenterViewHolder presenterViewHolder = new TestPresenterViewHolder(view);

        TestItemDef.IItemPresenter presenter = Mockito.mock(TestItemDef.IItemPresenter.class);
        presenterViewHolder.setPresenter(presenter);

        assertEquals("The presenter get from the PresenterViewHolder should be the same as the "
                        + "one set",
                presenter, presenterViewHolder.getPresenter());
    }

    @Test
    public void testShouldInitControllerWhileConstruct() {
        View view = Mockito.mock(View.class);

        final CallCount callCount = new CallCount();

        new TestPresenterViewHolder(view) {
            @Override
            protected void initController() {
                callCount.called();
            }
        };

        assertTrue("The method initController was not called", callCount.hasBeenCalled());
    }

    @Test
    public void testShouldCallPresenterUpdateView() {
        View view = Mockito.mock(View.class);

        final TestItemDef.IItemPresenter presenter = Mockito.mock(TestItemDef.IItemPresenter.class);
        final Context context = Mockito.mock(Context.class);
        final TestObject object = Mockito.mock(TestObject.class);

        TestPresenterViewHolder presenterViewHolder = new TestPresenterViewHolder(view) {
            @Override
            TestItemDef.IItemPresenter createPresenter(Class<? extends TestItemDef.IItemPresenter> modelClass) {
                return presenter;
            }
        };
        presenterViewHolder.updateView(context, object);

        verify(presenter).updateView(eq(context), eq(object));
    }

    @Test
    public void testCreatePresenter_normal() {
        View view = Mockito.mock(View.class);
        TestPresenterViewHolder presenterViewHolder = new TestPresenterViewHolder(view);

        TestItemDef.IItemPresenter item = presenterViewHolder.createPresenter(RegularClass.class);
        assertTrue(item instanceof RegularClass);
    }

    @Test
    public void testCreatePresenter_privateConstructor() {
        View view = Mockito.mock(View.class);
        TestPresenterViewHolder presenterViewHolder = new TestPresenterViewHolder(view);

        try {
            presenterViewHolder.createPresenter(PrivateConstructorClass.class);
            fail("Should throw an exception");
        } catch (RuntimeException ex) {
            // normal flow
        }
    }

    @Test
    public void testCreatePresenter_nonEmptyConstructor() {
        View view = Mockito.mock(View.class);
        TestPresenterViewHolder presenterViewHolder = new TestPresenterViewHolder(view);

        try {
            presenterViewHolder.createPresenter(NonEmptyConstructorClass.class);
            fail("Should throw an exception");
        } catch (RuntimeException ex) {
            // normal flow
        }
    }

    public static class RegularClass extends MockItemPresenter {
    }

    public static class PrivateConstructorClass extends MockItemPresenter {
        private PrivateConstructorClass() {
        }
    }

    public static class NonEmptyConstructorClass extends MockItemPresenter {
        private NonEmptyConstructorClass(View view) {
        }
    }

    private static class TestPresenterViewHolder
            extends PresenterViewHolder<TestObject, TestItemDef.IItemPresenter>
            implements TestItemDef.IItemView {

        public TestPresenterViewHolder(View itemView) {
            super(itemView);
        }

        @NonNull
        @Override
        protected Class<? extends TestItemDef.IItemPresenter> getPresenterClass() {
            return TestItemPresenter.class;
        }
    }

    public static class TestItemPresenter
            extends BaseItemPresenter<TestItemDef.IDataProvider, TestItemDef.IItemView>
            implements TestItemDef.IItemPresenter {

        @Override
        public void updateView(@NonNull Context context, @NonNull TestObject object) {
            // no-op
        }
    }

    private static class MockItemPresenter implements TestItemDef.IItemPresenter {

        @Override
        public void setView(Base.IView view) {

        }

        @Override
        public Activity getActivity() {
            return null;
        }

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public Resources getResources() {
            return null;
        }

        @Override
        public void updateView(@NonNull Context context, @NonNull TestObject object) {

        }
    }

}