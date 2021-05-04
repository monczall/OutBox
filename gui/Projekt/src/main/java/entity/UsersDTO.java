package main.java.entity;

public class UsersDTO {
    private int userID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String streetAndNumber;
    private String city;
    private String voivodeship;
    private String email;
    private String areaName;

    public UsersDTO(String name, String surname, String phoneNumber, String city, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.email = email;
    }

    public UsersDTO(int userID, String name, String surname, String phoneNumber, String city, String email) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.email = email;
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
}
