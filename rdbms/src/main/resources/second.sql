--liquibase formatted sql

--changeset .

alter table account1 add constraint checkForNegative check (amount >= 0);