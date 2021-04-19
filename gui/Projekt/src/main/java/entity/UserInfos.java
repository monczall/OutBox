package main.java.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_infos", schema = "outbox")
public class UserInfos {
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String streetAndNumber;
    private String city;
    private String voivodeship;
    private Collection<Packages> packagesById;
    private Collection<Users> usersById;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    @OneToMany(mappedBy = "userInfosByUserInfoId")
    public Collection<Packages> getPackagesById() {
        return packagesById;
    }

    public void setPackagesById(Collection<Packages> packagesById) {
        this.packagesById = packagesById;
    }

    @OneToMany(mappedBy = "userInfosByUserInfoId")
    public Collection<Users> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<Users> usersById) {
        this.usersById = usersById;
    }
}
