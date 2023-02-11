create database ytdl;
use ytdl;
create table videos
(
    id         int auto_increment,
    url        text null,
    title      text null,
    only_audio bool null,
    primary key (id)
);
