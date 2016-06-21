package com.xavier.spikedroid.main;

import android.content.Intent;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 21/06/2016.
 */
public class Spike{
    private String name;
    private Intent intent;

    public Spike(String name, Intent intent){
        this.name = name;
        this.intent = intent;
    }

    // ---------------------------------------
    // Getters / Setters
    // ---------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}

