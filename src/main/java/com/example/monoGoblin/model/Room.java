package com.example.monoGoblin.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UUID administrator_uuid;

    @ElementCollection
    @Column(nullable = false)
    private List<UUID> users_uuid;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;

    private Date end_at;

    @Column(columnDefinition = "boolean default false")
    private Boolean active;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getAdministrator_uuid() {
        return administrator_uuid;
    }

    public void setAdministrator_uuid(UUID administrator_uuid) {
        this.administrator_uuid = administrator_uuid;
    }

    public List<UUID> getUsers_uuid() {
        return users_uuid;
    }

    public void setUsers_uuid(List<UUID> users) {
        this.users_uuid = users;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
