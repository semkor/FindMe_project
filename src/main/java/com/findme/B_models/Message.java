package com.findme.B_models;

import com.findme.HW.StatusMessage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AB_MESSAGE")
public class Message {
    private Long id;
    private String text;
    private Date dateSent;
    private Date dateEdited;
    private Date dateRead;
    private Date dateDeleted;
    private User userFrom;
    private User userTo;
    private StatusMessage statusMessage;

    //------------------------------------------------------------------------------------------------------------------
    public Message() {
    }

    public Message(Long id, String text, Date dateSent, Date dateEdited, Date dateRead, Date dateDeleted, User userFrom, User userTo, StatusMessage statusMessage) {
        this.id = id;
        this.text = text;
        this.dateSent = dateSent;
        this.dateEdited = dateEdited;
        this.dateRead = dateRead;
        this.dateDeleted = dateDeleted;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.statusMessage = statusMessage;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "TEXTS")
    public String getText() {
        return text;
    }

    @Column(name = "DATE_SENT")
    public Date getDateSent() {
        return dateSent;
    }

    @Column(name = "DATE_EDITED")
    public Date getDateEdited() {
        return dateEdited;
    }

    @Column(name = "DATE_READ")
    public Date getDateRead() {
        return dateRead;
    }

    @Column(name = "DATE_DELETED")
    public Date getDateDeleted() {
        return dateDeleted;
    }

    @ManyToOne
    @JoinColumn(name = "USER_FROM")
    public User getUserFrom() {
        return userFrom;
    }

    @ManyToOne
    @JoinColumn(name = "USER_TO")
    public User getUserTo() {
        return userTo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_MESSAGE")
    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateSent=" + dateSent +
                ", dateEdited=" + dateEdited +
                ", dateRead=" + dateRead +
                ", dateDeleted=" + dateDeleted +
                ", userFrom=" + userFrom +
                ", userTo=" + userTo +
                '}';
    }
}
