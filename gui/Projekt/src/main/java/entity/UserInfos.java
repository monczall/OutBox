package main.java.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_infos", schema = "outbox", catalog = "")
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
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 128)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "phone_number", nullable = false, length = 32)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "street_and_number", nullable = false, length = 256)
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    @Basic
    @Column(name = "city", nullable = false, length = 128)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "voivodeship", nullable = false, length = 128)
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
        return id == userInfos.id && Objects.equals(name, userInfos.name) && Objects.equals(surname, userInfos.surname) && Objects.equals(phoneNumber, userInfos.phoneNumber) && Objects.equals(streetAndNumber, userInfos.streetAndNumber) && Objects.equals(city, userInfos.city) && Objects.equals(voivodeship, userInfos.voivodeship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phoneNumber, streetAndNumber, city, voivodeship);
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
