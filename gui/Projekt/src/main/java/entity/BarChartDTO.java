package main.java.entity;

public class BarChartDTO {
    private String day;
    private Long quantity;

    public BarChartDTO(String day, Long quantity) {
        this.day = day;
        this.quantity = quantity;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
