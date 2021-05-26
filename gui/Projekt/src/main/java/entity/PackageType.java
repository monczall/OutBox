package main.java.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "package_type", schema = "outbox", catalog = "")
public class PackageType {
    private int id;
    private String sizeName;
    private String size;
    private String weight;
    private String price;
    private Collection<Packages> packagesById;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "size_name", nullable = false, length = 16)
    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Basic
    @Column(name = "size", nullable = false, length = 16)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "weight", nullable = false, length = 16)
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "price", nullable = false, length = 16)
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
        return id == that.id && Objects.equals(sizeName, that.sizeName) && Objects.equals(size, that.size) && Objects.equals(weight, that.weight) && Objects.equals(price, that.price);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, sizeName, size, weight, price);
    }

    @OneToMany(mappedBy = "packageTypeByTypeId")
    public Collection<Packages> getPackagesById() {
        return packagesById;
    }

    public void setPackagesById(Collection<Packages> packagesById) {
        this.packagesById = packagesById;
    }
}
