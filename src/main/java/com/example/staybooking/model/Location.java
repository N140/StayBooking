package com.example.staybooking.model;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Entity;
import java.io.Serializable;

@Document(indexName = "location")
public class Location implements Serializable {
    private static final long serialVersionUID=2L;
    /*
    * 返回search result
    * */
    //Field map jjava var type to db(index) type
    @Field(type= FieldType.Long)
    private long id;
    @GeoPointField
    private GeoPoint geoPoint;

    public Location(long id, GeoPoint geoPoint) {
        this.id = id;
        this.geoPoint = geoPoint;
    }

    public long getId() {
        return id;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}
