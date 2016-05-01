package com.skocken.presentation.viewholder;

import com.skocken.presentation.definition.TestItemDef;
import com.skocken.presentation.model.TestObject;
import com.skocken.testing.CallCount;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

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
            @NonNull
            @Override
            protected TestItemDef.IItemPresenter createBaseItemPresenter() {
                return presenter;
            }
        };
        presenterViewHolder.updateView(context, object);

        verify(presenter).updateView(eq(context), eq(object));
    }

    private static class TestPresenterViewHolder
            extends PresenterViewHolder<TestObject, TestItemDef.IItemPresenter>
            implements TestItemDef.IItemView {

        public TestPresenterViewHolder(View itemView) {
            super(itemView);
        }

        @NonNull
        @Override
        protected TestItemDef.IItemPresenter createBaseItemPresenter() {
            return null;
        }
    }

}