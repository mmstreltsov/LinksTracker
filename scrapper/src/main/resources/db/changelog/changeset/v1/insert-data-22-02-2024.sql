--liquibase formatted sql
--changeset mmstr:22.02.2024-insert-data

insert into links (url, updated_at)
values ('ahahha', '2022-01-01T12:00:00+05:00'::TIMESTAMP WITH TIME ZONE);

insert into chat (chat_id, link_id)
values (15, 1), (16, 1), (15, 1);
