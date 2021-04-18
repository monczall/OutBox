package main.java.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Packages {
    private int packageId;
    private int typeId;
    private int userId;
    private int userInfoId;
    private String packageNumber;
    private String timeOfPlannedDelivery;
    private String additionalComment;

    @Id
    @Column(name = "packageID")
    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @Basic
    @Column(name = "typeID")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_infoID")
    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Basic
    @Column(name = "package_number")
    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    @Basic
    @Column(name = "time_of_planned_delivery")
    public String getTimeOfPlannedDelivery() {
        return timeOfPlannedDelivery;
    }

    public void setTimeOfPlannedDelivery(String timeOfPlannedDelivery) {
        this.timeOfPlannedDelivery = timeOfPlannedDelivery;
    }

    @Basic
    @Column(name = "additional_comment")
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
        return packageId == packages.packageId && typeId == packages.typeId && userId == packages.userId && userInfoId == packages.userInfoId && Objects.equals(packageNumber, packages.packageNumber) && Objects.equals(timeOfPlannedDelivery, packages.timeOfPlannedDelivery) && Objects.equals(additionalComment, packages.additionalComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, typeId, userId, userInfoId, packageNumber, timeOfPlannedDelivery, additionalComment);
    }
}
