package com.skocken.presentation.definition;

import android.app.Activity;
import androidx.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        void updateView(@NonNull Context context, @Nullable T item);
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
