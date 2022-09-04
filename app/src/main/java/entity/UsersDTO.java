package main.java.entity;

public class UsersDTO {

    private final String name;
    private final String surname;
    private final String phoneNumber;
    private final String city;
    private final String email;
    private int userID;
    private String streetAndNumber;
    private String voivodeship;
    private String areaName;
    private int userInfoID;

    public UsersDTO(String name, String surname, String phoneNumber, String city, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.email = email;
    }

    public UsersDTO(int userID, String name, String surname, String phoneNumber, String city, String email, int userInfoID) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.email = email;
        this.userInfoID = userInfoID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getCity() {
        return city;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public String getEmail() {
        return email;
    }

    public String getAreaName() {
        return areaName;
    }

    public int getUserID() {
        return userID;
    }

    public int getUserInfoID() {
        return userInfoID;
    }
}
