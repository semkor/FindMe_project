package com.findme.B_models;

import com.findme.AA_ENUM.Status;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "AD_RELATIONSHIP")
public class Relationship {
    private Long id;
    private String userFromId;
    private String userToId;
    private Status status;
    private Date DateChangeStatus;

    //------------------------------------------------------------------------------------------------------------------
    public Relationship() {
    }

    public Relationship(String userFromId, String userToId, Status status) {
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.status = status;
    }

    public Relationship(String userFromId, String userToId, Status status, Date dateChangeStatus) {
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.status = status;
        this.DateChangeStatus = dateChangeStatus;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "USER_FROM_ID")
    public String getUserFromId() {
        return userFromId;
    }

    @Column(name = "USER_TO_ID")
    public String getUserToId() {
        return userToId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    public Status getStatus() {
        return status;
    }

    @Column(name = "DATA_CHANGE_STATUS")
    public Date getDateChangeStatus() {
        return DateChangeStatus;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserFromId(String userFromId) {
        this.userFromId = userFromId;
    }

    public void setUserToId(String userToId) {
        this.userToId = userToId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDateChangeStatus(Date dateChangeStatus) {
        DateChangeStatus = dateChangeStatus;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", userFromId='" + userFromId + '\'' +
                ", userToId='" + userToId + '\'' +
                ", status=" + status +
                ", DateChangeStatus=" + DateChangeStatus +
                '}';
    }
}
