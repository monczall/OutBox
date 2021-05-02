package main.java.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Packages {
    private int id;
    private int typeId;
    private int userId;
    private int userInfoId;
    private String email;
    private String packageNumber;
    private String timeOfPlannedDelivery;
    private String additionalComment;
    private Collection<PackageHistory> packageHistoriesById;
    private PackageType packageTypeByTypeId;
    private Users usersByUserId;
    private UserInfos userInfosByUserInfoId;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "typeID", nullable = false)
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "userID", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    @Column(name = "email", nullable = false, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "package_number", nullable = false, length = 64)
    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    @Basic
    @Column(name = "time_of_planned_delivery", nullable = false, length = 64)
    public String getTimeOfPlannedDelivery() {
        return timeOfPlannedDelivery;
    }

    public void setTimeOfPlannedDelivery(String timeOfPlannedDelivery) {
        this.timeOfPlannedDelivery = timeOfPlannedDelivery;
    }

    @Basic
    @Column(name = "additional_comment", nullable = true, length = 512)
    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packages packages = (Packages) o;
        return id == packages.id && typeId == packages.typeId && userId == packages.userId && userInfoId == packages.userInfoId && Objects.equals(email, packages.email) && Objects.equals(packageNumber, packages.packageNumber) && Objects.equals(timeOfPlannedDelivery, packages.timeOfPlannedDelivery) && Objects.equals(additionalComment, packages.additionalComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, userId, userInfoId, email, packageNumber, timeOfPlannedDelivery, additionalComment);
    }

    @OneToMany(mappedBy = "packagesByPackageId")
    public Collection<PackageHistory> getPackageHistoriesById() {
        return packageHistoriesById;
    }

    public void setPackageHistoriesById(Collection<PackageHistory> packageHistoriesById) {
        this.packageHistoriesById = packageHistoriesById;
    }

    @ManyToOne
    @JoinColumn(name = "typeID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public PackageType getPackageTypeByTypeId() {
        return packageTypeByTypeId;
    }

    public void setPackageTypeByTypeId(PackageType packageTypeByTypeId) {
        this.packageTypeByTypeId = packageTypeByTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public Users getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "user_infoID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public UserInfos getUserInfosByUserInfoId() {
        return userInfosByUserInfoId;
    }

    public void setUserInfosByUserInfoId(UserInfos userInfosByUserInfoId) {
        this.userInfosByUserInfoId = userInfosByUserInfoId;
    }
}