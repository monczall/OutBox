package main.java.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "package_type", schema = "outbox", catalog = "")
public class PackageType {
    private int typeId;
    private String sizeName;
    private String size;
    private String weight;
    private String price;

    @Id
    @Column(name = "typeID")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "size_name")
    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Basic
    @Column(name = "size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageType that = (PackageType) o;
        return typeId == that.typeId && Objects.equals(sizeName, that.sizeName) && Objects.equals(size, that.size) && Objects.equals(weight, that.weight) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, sizeName, size, weight, price);
    }
}
