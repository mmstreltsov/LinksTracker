--liquibase formatted sql
--changeset mmstr:22.02.2024-insert-data

insert into links
values (1, 'ahahha'), (2, 'nonononon'), (3, 'gooooooooo');

insert into chat
values (1), (2), (555);

insert into chatToLinks
values (1, 1), (2, 1), (555, 1);