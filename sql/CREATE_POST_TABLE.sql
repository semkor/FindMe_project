USE database_name;

CREATE TABLE AC_POST (
    ID INT AUTO_INCREMENT,
    CONSTRAINT AC_POST_ID PRIMARY KEY (ID),
    MESSAGE LONGTEXT,
    DATA_POSTED DATE,
    USER_POSTED INT,
        CONSTRAINT USER_POSTED_FK FOREIGN KEY (USER_POSTED) REFERENCES AA_USER(ID)
);