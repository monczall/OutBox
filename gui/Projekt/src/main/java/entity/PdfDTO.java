package main.java.entity;

import java.util.Date;

public class PdfDTO {

    private String packageNumber;
    private String size;
    private String city;
    private String voivodeship;
    private Date date;
    private int packageID;
    private Integer areaID;


    public PdfDTO(String packageNumber, String size, String city, String voivodeship, Date date, Integer areaID) {
        this.packageNumber = packageNumber;
        this.size = size;
        this.city = city;
        this.voivodeship = voivodeship;
        this.date = date;
        this.areaID = areaID;
    }

    public PdfDTO(String packageNumber, String size, String city, String voivodeship, Date date, int packageID, Integer areaID) {
        this.packageNumber = packageNumber;
        this.size = size;
        this.city = city;
        this.voivodeship = voivodeship;
        this.date = date;
        this.packageID = packageID;
        this.areaID = areaID;
    }

    public int getPackageID() {
        return packageID;
    }

    public Integer getAreaID() {
        return areaID;
    }

    public void setAreaID(Integer areaID) {
        this.areaID = areaID;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public String getSize() {
        return size;
    }

    public String getCity() {
        return city;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public Date getDate() {
        return date;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
