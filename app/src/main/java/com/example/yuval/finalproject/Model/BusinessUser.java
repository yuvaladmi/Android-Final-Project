package com.example.yuval.finalproject.Model;

/**
 * Created by Yuval on 10/06/2017.
 */

public class BusinessUser extends ClientUser {

    private String address;
    private String treatments;
    private String images;

    public BusinessUser() {
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getTreatments() {return treatments;}

    public void setTreatments(String treatments) {this.treatments = treatments;}

    public String getImages() {return images;}

    public void setImages(String images) {this.images = images;}
}
