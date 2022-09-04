package main.java.entity;

public class AreasDTO {

    private int areaID;
    private final String name;
    private final String city;
    private final String voivodeship;
    private final String street;

    public AreasDTO(int areaID,String name, String city, String voivodeship, String street) {
        this.areaID = areaID;
        this.name = name;
        this.city = city;
        this.voivodeship = voivodeship;
        this.street = street;
    }

    public int getAreaID() {
        return areaID;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public String getStreet() {
        return street;
    }
}
