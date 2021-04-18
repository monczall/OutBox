package main.java.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Areas {
    private int areaId;
    private String name;
    private String voivodeship;
    private String city;
    private String departmentStreetAndNumber;

    @Id
    @Column(name = "areaID")
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Areas areas = (Areas) o;
        return areaId == areas.areaId && Objects.equals(name, areas.name) && Objects.equals(voivodeship, areas.voivodeship) && Objects.equals(city, areas.city) && Objects.equals(departmentStreetAndNumber, areas.departmentStreetAndNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaId, name, voivodeship, city, departmentStreetAndNumber);
    }
}
