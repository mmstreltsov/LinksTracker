--liquibase formatted sql
--changeset mmstr:22.02.2024-create-chat-table
create table if not exists chat(
    id bigint primary key,
    chat_id bigint unique
);