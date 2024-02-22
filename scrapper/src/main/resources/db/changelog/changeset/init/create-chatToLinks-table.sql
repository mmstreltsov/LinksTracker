--liquibase formatted sql
--changeset mmstr:22.02.2024-create-chatToLinks-table
create table chatToLinks(
    chat_id int,
    link_id int,
    FOREIGN KEY (chat_id) REFERENCES chat(id),
    FOREIGN KEY (link_id) REFERENCES links(id)
);