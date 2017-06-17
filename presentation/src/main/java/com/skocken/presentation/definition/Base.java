package com.skocken.presentation.definition;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

public interface Base {

    interface IPresenter {

        void setView(Base.IView view);

        Activity getActivity();

        Context getContext();

        Resources getResources();
    }

    interface IItemPresenter<T> extends IPresenter {

        void updateView(Context context, T object);
    }

    interface IDataProvider {

        void setPresenter(Base.IPresenter presenter);
    }

    interface IView {

        Context getContext();

        Resources getResources();

        void setPresenter(Base.IPresenter presenter);
    }

    interface IItemView<T> extends IView {

        T getObject();

        int getPosition();
    }
}
