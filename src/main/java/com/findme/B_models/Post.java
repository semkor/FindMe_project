package com.findme.B_models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AC_POST")
public class Post {
    private Long id;
    private String message;
    private Date datePosted;
    private User userPosted;
    //TODO
    //levels permissions
    //TODO
    //coments

    //------------------------------------------------------------------------------------------------------------------
    public Post() {
    }

    public Post(Long id, String message, Date datePosted, User userPosted) {
        this.id = id;
        this.message = message;
        this.datePosted = datePosted;
        this.userPosted = userPosted;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    @Column(name = "DATA_POSTED")
    public Date getDatePosted() {
        return datePosted;
    }

    @ManyToOne
    @JoinColumn(name = "USER_POSTED")
    public User getUserPosted() {
        return userPosted;
    }

    //------------------------------------------------------------------------------------------------------------------

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", datePosted=" + datePosted +
                '}';
    }
}
