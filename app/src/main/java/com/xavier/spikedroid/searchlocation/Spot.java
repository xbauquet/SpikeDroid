package com.xavier.spikedroid.searchlocation;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 28/06/2016.
 */

@Table(database = AppDatabase.class)
public class Spot extends BaseModel{


    @Column
    @PrimaryKey(autoincrement =  true)
    private long id;

    @Column
    @Unique
    private String name;

    @Column
    private Double latitude = null;

    @Column
    private Double longitude = null;

    @Column
    private Type type;

    @Column
    private long timestamp;

    @Column
    private long count = 0;

    @Column
    private String sourceId;


    Spot(){}

    Spot(String name, Double latitude, Double longitude, Type type, String sourceId){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        timestamp = new Date().getTime();
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}

enum Type{
    FAVORITE, HISTORY, GOOGLE_SUGGESTION
}