drop table article;

create table if not exists article
(
    id         bigint generated by default as identity,
    created_at timestamp(6),
    updated_at timestamp(6),
    author_id  bigint,
    body       varchar(255),
    title      varchar(255),
    primary key (id)
);

INSERT INTO article(title, body, author_id, created_at, updated_at) VALUES ('title 1', 'body 1', 1234, current_timestamp(), current_timestamp());