--liquibase formatted sql
--changeset mmstr:22.02.2024-create-links-table
create table if not exists links (
    id int primary key,
    url varchar(255) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    checked_at TIMESTAMP WITH TIME ZONE
);