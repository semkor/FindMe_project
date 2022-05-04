package com.findme.B_models;

import com.findme.AA_ENUM.Status;
import javax.persistence.*;


@Entity
@Table(name = "AD_RELATIONSHIP")
public class Relationship {
    private Long id;
    private String userFromId;
    private String userToId;
    private Status status;

    //------------------------------------------------------------------------------------------------------------------
    public Relationship() {

    }

    public Relationship(String userFromId, String userToId, Status status) {
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.status = status;
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

    @Override
    public String toString() {
        return "Relationship{" +
                "userFromId='" + userFromId + '\'' +
                ", userToId='" + userToId + '\'' +
                ", status=" + status +
                '}';
    }
}
