package main.java.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_infos", schema = "outbox")
public class UserInfos {
    private int userInfoId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String streetAndNumber;
    private String city;
    private String voivodeship;

    @Id
    @Column(name="user_infoID")
    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "street_and_number")
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "voivodeship")
    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfos userInfos = (UserInfos) o;
        return userInfoId == userInfos.userInfoId && Objects.equals(name, userInfos.name) && Objects.equals(surname, userInfos.surname) && Objects.equals(email, userInfos.email) && Objects.equals(phoneNumber, userInfos.phoneNumber) && Objects.equals(streetAndNumber, userInfos.streetAndNumber) && Objects.equals(city, userInfos.city) && Objects.equals(voivodeship, userInfos.voivodeship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInfoId, name, surname, email, phoneNumber, streetAndNumber, city, voivodeship);
    }
}
