package com.dk.agriculture;

import java.io.Serializable;

/**
 * Created by Anurag on 24-03-2018.
 */

public class MarketAddModel implements Serializable {
    public String equipment,equipmentdesc, priceperday, maxdays,  renternumber, latitude,location, longitude,rentername, distance, id;

    public MarketAddModel () {

    }

    public MarketAddModel(String equipment,String rentername,  String equipmentdesc, String priceperday, String maxdays, String location, String renternumber, String latitude, String longitude, String distance, String id) {
        this.equipment = equipment;
        this.equipmentdesc = equipmentdesc;
        this.priceperday = priceperday;
        this.maxdays = maxdays;
        this.rentername= rentername;
        this.renternumber = renternumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location= location;
        this.distance = distance;
        this.id = id;
    }

    public String getRentername() {
        return rentername;
    }

    public void setRentername(String rentername) {
        this.rentername = rentername;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getEquipmentdesc() {
        return equipmentdesc;
    }

    public void setEquipmentdesc(String equipmentdesc) {
        this.equipmentdesc = equipmentdesc;
    }

    public String getPriceperday() {
        return priceperday;
    }

    public void setPriceperday(String priceperday) {
        this.priceperday = priceperday;
    }

    public String getMaxdays() {
        return maxdays;
    }

    public void setMaxdays(String maxdays) {
        this.maxdays = maxdays;
    }


    public String getRenternumber() {
        return renternumber;
    }

    public void setRenternumber(String renternumber) {
        this.renternumber = renternumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}