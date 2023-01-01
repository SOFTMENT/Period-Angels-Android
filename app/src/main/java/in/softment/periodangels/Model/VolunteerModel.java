package in.softment.periodangels.Model;

import java.util.ArrayList;
import java.util.Date;

public class VolunteerModel {

    public String name = "";
    public String fullAddress = "";
    public String emailId = "";
    public String uid = "";
    public Date registeredAt = new Date();
    public String phoneNumber = "";
    public Double latitude = 0.0;
    public Double longitude = 0.0;
    public ArrayList<String> products = new ArrayList<>();
    public String hash = "";
    public int periodPads = 0;
    public int tampons = 0;
    public int reusableProducts = 0;
    public int menstrualCup = 0;
    public int plasticFree = 0;



    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public static VolunteerModel data  = new VolunteerModel();

    public VolunteerModel() {
        data = this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getPeriodPads() {
        return periodPads;
    }

    public void setPeriodPads(int periodPads) {
        this.periodPads = periodPads;
    }

    public int getTampons() {
        return tampons;
    }

    public void setTampons(int tampons) {
        this.tampons = tampons;
    }

    public int getReusableProducts() {
        return reusableProducts;
    }

    public void setReusableProducts(int reusableProducts) {
        this.reusableProducts = reusableProducts;
    }

    public int getMenstrualCup() {
        return menstrualCup;
    }

    public void setMenstrualCup(int menstrualCup) {
        this.menstrualCup = menstrualCup;
    }

    public int getPlasticFree() {
        return plasticFree;
    }

    public void setPlasticFree(int plasticFree) {
        this.plasticFree = plasticFree;
    }

    public static VolunteerModel getData() {
        return data;
    }

    public static void setData(VolunteerModel data) {
        VolunteerModel.data = data;
    }
}
