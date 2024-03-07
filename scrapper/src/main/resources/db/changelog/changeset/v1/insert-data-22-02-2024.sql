--liquibase formatted sql
--changeset mmstr:22.02.2024-insert-data

insert into links
values (1, 'ahahha'), (2, 'nonononon'), (3, 'gooooooooo');

insert into chat
values (1, 15, 1), (2, 15, 2), (3, 15, 3);
