package main.java.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Users {
    private int id;
    private int userInfoId;
    private Integer areaId;
    private String email;
    private String password;
    private String role;
    private Collection<Packages> packagesById;
    private UserInfos userInfosByUserInfoId;
    private Areas areasByAreaId;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_infoID", nullable = false)
    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Basic
    @Column(name = "areaID", nullable = true)
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 256)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && userInfoId == users.userInfoId && Objects.equals(areaId, users.areaId) && Objects.equals(email, users.email) && Objects.equals(password, users.password) && Objects.equals(role, users.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userInfoId, areaId, email, password, role);
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<Packages> getPackagesById() {
        return packagesById;
    }

    public void setPackagesById(Collection<Packages> packagesById) {
        this.packagesById = packagesById;
    }

    @ManyToOne
    @JoinColumn(name = "user_infoID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public UserInfos getUserInfosByUserInfoId() {
        return userInfosByUserInfoId;
    }

    public void setUserInfosByUserInfoId(UserInfos userInfosByUserInfoId) {
        this.userInfosByUserInfoId = userInfosByUserInfoId;
    }

    @ManyToOne
    @JoinColumn(name = "areaID", referencedColumnName = "ID", insertable = false, updatable = false)
    public Areas getAreasByAreaId() {
        return areasByAreaId;
    }

    public void setAreasByAreaId(Areas areasByAreaId) {
        this.areasByAreaId = areasByAreaId;
    }
}
