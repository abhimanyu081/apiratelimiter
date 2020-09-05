package com.abhimanyu.apiratelimiter.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="table_api_access_quota")
public class TableApiAccessQuota {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column(name="api_access_count")
    private int apiAccessCount;

    @Column(name="last_access_time")
    private LocalDateTime lastAccessTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_user_id", nullable = false)
    private TableUser tableUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_id", nullable = false)
    private TableAPI tableAPI;

    @CreatedDate
    @Column(name = "reset_at")
    private LocalDateTime resetAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LocalDateTime getResetAt() {
        return resetAt;
    }

    public void setResetAt(LocalDateTime resetAt) {

        this.resetAt = resetAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TableAPI getTableAPI() {
        return tableAPI;
    }

    public void setTableAPI(TableAPI tableAPI) {
        this.tableAPI = tableAPI;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableUser getTableUser() {
        return tableUser;
    }

    public void setTableUser(TableUser tableUser) {
        this.tableUser = tableUser;
    }

    public int getApiAccessCount() {
        return apiAccessCount;
    }

    public void setApiAccessCount(int apiAccessCount) {
        this.apiAccessCount = apiAccessCount;
    }

    public void incrementApiAccessCount() {


        this.apiAccessCount ++;
    }

    public void resetApiAccessCount() {


        this.apiAccessCount =0;
    }

    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(LocalDateTime lastAccessTime) {
        if(lastAccessTime==null){
            lastAccessTime=LocalDateTime.now();
        }
        this.lastAccessTime = lastAccessTime;
    }

    public void resetLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }

}
