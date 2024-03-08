--liquibase formatted sql
--changeset mmstr:22.02.2024-insert-data

insert into links (url)
values ('ahahha'), ('nonononon'), ('gooooooooo');

insert into chat (chat_id, link_id)
values (15, 1), (15, 2), (15, 3);
