package main.java.entity;

public class PdfAreaDTO {


    private final String areaName;
    private final Long smallPackages;
    private final Long mediumPackages;
    private final Long bigPackages;


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

