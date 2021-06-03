package main.java.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "package_history", schema = "outbox", catalog = "")
public class PackageHistory {
    private int id;
    private int packageId;
    private String status;
    private Timestamp date;
    private Packages packagesByPackageId;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "packageID", nullable = false)
    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageHistory that = (PackageHistory) o;
        return id == that.id && packageId == that.packageId && Objects.equals(status, that.status) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, packageId, status, date);
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
