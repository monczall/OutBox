package main.java.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Areas {
    private int id;
    private String name;
    private String voivodeship;
    private String city;
    private String departmentStreetAndNumber;
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
    @Column(name = "voivodeship")
    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
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
    @Column(name = "department_street_and_number")
    public String getDepartmentStreetAndNumber() {
        return departmentStreetAndNumber;
    }

    public void setDepartmentStreetAndNumber(String departmentStreetAndNumber) {
        this.departmentStreetAndNumber = departmentStreetAndNumber;
    }

    @OneToMany(mappedBy = "areasByAreaId")
    public Collection<Users> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<Users> usersById) {
        this.usersById = usersById;
    }
}
