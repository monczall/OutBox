package main.java.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "package_history", schema = "outbox")
public class PackageHistory {
    private int id;
    private int packageId;
    private String status;
    private Timestamp timestamp;
    private Packages packagesByPackageId;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "packageID")
    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return timestamp;
    }

    public void setDate(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @ManyToOne
    @JoinColumn(name = "packageID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public Packages getPackagesByPackageId() {
        return packagesByPackageId;
    }

    public void setPackagesByPackageId(Packages packagesByPackageId) {
        this.packagesByPackageId = packagesByPackageId;
    }
}
