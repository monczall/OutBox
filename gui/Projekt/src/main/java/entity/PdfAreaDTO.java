package main.java.entity;

import java.util.Date;

public class PdfAreaDTO {


    private String areaName;
    private Long smallPackages;
    private Long mediumPackages;
    private Long bigPackages;


    public PdfAreaDTO(String areaName, Long smallPackages, Long mediumPackages, Long bigPackages) {
        this.areaName = areaName;
        this.smallPackages = smallPackages;
        this.mediumPackages = mediumPackages;
        this.bigPackages = bigPackages;

    }

    public String getAreaName() {
        return areaName;
    }

    public Long getSmallPackages() {
        return smallPackages;
    }

    public Long getMediumPackages() {
        return mediumPackages;
    }

    public Long getBigPackages() {
        return bigPackages;
    }


}

