package in.softment.periodangels.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class OrganiserModel implements Serializable {

    public String name = "";
    public String fullAddress = "";
    public String emailId = "";
    public String uid = "";
    public Date registeredAt = new Date();
    public String type = "";
    public String phoneNumber = "";
    public Double latitude = 0.0;
    public Double longitude = 0.0;
    public ArrayList<String> products = new ArrayList<>();
    public String hash = "";
    public boolean mondayAvailable = true;
    public boolean tuesdayAvailable = true;
    public boolean wednesdayAvailable = true;
    public boolean thursdayAvailable = true;
    public boolean fridayAvailable = true;
    public boolean saturdayAvailable = true;
    public boolean sundayAvailable = true;

    public String mondayStartTime = "11:00 AM";
    public String mondayEndTime = "09:00 PM";

    public String tuesdayStartTime = "11:00 AM";
    public String tuesdayEndTime = "09:00 PM";

    public String wednesdayStartTime = "11:00 AM";
    public String wednesdayEndTime = "09:00 PM";

    public String thursdayStartTime = "11:00 AM";
    public String thursdayEndTime = "09:00 PM";

    public String fridayStartTime = "11:00 AM";
    public String fridayEndTime = "09:00 PM";

    public String saturdayStartTime = "11:00 AM";
    public String saturdayEndTime = "09:00 PM";

    public String sundayStartTime = "11:00 AM";
    public String sundayEndTime = "09:00 PM";




    public ArrayList<String> getProducts() {
        return products;
    }



    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public static OrganiserModel data  = new OrganiserModel();

    public OrganiserModel() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isMondayAvailable() {
        return mondayAvailable;
    }

    public void setMondayAvailable(boolean mondayAvailable) {
        this.mondayAvailable = mondayAvailable;
    }

    public boolean isTuesdayAvailable() {
        return tuesdayAvailable;
    }

    public void setTuesdayAvailable(boolean tuesdayAvailable) {
        this.tuesdayAvailable = tuesdayAvailable;
    }

    public boolean isWednesdayAvailable() {
        return wednesdayAvailable;
    }

    public void setWednesdayAvailable(boolean wednesdayAvailable) {
        this.wednesdayAvailable = wednesdayAvailable;
    }

    public boolean isThursdayAvailable() {
        return thursdayAvailable;
    }

    public void setThursdayAvailable(boolean thursdayAvailable) {
        this.thursdayAvailable = thursdayAvailable;
    }

    public boolean isFridayAvailable() {
        return fridayAvailable;
    }

    public void setFridayAvailable(boolean fridayAvailable) {
        this.fridayAvailable = fridayAvailable;
    }

    public boolean isSaturdayAvailable() {
        return saturdayAvailable;
    }

    public void setSaturdayAvailable(boolean saturdayAvailable) {
        this.saturdayAvailable = saturdayAvailable;
    }

    public boolean isSundayAvailable() {
        return sundayAvailable;
    }

    public void setSundayAvailable(boolean sundayAvailable) {
        this.sundayAvailable = sundayAvailable;
    }

    public String getMondayStartTime() {
        return mondayStartTime;
    }

    public void setMondayStartTime(String mondayStartTime) {
        this.mondayStartTime = mondayStartTime;
    }

    public String getMondayEndTime() {
        return mondayEndTime;
    }

    public void setMondayEndTime(String mondayEndTime) {
        this.mondayEndTime = mondayEndTime;
    }

    public String getTuesdayStartTime() {
        return tuesdayStartTime;
    }

    public void setTuesdayStartTime(String tuesdayStartTime) {
        this.tuesdayStartTime = tuesdayStartTime;
    }

    public String getTuesdayEndTime() {
        return tuesdayEndTime;
    }

    public void setTuesdayEndTime(String tuesdayEndTime) {
        this.tuesdayEndTime = tuesdayEndTime;
    }

    public String getWednesdayStartTime() {
        return wednesdayStartTime;
    }

    public void setWednesdayStartTime(String wednesdayStartTime) {
        this.wednesdayStartTime = wednesdayStartTime;
    }

    public String getWednesdayEndTime() {
        return wednesdayEndTime;
    }

    public void setWednesdayEndTime(String wednesdayEndTime) {
        this.wednesdayEndTime = wednesdayEndTime;
    }

    public String getThursdayStartTime() {
        return thursdayStartTime;
    }

    public void setThursdayStartTime(String thursdayStartTime) {
        this.thursdayStartTime = thursdayStartTime;
    }

    public String getThursdayEndTime() {
        return thursdayEndTime;
    }

    public void setThursdayEndTime(String thursdayEndTime) {
        this.thursdayEndTime = thursdayEndTime;
    }

    public String getFridayStartTime() {
        return fridayStartTime;
    }

    public void setFridayStartTime(String fridayStartTime) {
        this.fridayStartTime = fridayStartTime;
    }

    public String getFridayEndTime() {
        return fridayEndTime;
    }

    public void setFridayEndTime(String fridayEndTime) {
        this.fridayEndTime = fridayEndTime;
    }

    public String getSaturdayStartTime() {
        return saturdayStartTime;
    }

    public void setSaturdayStartTime(String saturdayStartTime) {
        this.saturdayStartTime = saturdayStartTime;
    }

    public String getSaturdayEndTime() {
        return saturdayEndTime;
    }

    public void setSaturdayEndTime(String saturdayEndTime) {
        this.saturdayEndTime = saturdayEndTime;
    }

    public String getSundayStartTime() {
        return sundayStartTime;
    }

    public void setSundayStartTime(String sundayStartTime) {
        this.sundayStartTime = sundayStartTime;
    }

    public String getSundayEndTime() {
        return sundayEndTime;
    }

    public void setSundayEndTime(String sundayEndTime) {
        this.sundayEndTime = sundayEndTime;
    }
}
