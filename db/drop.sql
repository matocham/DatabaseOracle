DROP SEQUENCE users_seq;
DROP SEQUENCE category_seq;
DROP SEQUENCE product_seq;
DROP SEQUENCE conversation_seq;
DROP SEQUENCE message_seq;
DROP SEQUENCE offer_seq;

drop trigger MESSAGE_INSERT_TRIGGER;
drop trigger PRODUCT_INSERT_TRIGGER;
drop trigger CONV_H_UPDATE_TRIGGER;
drop trigger CON_H_INSERT_TRIGGER;
drop trigger CONVERSATION_INSERT_TRIGGER;
drop trigger CATEGORY_INSERT_TRIGGER;
drop trigger USERS_INSERT_TRIGGER;
drop trigger OFFER_INSERT_TRIGGER;

DROP TABLE offered_products_list;
DROP TABLE offer;
DROP TABLE message;
DROP TABLE conversation;
DROP TABLE product;
DROP TABLE category;
DROP TABLE users;

DROP PACKAGE utilities;

drop view conversation_heading;
drop view myOffers;
drop view offeredMe;