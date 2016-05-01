package com.skocken.presentation.definition;

import com.skocken.presentation.model.TestObject;

public interface TestItemDef {

    interface IDataProvider extends Base.IDataProvider {

    }

    interface IItemPresenter extends Base.IItemPresenter<TestObject> {

    }

    interface IItemView extends Base.IItemView<TestObject> {

    }

}
