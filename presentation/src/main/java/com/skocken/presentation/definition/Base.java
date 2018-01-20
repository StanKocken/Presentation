package com.skocken.presentation.definition;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Base {

    interface IPresenter {

        void setView(@Nullable Base.IView view);

        @Nullable
        Activity getActivity();

        @Nullable
        Context getContext();

        @Nullable
        Resources getResources();
    }

    interface IItemPresenter<T> extends IPresenter {

        void updateView(@NonNull Context context, @Nullable T object);
    }

    interface IDataProvider {

        void setPresenter(@Nullable Base.IPresenter presenter);
    }

    interface IView {

        @NonNull
        Context getContext();

        @NonNull
        Resources getResources();

        void setPresenter(@Nullable Base.IPresenter presenter);
    }

    interface IItemView<T> extends IView {

        @Nullable
        T getObject();

        int getPosition();
    }
}
