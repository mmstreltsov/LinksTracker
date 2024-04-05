--liquibase formatted sql
--changeset mmstr:22.02.2024-create-links-table
create table if not exists links (
    id bigint primary key,
    url varchar(255) NOT NULL,
    chat_id bigint NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    checked_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (chat_id) REFERENCES chat(id) ON DELETE CASCADE
);