package com.xavier.spikedroid.githubrepositories;

import java.io.Serializable;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 22/06/2016.
 */
public class Repository implements Serializable {
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
