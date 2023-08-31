CREATE TABLE clients
(
    id            serial      primary key,
    firstname     character varying(100),
    surname       character varying(100),
    patronymic    character varying(100)
);
CREATE TABLE banks
(
    id            serial      primary key,
    name          character varying(100),
    IBAN_code     character varying(14) -- MAX 14 FOR IRISH IBAN
);

create table currencies(
    id  serial primary key,
    code character varying(3),
    name character varying(60) -- max? franc de la Coopération financière en Afrique-centrale
);

CREATE TABLE accounts
(
    id            serial      primary key,
    IBAN_code     character varying(34),
    client_id     int REFERENCES clients(id) on delete restrict,
    bank_id       int REFERENCES banks(id) on delete restrict,
    amount        numeric (26,7),
    opening_date  date,
    currency_id   int REFERENCES currencies(id) on delete restrict
);


CREATE TABLE transaction_type
(
    id            serial      primary key,
    description   character   varying(100)
);

CREATE TABLE transaction
(
    id            serial      primary key,
    account_id_from     int REFERENCES accounts(id) on delete restrict,
    account_id_to       int REFERENCES accounts(id) on delete restrict,
    amount        numeric (26,7),
    time          timestamp,
    transaction_type_id int REFERENCES transaction_type(id) on delete restrict
);

