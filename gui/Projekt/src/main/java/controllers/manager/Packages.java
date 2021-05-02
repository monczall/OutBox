package main.java.controllers.manager;

public class Packages {

    private String tableNumber;
    private String tableStatus;
    private String tableType;
    private String tableSenderName;
    private String tableSenderSurname;
    private String tableSenderCity;
    private String tableSenderPhone;
    private String tableRecipientName;
    private String tableRecipientSurname;
    private String tableRecipientCity;
    private String tableRecipientPhone;

    public Packages(String tableNumber, String tableStatus, String tableType, String tableSenderName,
                    String tableSenderSurname, String tableSenderCity, String tableSenderPhone,
                    String tableRecipientName, String tableRecipientSurname, String tableRecipientCity,
                    String tableRecipientPhone) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
        this.tableType = tableType;
        this.tableSenderName = tableSenderName;
        this.tableSenderSurname = tableSenderSurname;
        this.tableSenderCity = tableSenderCity;
        this.tableSenderPhone = tableSenderPhone;
        this.tableRecipientName = tableRecipientName;
        this.tableRecipientSurname = tableRecipientSurname;
        this.tableRecipientCity = tableRecipientCity;
        this.tableRecipientPhone = tableRecipientPhone;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableSenderName() {
        return tableSenderName;
    }

    public void setTableSenderName(String tableSenderName) {
        this.tableSenderName = tableSenderName;
    }

    public String getTableSenderSurname() {
        return tableSenderSurname;
    }

    public void setTableSenderSurname(String tableSenderSurname) {
        this.tableSenderSurname = tableSenderSurname;
    }

    public String getTableSenderCity() {
        return tableSenderCity;
    }

    public void setTableSenderCity(String tableSenderCity) {
        this.tableSenderCity = tableSenderCity;
    }

    public String getTableSenderPhone() {
        return tableSenderPhone;
    }

    public void setTableSenderPhone(String tableSenderPhone) {
        this.tableSenderPhone = tableSenderPhone;
    }

    public String getTableRecipientName() {
        return tableRecipientName;
    }

    public void setTableRecipientName(String tableRecipientName) {
        this.tableRecipientName = tableRecipientName;
    }

    public String getTableRecipientSurname() {
        return tableRecipientSurname;
    }

    public void setTableRecipientSurname(String tableRecipientSurname) {
        this.tableRecipientSurname = tableRecipientSurname;
    }

    public String getTableRecipientCity() {
        return tableRecipientCity;
    }

    public void setTableRecipientCity(String tableRecipientCity) {
        this.tableRecipientCity = tableRecipientCity;
    }

    public String getTableRecipientPhone() {
        return tableRecipientPhone;
    }

    public void setTableRecipientPhone(String tableRecipientPhone) {
        this.tableRecipientPhone = tableRecipientPhone;
    }
}
