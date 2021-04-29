package main.java.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "packages")
@SecondaryTables({

        @SecondaryTable(name = "user_infos", pkJoinColumns = {
                @PrimaryKeyJoinColumn(name = "ID")
        }),
        @SecondaryTable(name = "package_history", pkJoinColumns = {
                @PrimaryKeyJoinColumn(name = "packageId")
        })
})
public class Packages {
    private int id;
    private int typeId;
    private int userId;
    private int userInfoId;
    private String email;
    private String packageNumber;
    private String timeOfPlannedDelivery;
    private String status;
    private String additionalComment;
    private String name;
    private String surname;
    private String city;
    private String address;
    private String phone;
    private String voivodeship;
    private Timestamp timestamp;
    private Collection<PackageHistory> packageHistoriesById;
    private PackageType packageTypeByTypeId;
    private Users usersByUserId;
    private UserInfos userInfosByUserInfoId;

    public Packages() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "name", table = "user_infos")
    public String getName() {
        return getUserInfosByUserInfoId().getName();
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "surname", table = "user_infos")
    public String getSurname() {
        return getUserInfosByUserInfoId().getSurname();
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Column(name = "city", table = "user_infos")
    public String getCity() {
        return getUserInfosByUserInfoId().getCity();
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column(name = "street_and_number", table = "user_infos")
    public String getAddress() {
        return getUserInfosByUserInfoId().getStreetAndNumber();
    }

    @Column(name = "voivodeship", table = "user_infos")
    public String getVoivodeship() {
        return getUserInfosByUserInfoId().getVoivodeship();
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone_number", table = "user_infos")
    public String getPhone() {
        return getUserInfosByUserInfoId().getPhoneNumber();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "status", table = "package_history")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "date", table = "package_history")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}