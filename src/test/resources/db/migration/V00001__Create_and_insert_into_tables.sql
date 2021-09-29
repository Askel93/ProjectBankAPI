DROP TABLE IF EXISTS bank_cards;
DROP TABLE IF EXISTS bank_accounts;
DROP TABLE IF EXISTS legal_persons;
DROP TABLE IF EXISTS natural_persons;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS payment_systems;
DROP TABLE IF EXISTS bank_card_types;

CREATE TABLE users(
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      version BIGINT,
                      user_name VARCHAR(100),
                      password VARCHAR(255)
);
INSERT INTO users(version, user_name, password)
VALUES
    (0, 'user_name1', '{bcrypt}$2a$12$bOvHoH2FX./05XN03Ucn9uTYW6eLJ8Hp6waaoeLUtOfK0dIP0L/2m'),
    (0, 'user_name99', '{bcrypt}$2a$12$bOvHoH2FX./05XN03Ucn9uTYW6eLJ8Hp6waaoeLUtOfK0dIP0L/2m');


CREATE TABLE roles(
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      version BIGINT,
                      name VARCHAR(100)
);
INSERT INTO roles(version, name)
VALUES
    (0, 'access_to_bank_api'),
    (0, 'access_to_other_api');


CREATE TABLE users_roles(
                            role_id INT,
                            user_id INT,
                            version BIGINT,
                            primary key (role_id, user_id),
                            foreign key (role_id) references roles(id),
                            foreign key (user_id) references users(id)
);
CREATE INDEX users_roles_role_idx on users_roles(role_id);
CREATE INDEX users_roles_user_idx on users_roles(user_id);
INSERT INTO users_roles(version, role_id, user_id)
VALUES
    (0, SELECT id from roles OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY, SELECT id from users OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY),
    (0, SELECT id from roles OFFSET 1 ROWS FETCH NEXT 1 ROW ONLY, SELECT id from users OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY),
    (0, SELECT id from roles OFFSET 1 ROWS FETCH NEXT 1 ROW ONLY, SELECT id from users OFFSET 1 ROWS FETCH NEXT 1 ROW ONLY);



CREATE TABLE natural_persons(
                                id INT auto_increment PRIMARY KEY,
                                version BIGINT,
                                name VARCHAR(100),
                                surname VARCHAR(100),
                                patronymic VARCHAR(100),
                                birthdate DATE,
                                phone_number VARCHAR(15),
                                email VARCHAR(100)
);
INSERT INTO natural_persons(version, name, surname, patronymic, birthdate)
VALUES
    (0, 'Сергей', 'Сергеев', 'Сергеевич', '1993-07-10'),
    (0, 'Иван', 'Иванов', 'Иванович','1990-05-01'),
    (0, 'Петр', 'Петров', 'Петрович', '1992-09-30'),
    (0, 'Александр', 'Александров', 'Александрович', '1960-12-15'),
    (0, 'Валентина', 'Валентинова', 'Валентиновна', '1967-06-06');


CREATE TABLE bank_accounts(
                              id INT auto_increment PRIMARY KEY,
                              version BIGINT,
                              number VARCHAR(20) UNIQUE,
                              currency VARCHAR(10),
                              balance DOUBLE,
                              owner_id int,
                              foreign key (owner_id) references natural_persons(id)
);
CREATE INDEX bank_accounts_owner_idx on bank_accounts(owner_id);
INSERT INTO bank_accounts(version, number, currency, balance, owner_id)
VALUES
    (0, '40817810099910004312','RUB',100000.40, SELECT id from natural_persons OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY),
    (0, '40817810099910004313','EUR',800.40, SELECT id from natural_persons OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY),
    (0, '40817810099910004314','USD',32000, SELECT id from natural_persons OFFSET 1 ROWS FETCH NEXT 1 ROW ONLY),
    (0, '40817810099910004315','RUB',600000.88, SELECT id from natural_persons OFFSET 2 ROWS FETCH NEXT 1 ROW ONLY),
    (0, '40817810099910004316','RUB',1000.80, SELECT id from natural_persons OFFSET 3 ROWS FETCH NEXT 1 ROW ONLY),
    (0, '40817810099910004317','USD',112003.40, SELECT id from natural_persons OFFSET 4 ROWS FETCH NEXT 1 ROW ONLY),
    (0, '40817810099910004318','EUR',348933.94, SELECT id from natural_persons OFFSET 4 ROWS FETCH NEXT 1 ROW ONLY);

CREATE TABLE payment_systems(
                                id INT auto_increment PRIMARY KEY,
                                version BIGINT,
                                name VARCHAR(100),
                                payment_system_code VARCHAR(10) NOT NULL,
                                bank_code VARCHAR(10) NOT NULL
);
INSERT INTO payment_systems(version, name, payment_system_code, bank_code)
VALUES
    (0, 'VISA','4','276'),
    (0, 'Mastercard','5','738'),
    (0, 'МИР','22','022');

CREATE TABLE bank_card_types(
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                version BIGINT,
                                name VARCHAR(100),
                                bank_card_type_code VARCHAR(10) UNIQUE NOT NULL
);
INSERT INTO bank_card_types(version, name, bank_card_type_code)
values
    (0, 'debit card', '72'),
    (0, 'credit card', '90');

CREATE TABLE bank_cards(
                           id INT auto_increment PRIMARY KEY,
                           version BIGINT,
                           bank_account_id int,
                           number VARCHAR(16) UNIQUE,
                           valid_through DATE,
                           CVV VARCHAR(3),
                           payment_system_id int,
                           bank_card_type_id int,
                           issued BOOL,
                           foreign key (bank_account_id) references bank_accounts(id),
                           foreign key (bank_card_type_id) references bank_card_types(id)
);
CREATE INDEX bank_cards_bank_account_idx on bank_cards(bank_account_id);
CREATE INDEX bank_cards_number_idx on bank_cards(number);
CREATE INDEX bank_cards_payment_system_idx on bank_cards(payment_system_id);
CREATE INDEX bank_cards_bank_card_type_idx on bank_cards(bank_card_type_id);
INSERT INTO bank_cards(version, number, bank_account_id, valid_through, CVV, issued, payment_system_id, bank_card_type_id)
VALUES
    (0
    , '4276720089234785'
    , SELECT id from bank_accounts OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY
    , '2023-02-01'
    , '777'
    , true
    , SELECT id FROM payment_systems where name = 'VISA'
    , SELECT id FROM bank_card_types where name = 'debit card'),

    (0
    , '4276720089234792'
    , SELECT id from bank_accounts OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY
    , '2023-02-02'
    , '777'
    , true
    , SELECT id FROM payment_systems where name = 'VISA'
    , SELECT id FROM bank_card_types where name = 'debit card'),

    (0
    , '4738900089234786'
    , SELECT id from bank_accounts OFFSET 1 ROWS FETCH NEXT 1 ROW ONLY
    , '2024-04-01'
    , '778'
    , true
    , SELECT id FROM payment_systems where name = 'Mastercard'
    , SELECT id FROM bank_card_types where name = 'credit card'),

    (0
    , '4276900089234787'
    , SELECT id from bank_accounts OFFSET 2 ROWS FETCH NEXT 1 ROW ONLY
    , '2024-07-01'
    , '779'
    , true
    , SELECT id FROM payment_systems where name = 'VISA'
    , SELECT id FROM bank_card_types where name = 'credit card'),

    (0
    , '4276720089234788'
    , SELECT id from bank_accounts OFFSET 3 ROWS FETCH NEXT 1 ROW ONLY
    , '2028-01-01'
    , '780'
    , true
    , SELECT id FROM payment_systems where name = 'VISA'
    , SELECT id FROM bank_card_types where name = 'debit card'),

    (0
    , '5738720089234789'
    , SELECT id from bank_accounts OFFSET 4 ROWS FETCH NEXT 1 ROW ONLY
    , '2022-10-01'
    , '781'
    , true
    , SELECT id FROM payment_systems where name = 'Mastercard'
    , SELECT id FROM bank_card_types where name = 'debit card'),

    (0
    , '4276720089234790'
    , SELECT id from bank_accounts OFFSET 5 ROWS FETCH NEXT 1 ROW ONLY
    , '2019-10-01'
    , '782'
    , true
    , SELECT id FROM payment_systems where name = 'VISA'
    , SELECT id FROM bank_card_types where name = 'debit card'),

    (0
    , '2202272089234791'
    , SELECT id from bank_accounts OFFSET 6 ROWS FETCH NEXT 1 ROW ONLY
    , '2023-12-01'
    , '083'
    , true
    , SELECT id FROM payment_systems where name = 'МИР',
        SELECT id FROM bank_card_types where name = 'debit card');



select * from natural_persons;
select * from bank_accounts;
select * from payment_systems;
select * from bank_card_types;
select * from bank_cards;

select * from users;
select * from roles;
select * from users_roles;