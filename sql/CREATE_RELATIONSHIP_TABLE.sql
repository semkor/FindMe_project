USE database_name;

CREATE TABLE AD_RELATIONSHIP (
    ID INT AUTO_INCREMENT,
        CONSTRAINT AD_RELATIONSHIP_ID PRIMARY KEY (ID),
    USER_FROM_ID VARCHAR(50),
    USER_TO_ID VARCHAR(50),
    STATUS ENUM ('NOT_FRIENDS','REQUEST','REQUEST_DENIED','FORMER_FRIENDS','FRIENDS')
);

# добавление даты изменения статуса
ALTER TABLE AD_RELATIONSHIP ADD DATA_CHANGE_STATUS DATE AFTER STATUS;




SELECT * FROM AD_RELATIONSHIP;

#  поиск друзей
SELECT * FROM AD_RELATIONSHIP WHERE USER_FROM_ID = ? AND STATUS = 'FRIENDS';
#  поиск кто мне оптравил запрос
SELECT * FROM AD_RELATIONSHIP WHERE USER_TO_ID = ? AND STATUS = 'REQUEST';
#  поиск кому я отправил запрос
SELECT * FROM AD_RELATIONSHIP WHERE USER_FROM_ID = ? AND STATUS = 'REQUEST';

# определение количества  заявок в друзья у USER_FROM
SELECT COUNT(STATUS) FROM AD_RELATIONSHIP WHERE USER_FROM_ID = ? AND STATUS = 'REQUEST' GROUP BY STATUS;
#  поиск наличия цепочки юзеров
SELECT * FROM AD_RELATIONSHIP WHERE (USER_FROM_ID = ? AND USER_TO_ID = ?) OR (USER_TO_ID = ? AND USER_FROM_ID = ?);
#  определяем сколько друзей
SELECT COUNT(STATUS) FROM AD_RELATIONSHIP WHERE (USER_FROM_ID = ? OR USER_TO_ID = ?)  AND STATUS = 'FRIENDS' GROUP BY STATUS;

#  дружат юзеры или нет
SELECT * FROM AD_RELATIONSHIP WHERE (USER_FROM_ID = ? AND USER_TO_ID = ? AND STATUS = 'FRIENDS' ) OR (USER_TO_ID = ? AND USER_FROM_ID = ? AND STATUS = 'FRIENDS');

#  спиоск, кто является другом
SELECT * FROM AD_RELATIONSHIP WHERE ( USER_FROM_ID = ? OR USER_TO_ID = ?) AND STATUS = 'FRIENDS';