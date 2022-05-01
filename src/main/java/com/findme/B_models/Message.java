package com.findme.B_models;

import com.findme.hw.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AB_MESSAGE")
public class Message {
    private Long id;
    private String text;
    private Date dateSent;
    private Date dateRead;
    private User userTo;
    private User userFrom;

    //------------------------------------------------------------------------------------------------------------------
    public Message() {
    }

    public Message(Long id, String text, Date dateSent, Date dateRead, User userTo, User userFrom) {
        this.id = id;
        this.text = text;
        this.dateSent = dateSent;
        this.dateRead = dateRead;
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    @Column(name="TEXT")
    public String getText() {
        return text;
    }

    @Column(name="DATE_SENT")
    public Date getDateSent() {
        return dateSent;
    }

    @Column(name="DATE_READ")
    public Date getDateRead() {
        return dateRead;
    }

    @ManyToOne
    @JoinColumn(name="USER_TO")
    public User getUserTo() {
        return userTo;
    }

    @ManyToOne
    @JoinColumn(name = "USER_FROM")
    public User getUserFrom() {
        return userFrom;
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

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateSent=" + dateSent +
                ", dateRead=" + dateRead +
                ", userTo=" + userTo +
                ", userFrom=" + userFrom +
                '}';
    }
}
