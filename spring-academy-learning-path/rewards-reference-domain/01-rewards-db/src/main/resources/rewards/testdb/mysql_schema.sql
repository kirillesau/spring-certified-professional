drop table if exists T_ACCOUNT_BENEFICIARY;
drop table if exists T_ACCOUNT_CREDIT_CARD;
drop table if exists T_ACCOUNT;
drop table if exists T_RESTAURANT;
drop table if exists T_REWARD;
drop table if exists DUAL_REWARD_CONFIRMATION_NUMBER;

create table T_ACCOUNT
(
    ID     INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID),
    NUMBER varchar(9),
    NAME   varchar(50)
);
create table T_ACCOUNT_CREDIT_CARD
(
    ID         INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID),
    ACCOUNT_ID integer,
    NUMBER     varchar(16),
    unique (ACCOUNT_ID, NUMBER)
);
create table T_ACCOUNT_BENEFICIARY
(
    ID                    INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID),
    ACCOUNT_ID            integer,
    NAME                  varchar(50),
    ALLOCATION_PERCENTAGE decimal(3, 2),
    SAVINGS               decimal(8, 2)
);
create table T_RESTAURANT
(
    ID                          INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID),
    MERCHANT_NUMBER             varchar(10),
    NAME                        varchar(80),
    BENEFIT_PERCENTAGE          decimal(3, 2),
    BENEFIT_AVAILABILITY_POLICY varchar(1)
);
create table T_REWARD
(
    ID                     INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID),
    CONFIRMATION_NUMBER    varchar(25),
    REWARD_AMOUNT          decimal(8, 2),
    REWARD_DATE            date,
    ACCOUNT_NUMBER         varchar(9),
    DINING_AMOUNT          decimal(8, 2),
    DINING_MERCHANT_NUMBER varchar(10),
    DINING_DATE            date
);

create table DUAL_REWARD_CONFIRMATION_NUMBER
(
    ZERO int
);
insert into DUAL_REWARD_CONFIRMATION_NUMBER
values (0);
       
