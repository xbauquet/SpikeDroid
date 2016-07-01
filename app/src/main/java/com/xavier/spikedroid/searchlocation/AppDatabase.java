package com.xavier.spikedroid.searchlocation;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 30/06/2016.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, foreignKeysSupported = true)
public class AppDatabase {
    public static final String NAME = "dataBase";

    public static final int VERSION = 1;

}
