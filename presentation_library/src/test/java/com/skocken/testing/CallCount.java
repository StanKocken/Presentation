package com.skocken.testing;

public class CallCount {

    private int mNbCalled;

    public void called() {
        mNbCalled++;
    }

    public boolean hasBeenCalled() {
        return mNbCalled == 1;
    }

    public int getNbCalled() {
        return mNbCalled;
    }
}
