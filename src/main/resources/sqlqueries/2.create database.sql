CREATE DATABASE clevertec_bank with encoding 'utf8';
CREATE USER clevertec_user with password 'password';
GRANT all privileges on database clevertec_bank to clevertec_user;
GRANT all privileges on SCHEMA public to clevertec_user;
