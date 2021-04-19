package main.java.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "package_history", schema = "outbox")
public class PackageHistory {
    private int historyId;
    private int packageId;
    private String status;
    private Timestamp timestamp;

    @Id
    @Column(name = "historyID")
    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageHistory that = (PackageHistory) o;
        return historyId == that.historyId && packageId == that.packageId && Objects.equals(status, that.status) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyId, packageId, status, timestamp);
    }
}
