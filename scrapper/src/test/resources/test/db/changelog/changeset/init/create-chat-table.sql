--liquibase formatted sql
--changeset mmstr:22.02.2024-create-chat-table
create table chat(
    id int primary key
);