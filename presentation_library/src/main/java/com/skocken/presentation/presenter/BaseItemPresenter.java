package com.skocken.presentation.presenter;

import com.skocken.presentation.definition.Base;

public abstract class BaseItemPresenter
        <P extends Base.IDataProvider, V extends Base.IItemView>
        extends BasePresenter<P, V> {

    public BaseItemPresenter(P provider, V view) {
        super(provider, view);
    }

}
