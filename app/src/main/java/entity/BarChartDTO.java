package main.java.entity;

public class BarChartDTO {
    private final String day;
    private final Long quantity;

    public BarChartDTO(String day, Long quantity) {
        this.day = day;
        this.quantity = quantity;
    }

    public String getDay() {
        return day;
    }

    public Long getQuantity() {
        return quantity;
    }
}
