package main.java.entity;

public class PackagesDTO {

    private int userInfosId;
    private int packagesId;
    private String packageNumber;
    private String timeOfPlannedDelivery;
    private String name;
    private String surname;
    private String phoneNumber;
    private String streetAndNumber;
    private String city;
    private String status;
    private String additionalComment;
    private String email;
    private String sizeName;
    private String recipentName;
    private String recipentSurname;

    public PackagesDTO(int userInfosId, int packagesId, String packageNumber, String timeOfPlannedDelivery,
                       String name, String surname, String phoneNumber, String streetAndNumber, String city,
                       String status, String additionalComment, String email) {
        this.userInfosId = userInfosId;
        this.packagesId = packagesId;
        this.packageNumber = packageNumber;
        this.timeOfPlannedDelivery = timeOfPlannedDelivery;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.streetAndNumber = streetAndNumber;
        this.city = city;
        this.status = status;
        this.additionalComment = additionalComment;
        this.email = email;
    }

    public PackagesDTO(int packagesId, String packageNumber, String name, String surname, String status,
                       String sizeName,
                       String recipentName, String recipentSurname) {
        this.packagesId = packagesId;
        this.packageNumber = packageNumber;
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.sizeName = sizeName;
        this.recipentName = recipentName;
        this.recipentSurname = recipentSurname;
    }

    public int getUserInfosId() {
        return userInfosId;
    }

    public int getPackagesId() {
        return packagesId;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public String getTimeOfPlannedDelivery() {
        return timeOfPlannedDelivery;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStatus() {
        return status;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public String getEmail() {
        return email;
    }

    public String getSizeName() {
        return sizeName;
    }

    public String getRecipentName() {
        return recipentName;
    }

    public String getRecipentSurname() {
        return recipentSurname;
    }
}
