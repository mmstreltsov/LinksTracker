--liquibase formatted sql
--changeset mmstr:22.02.2024-insert-data

insert into chat (chat_id)
values (1), (555), (777);

insert into links (url, chat_id, updated_at)
values ('ahaha', 1, '2022-01-01T12:00:00+05:00'::TIMESTAMP WITH TIME ZONE),
       ('ohohoh', 2, '2022-01-01T12:00:00+05:00'::TIMESTAMP WITH TIME ZONE),
       ('nononono', 3, '2022-01-01T12:00:00+05:00'::TIMESTAMP WITH TIME ZONE);


