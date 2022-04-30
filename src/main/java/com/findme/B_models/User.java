package com.findme.B_models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "AA_USER")
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    //TODO from existed data
    private String country;
    private String city;

    private Integer age;
    private Date dateRegistered;
    private Date dateLastActive;
    //TODO enum
    private String relationshipStatus;
    private String religion;
    //TODO from existed data
    private String school;
    private String university;

    private List<Message> messagesSent;
    private List<Message> messagesReceived;
    private List<Post> postList;

    //private String[] interests;

    //------------------------------------------------------------------------------------------------------------------
    public User() {
    }

    public User(Long id, String firstName, String lastName, String phone, String email,String country, String city, Integer age, Date dateRegistered, Date dateLastActive, String relationshipStatus, String religion, String school, String university, List<Message> messagesSent, List<Message> messagesReceived, List<Post> postList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.age = age;
        this.dateRegistered = dateRegistered;
        this.dateLastActive = dateLastActive;
        this.relationshipStatus = relationshipStatus;
        this.religion = religion;
        this.school = school;
        this.university = university;
        this.messagesSent = messagesSent;
        this.messagesReceived = messagesReceived;
        this.postList = postList;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    @Column(name="FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    @Column(name="LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name="PHONE")
    public String getPhone() {
        return phone;
    }

    @Column(name="EMAIL")
    public String getEmail() {
        return email;
    }

    @Column(name="COUNTRY")
    public String getCountry() {
        return country;
    }

    @Column(name="CITY")
    public String getCity() {
        return city;
    }

    @Column(name="AGE")
    public Integer getAge() {
        return age;
    }

    @Column(name="DATA_REGISTERED")
    public Date getDateRegistered() {
        return dateRegistered;
    }

    @Column(name="DATA_LAST_ACTIVE")
    public Date getDateLastActive() {
        return dateLastActive;
    }

    @Column(name="RELATIONSHIP_STATUS")
    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    @Column(name="RELIGION")
    public String getReligion() {
        return religion;
    }

    @Column(name="SCHOOL")
    public String getSchool() {
        return school;
    }

    @Column(name="UNIVERSITY")
    public String getUniversity() {
        return university;
    }

    @OneToMany (mappedBy = "userTo", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    @OneToMany (mappedBy = "userFrom", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    public List<Message> getMessagesReceived() {
        return messagesReceived;
    }

    @OneToMany (mappedBy = "userPosted", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    public List<Post> getPostList() {
        return postList;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setDateLastActive(Date dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public void setMessagesReceived(List<Message> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", dateRegistered=" + dateRegistered +
                ", dateLastActive=" + dateLastActive +
                ", relationshipStatus='" + relationshipStatus + '\'' +
                ", religion='" + religion + '\'' +
                ", school='" + school + '\'' +
                ", university='" + university + '\'' +
                ", messagesSent=" + messagesSent +
                ", messagesReceived=" + messagesReceived +
                '}';
    }
}
