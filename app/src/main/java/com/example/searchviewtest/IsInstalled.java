package com.example.searchviewtest;

import android.app.Application;

public class IsInstalled extends Application {
    private boolean isTwitch;

    public boolean isTwitch() {
        return isTwitch;
    }

    public void setTwitch(boolean twitch) {
        isTwitch = twitch;
    }
}
