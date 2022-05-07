USE database_name;

CREATE TABLE AA_USER (
    ID INT AUTO_INCREMENT,
        CONSTRAINT AA_USER_ID PRIMARY KEY (ID),
    FIRST_NAME VARCHAR(40),
    LAST_NAME VARCHAR(40),
    PHONE VARCHAR(30),
    COUNTRY VARCHAR(30),
    CITY VARCHAR(30),
    AGE INT,
    DATA_REGISTERED DATE,
    DATA_LAST_ACTIVE DATE,
    RELATIONSHIP_STATUS VARCHAR(40),
    RELIGION VARCHAR(40),
    SCHOOL VARCHAR(40),
    UNIVERSITY VARCHAR(40)
);

# добавление email
ALTER TABLE AA_USER ADD EMAIL VARCHAR(40) AFTER PHONE;
# добавление login
ALTER TABLE AA_USER ADD LOGIN VARCHAR(40) AFTER ID;
# добавление password
ALTER TABLE AA_USER ADD PASSWORD VARCHAR(40) AFTER LOGIN;



SELECT * FROM  AA_USER;

# проверка наличия телефона
SELECT * FROM AA_USER WHERE PHONE = ?;
# проверка наличия email
SELECT * FROM AA_USER WHERE EMAIL = ?;

# проверка при входе в систему
SELECT * FROM AA_USER WHERE LOGIN = ? AND PASSWORD = ?;


