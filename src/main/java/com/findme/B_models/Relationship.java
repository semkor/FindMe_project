package com.findme.B_models;

import com.findme.AA_ENUM.Status;

public class Relationship {
    private User userFrom;
    private User userTo;
    private Status status;

    //------------------------------------------------------------------------------------------------------------------
    public Relationship(User userFrom, User userTo, Status status) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.status = status;
    }

    //------------------------------------------------------------------------------------------------------------------
    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public Status getStatus() {
        return status;
    }

    //------------------------------------------------------------------------------------------------------------------

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
