package com.example.fitnessapp;

public class UserModel {
    private String userEmail;
    private String userPassword;
    private float userStepsTaken;
    private double distanceTraveled;

    public UserModel(){

    }

    public UserModel(float userStepsTaken, double distanceTraveled){
        this.userStepsTaken = userStepsTaken;
        this.distanceTraveled = distanceTraveled;
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }
    public float getUserStepsTaken(){
        return userStepsTaken;
    }




    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getUserPassword(){
        return userPassword;
    }


}
