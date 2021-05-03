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
    @Column(name = "voivodeship", nullable = false, length = 128)
    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
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
    @Column(name = "department_street_and_number", nullable = false, length = 256)
    public String getDepartmentStreetAndNumber() {
        return departmentStreetAndNumber;
    }

    public void setDepartmentStreetAndNumber(String departmentStreetAndNumber) {
        this.departmentStreetAndNumber = departmentStreetAndNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Areas areas = (Areas) o;
        return id == areas.id && Objects.equals(name, areas.name) && Objects.equals(voivodeship, areas.voivodeship) && Objects.equals(city, areas.city) && Objects.equals(departmentStreetAndNumber, areas.departmentStreetAndNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, voivodeship, city, departmentStreetAndNumber);
    }

    @OneToMany(mappedBy = "areasByAreaId")
    public Collection<Users> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<Users> usersById) {
        this.usersById = usersById;
    }
}
