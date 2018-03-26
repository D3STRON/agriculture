package com.dk.agriculture;

/**
 * Created by Anurag on 25-03-2018.
 */

public class BatchModel {
    public String cropname,harvestat, fertilizeat, waterat, id;

    BatchModel(){

    }

    public BatchModel(String cropname, String harvestat, String fertilizeat, String waterat, String id) {
        this.cropname = cropname;
        this.harvestat = harvestat;
        this.fertilizeat = fertilizeat;
        this.waterat = waterat;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getHarvestat() {
        return harvestat;
    }

    public void setHarvestat(String harvestat) {
        this.harvestat = harvestat;
    }

    public String getFertilizeat() {
        return fertilizeat;
    }

    public void setFertilizeat(String fertilizeat) {
        this.fertilizeat = fertilizeat;
    }

    public String getWaterat() {
        return waterat;
    }

    public void setWaterat(String waterat) {
        this.waterat = waterat;
    }
}
