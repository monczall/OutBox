package main.java.entity;

public class PieChartDTO {
    private final String type;
    private final Long quantity;

    public PieChartDTO(String type, Long quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public Long getQuantity() {
        return quantity;
    }


}
