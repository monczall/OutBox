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
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    @OneToMany(mappedBy = "packageTypeByTypeId")
    public Collection<Packages> getPackagesById() {
        return packagesById;
    }

    public void setPackagesById(Collection<Packages> packagesById) {
        this.packagesById = packagesById;
    }
}
