package main.java.controllers.courier;

public class Test {
    private int number;
    private String name;
    private String city;
    private String address;
    private String telephone;
    private String date;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Test(int number, String name, String city, String address, String telephone, String date) {

        this.number = number;
        this.name = name;
        this.city = city;
        this.address = address;
        this.telephone = telephone;
        this.date = date;
    }
}