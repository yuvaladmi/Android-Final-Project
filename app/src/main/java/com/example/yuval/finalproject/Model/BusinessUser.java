package com.example.yuval.finalproject.Model;

import android.graphics.Bitmap;

/**
 * Created by Yuval on 10/06/2017.
 */

public class BusinessUser extends ClientUser {

    private String Address;
    private String treatments;
    private String images;
    private byte[] imageBitMap;
    private Boolean business;
    private Boolean GelNail;
    private Boolean LaserHair;

    public Boolean getBusiness() {
        return this.business;
    }

    public void setBusiness(Boolean business) {
        this.business = business;
    }

    public byte[] getImageBitMap() {
        return imageBitMap;
    }

    public void setImageBitMap(byte[] imageBitMap) {
        this.imageBitMap = imageBitMap;
    }


    public Boolean getGelNail() {
        return GelNail;
    }

    public void setGelNail(Boolean gelNail) {
        GelNail = gelNail;
    }

    public Boolean getLaserHair() {
        return LaserHair;
    }

    public void setLaserHair(Boolean laserHair) {
        LaserHair = laserHair;
    }



    public BusinessUser() {
    }

    public String getAddress() {return Address;}

    public void setAddress(String address) {this.Address = address;}

    public String getTreatments() {return treatments;}

    public void setTreatments(String treatments) {this.treatments = treatments;}

    public String getImages() {return images;}

    public void setImages(String images) {this.images = images;}
}
