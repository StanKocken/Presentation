package com.skocken.presentation.presenter;

import com.skocken.presentation.definition.Base;

/**
 * Base of a Presenter for an item.
 */
public abstract class BaseItemPresenter
        <P extends Base.IDataProvider, V extends Base.IItemView>
        extends BasePresenter<P, V> {

}
