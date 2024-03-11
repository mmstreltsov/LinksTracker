CREATE SEQUENCE chat_sequence START 1 INCREMENT BY 1;
ALTER TABLE chat ALTER COLUMN id SET DEFAULT nextval('chat_sequence');


CREATE SEQUENCE links_sequence START 1 INCREMENT BY 1;
ALTER TABLE links ALTER COLUMN id SET DEFAULT nextval('links_sequence');