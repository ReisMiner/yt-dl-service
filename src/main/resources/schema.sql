create database if not exists ytdl;
create table ytdl.videos
(
    id          int auto_increment
        primary key,
    url         text       null,
    title       text       null,
    channel     text       null,
    file_path   text       null,
    length      int        null,
    audio_only  tinyint(1) null,
    quality     int        null,
    is_in_queue tinyint(1) null
);


