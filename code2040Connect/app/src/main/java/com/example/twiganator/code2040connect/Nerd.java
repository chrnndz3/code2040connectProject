package com.example.twiganator.code2040connect;

/**
 * Created by SarekSoteloJimenez on 7/16/17.
 */

public class Nerd {
    private String firstName;
    private String lastName;
    private double latitude;
    private double longitude;
    private String company;
    private String email;
    private String school;

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getCompany(){
        return company;
    }
    public String getEmail(){
        return email;
    }

    public String getSchool(){
        return school;
    }

    public void setFirstName(String name){
        firstName = name;
    }

    public void setLastName(String name){
        lastName = name;
    }

    public void setLatitude (double lat){
        latitude=lat;
    }
    public void setLongitude(double lng){
        longitude=lng;
    }

    public void setCompany(String work){
        company = work;
    }

    public void setEmail(String e){
        email = e;
    }

    public void setSchool(String skool){
        school = skool;
    }
}
