create table if not exists BANK_ACCOUNT (
  ACCOUNT_NUMBER varchar,
  BALANCE double,
  DESCRIPTION varchar
);
create table if not exists TRANSACTION (
  ACCOUNT_NUMBER varchar,
  AMOUNT double,
  TIME_STAMP real,
  LOG varchar
);