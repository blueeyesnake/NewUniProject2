package com.example.newuniproject2;

public class UserFeatures {

    // variables for our coursename,
    // description,tracks and duration,imageId.
    private String usersName;
    private String usersYear;
    private String usersSchool;
    private String usersDistance;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    private int imgId;

    // creating getter and setter methods
    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersYear() {
        return usersYear;
    }

    public void setUsersYear(String usersYear) {
        this.usersYear = usersYear;
    }

    public String getUsersSchool() {
        return usersSchool;
    }

    public void setUsersSchool(String usersSchool) {
        this.usersSchool = usersSchool;
    }

    public String getUsersDistance() {
        return usersDistance;
    }

    public void setUsersDistance(String usersDistance) {
        this.usersDistance = usersDistance;
    }

    // constructor.
    public UserFeatures(String usersName, String usersYear, String usersSchool, String usersDistance, int imgId) {
        this.usersName = usersName;
        this.usersYear = usersYear;
        this.usersSchool = usersSchool;
        this.usersDistance = usersDistance;
        this.imgId = imgId;
    }
}
