package com.findme.HW;

import com.findme.B_models.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "AC_POST")
public class Post implements Comparable<Post> {
    private Long id;
    private User userPosted;
    private User userPagePosted;
    private String message;
    private Date datePosted;
    private String location;

    private List<User> userTagged;

    //------------------------------------------------------------------------------------------------------------------
    public Post() {
    }

    public Post(Long id, User userPosted, User userPagePosted, String message, Date datePosted, String location, List<User> userTagged) {
        this.id = id;
        this.userPosted = userPosted;
        this.userPagePosted = userPagePosted;
        this.message = message;
        this.datePosted = datePosted;
        this.location = location;
        this.userTagged = userTagged;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_POSTED")
    public User getUserPosted() {
        return userPosted;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_PAGE_POSTED")
    public User getUserPagePosted() {
        return userPagePosted;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    @Column(name = "DATA_POSTED")
    public Date getDatePosted() {
        return datePosted;
    }

    @Column(name = "LOCATION")
    public String getLocation() {
        return location;
    }

    @ManyToMany (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable (name="AE_USER_POST",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn (name = "USER_ID"))
    public List<User> getUserTagged() {
        return userTagged;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }

    public void setUserPagePosted(User userPagePosted) {
        this.userPagePosted = userPagePosted;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUserTagged(List<User> userTagged) {
        this.userTagged = userTagged;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public int compareTo(Post o) {
        int res=-1;
        if(o.getDatePosted().getTime()>this.datePosted.getTime())
            res = 1;
        else if(o.getDatePosted().getTime()<this.datePosted.getTime())
            res = -1;
        return res;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userPosted=" + userPosted +
                ", userPagePosted=" + userPagePosted +
                ", message='" + message + '\'' +
                ", datePosted=" + datePosted +
                ", location='" + location + '\'' +
                '}';
    }
}
