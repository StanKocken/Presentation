package com.skocken.presentation.util;

import android.os.Bundle;

public interface ActivityLifeCycleListener {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
