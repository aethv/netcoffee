drop table if exists ACCOUNTS;
drop table if exists ORDERS;
drop table if exists ORDER_ITEMS;
drop table if exists PRODUCTS;
create table ACCOUNTS (ACCOUNT_ID bigint not null auto_increment, PASSWORD varchar(255), DISPLAY_NAME varchar(255), LAST_LOGINED integer, STATUS integer, primary key (ACCOUNT_ID));
create table ORDERS (ORDER_ID bigint not null auto_increment, AMOUNT double precision not null, STATUS integer not null, ACCOUNT_ID bigint not null, primary key (ORDER_ID));
create table ORDER_ITEMS (ITEM_ID bigint not null auto_increment, PRODUCT_NAME varchar(255) not null, QUANLITY integer not null, SUB_TOTAL double precision not null, UNIT_COST double precision not null, ORDER_ID bigint not null, product_PRODUCT_ID bigint, primary key (ITEM_ID));
create table PRODUCTS (PRODUCT_ID bigint not null auto_increment, PRODUCT_NAME varchar(255) not null, PRICE double precision not null, STATUS integer not null, STOCK integer not null, primary key (PRODUCT_ID));
--alter table ORDERS add index FK8B7256E5D918AF1E (ACCOUNT_ID), add constraint FK8B7256E5D918AF1E foreign key (ACCOUNT_ID) references ACCOUNTS (ACCOUNT_ID)
--alter table ORDER_ITEMS add index FK2BFF474F277AE07E (ORDER_ID), add constraint FK2BFF474F277AE07E foreign key (ORDER_ID) references ORDERS (ORDER_ID)
--alter table ORDER_ITEMS add index FK2BFF474FAD040D2E (product_PRODUCT_ID), add constraint FK2BFF474FAD040D2E foreign key (product_PRODUCT_ID) references PRODUCTS (PRODUCT_ID)
--alter table PRODUCTS add constraint uc_PRODUCTS_2 unique (PRODUCT_NAME)
