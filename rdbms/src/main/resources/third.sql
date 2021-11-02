--liquibase formatted sql

--changeset .

create INDEX some_index on acount1 (id, version);