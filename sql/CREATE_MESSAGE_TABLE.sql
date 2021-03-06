USE database_name;

CREATE TABLE AB_MESSAGE (
    ID INT AUTO_INCREMENT,
        CONSTRAINT AB_MESSAGE_ID PRIMARY KEY (ID),
    USER_FROM INT,
        CONSTRAINT USER_FROM_FK FOREIGN KEY (USER_FROM) REFERENCES AA_USER(ID),
    USER_TO INT,
        CONSTRAINT USER_TO_FK FOREIGN KEY (USER_TO) REFERENCES AA_USER(ID),
    TEXTS VARCHAR(140),
    DATE_SENT DATE,
    DATE_EDITED DATE,
    DATE_READ DATE,
    DATE_DELETED DATE,
    CONDITION_MESSAGE BOOLEAN
);


SELECT  * FROM AB_MESSAGE;
# добавил перечисление
ALTER TABLE AB_MESSAGE DROP COLUMN CONDITION_MESSAGE;
ALTER TABLE AB_MESSAGE ADD COLUMN STATUS_MESSAGE ENUM ('READ','UNREAD','EDITED');

# поиск не удаленных сообщений (учитывается лимит поиска)
SELECT * FROM AB_MESSAGE WHERE ((USER_FROM = ? AND USER_TO = ?) OR (USER_TO = ? AND USER_FROM = ?))  AND DATE_DELETED IS NULL LIMIT ?;

# обновление даты удаления на текущую дату
UPDATE AB_MESSAGE SET DATE_DELETED = CURRENT_DATE WHERE ((USER_FROM = ? AND USER_TO = ?) OR (USER_TO = ? AND USER_FROM = ?))  AND DATE_DELETED IS NULL;