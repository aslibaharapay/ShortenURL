drop table if exists hibernate_sequence cascade;

drop table if exists urls cascade;

create table if not exists hibernate_sequence
(
    next_val bigint NOT NULL
);

create table if not exists urls
(
    id           bigint       not null
        primary key,
    created_date date         not null,
    expires_date date         null,
    long_url     varchar(255) not null,
    request_ip   varchar(255) null
)
    PARTITION BY KEY ()
        PARTITIONS 2;

INSERT INTO hibernate_sequence(next_val)
VALUES (0);

SET GLOBAL event_scheduler = ON;

SHOW PROCESSLIST;

CREATE EVENT deleteExpireRecord
    ON SCHEDULE EVERY 1 DAY
        STARTS CURRENT_TIMESTAMP
        ENDS CURRENT_TIMESTAMP + INTERVAL 1 HOUR
    DO
    DELETE
    FROM shortenurldb.urls
    WHERE urls.expires_date < NOW();