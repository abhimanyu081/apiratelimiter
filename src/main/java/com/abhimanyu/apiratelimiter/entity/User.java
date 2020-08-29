package com.abhimanyu.apiratelimiter.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="api_access_count")
    private int apiAccessCount;

    @Column(name="last_access_time")
    private LocalDateTime lastAccessTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        this.lastAccessTime = lastAccessTime;
    }

    public void resetLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }
}
