package com.findme.AA_ENUM;

public enum Status {
    NOT_FRIENDS,                // не друзья (никогда не дружили) - по умолчанию, если нет в таблице Relationship
        REQUEST,                    // запрос отправлен
        REQUEST_DENIED,             // запрос отклонен
        FORMER_FRIENDS,             // бывший друг
        FRIENDS                     // друзья
}