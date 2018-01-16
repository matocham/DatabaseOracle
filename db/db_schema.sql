DROP TABLE offered_products_list;
DROP TABLE offer;
DROP TABLE message;
DROP TABLE conversation;
DROP TABLE product;
DROP TABLE category;
DROP TABLE users;

DROP PACKAGE utilities;

DROP SEQUENCE users_seq;
DROP SEQUENCE category_seq;
DROP SEQUENCE product_seq;
DROP SEQUENCE conversation_seq;
DROP SEQUENCE message_seq;
DROP SEQUENCE offer_seq;

-- DROP TRIGGER users_auto_inc;
-- DROP TRIGGER category_auto_inc;
-- DROP TRIGGER product_auto_inc;
-- DROP TRIGGER conversation_auto_inc;
-- DROP TRIGGER message_auto_inc;
-- DROP TRIGGER offer_auto_inc;

--Stworzenie tabel
CREATE TABLE users (
  id            NUMBER CONSTRAINT user_pk PRIMARY KEY,
  name          VARCHAR2(50),
  last_name     VARCHAR2(50),
  login         VARCHAR2(50),
  user_password VARCHAR2(50),
  email         VARCHAR2(100),
  city          VARCHAR2(60),
  premium_user  NUMBER(1, 0) DEFAULT 0,
  admin         NUMBER(1, 0) DEFAULT 0,
  CONSTRAINT user_premium CHECK (premium_user BETWEEN 0 and 1),
  CONSTRAINT user_admin CHECK (premium_user BETWEEN 0 and 1)
);

CREATE TABLE category (
  id             NUMBER CONSTRAINT category_pk PRIMARY KEY,
  name           VARCHAR2(100),
  parentCategory NUMBER CONSTRAINT category_parent_fk REFERENCES category (id)
);

CREATE TABLE product (
  id           NUMBER CONSTRAINT product_pk PRIMARY KEY,
  owner_id     NUMBER CONSTRAINT product_onwer_fk REFERENCES users (id),
  title        VARCHAR2(100),
  description  VARCHAR2(1000),
  category_id  NUMBER CONSTRAINT product_category_fk REFERENCES category (id),
  exchange_for NUMBER CONSTRAINT product_exchange_for_fk REFERENCES category (id),
  add_date     DATE CONSTRAINT product_date NOT NULL,
  exchanged    NUMBER(1, 0) DEFAULT 0,
  image_path   VARCHAR2(1000),
  CONSTRAINT product_exchanged CHECK (exchanged BETWEEN 0 and 1)
);

CREATE TABLE conversation (
  id               NUMBER CONSTRAINT conversation_pk PRIMARY KEY,
  init_sender      NUMBER CONSTRAINT conversation_init_sender_fk REFERENCES users (id),
  init_receiver    NUMBER CONSTRAINT conversation_init_receiver_fk REFERENCES users (id),
  product_id       NUMBER CONSTRAINT conversation_product_fk REFERENCES product (id),
  sender_deleted   NUMBER(1, 0) DEFAULT 0,
  receiver_deleted NUMBER(1, 0) DEFAULT 0,
  CONSTRAINT conversation_sender_del CHECK (sender_deleted BETWEEN 0 and 1),
  CONSTRAINT conversation_receiver_del CHECK (receiver_deleted BETWEEN 0 and 1)
);

CREATE TABLE message (
  id           NUMBER CONSTRAINT message_pk PRIMARY KEY,
  sender_id    NUMBER CONSTRAINT message_sender_fk REFERENCES users (id),
  receiver_id  NUMBER CONSTRAINT message_receiver_fk REFERENCES users (id),
  msg_body     VARCHAR2(1000),
  is_displayed NUMBER(1, 0) DEFAULT 0,
  send_date    DATE CONSTRAINT message_date NOT NULL,
  conversation NUMBER CONSTRAINT message_conversation_fk REFERENCES conversation (id),
  CONSTRAINT message_displayed CHECK (is_displayed BETWEEN 0 and 1)
);

CREATE TABLE offer (
  id            NUMBER CONSTRAINT offer_pk PRIMARY KEY,
  offered_date  DATE CONSTRAINT offer_date NOT NULL,
  exchange_date DATE,
  buyer_id      NUMBER CONSTRAINT offer_buyer_fk REFERENCES users (id),
  product_id    NUMBER CONSTRAINT offer_product_fk REFERENCES product (id),
  rate          NUMBER(2, 0) DEFAULT -1,
  CONSTRAINT offer_rate CHECK (rate BETWEEN -1 and 10)
);

CREATE TABLE offered_products_list (
  offer_id   NUMBER CONSTRAINT offered_pl_offer_fk REFERENCES offer (id),
  product_id NUMBER CONSTRAINT offered_pl_product_fk REFERENCES product (id),
  CONSTRAINT offered_pl_pk PRIMARY KEY (offer_id, product_id)
);

--stworzenie sekwencji
CREATE SEQUENCE users_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE category_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE product_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE conversation_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE message_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE offer_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;

--stworzenie triggerów do autoincrement
CREATE OR REPLACE TRIGGER users_insert_trigger
  BEFORE INSERT ON users FOR EACH ROW
  BEGIN
    SELECT users_seq.nextval INTO :new.id from dual;
    :new.user_password = utilities.get_hash_val(:new.user_password);
  END;
/
CREATE OR REPLACE TRIGGER category_insert_trigger
  BEFORE INSERT ON category FOR EACH ROW
  BEGIN
    SELECT category_seq.nextval INTO :new.id from dual;
  END;
/
CREATE OR REPLACE TRIGGER product_insert_trigger
  BEFORE INSERT ON product FOR EACH ROW
  BEGIN
    SELECT product_seq.nextval INTO :new.id from dual;
    --:new.add_date := current_date;
  END;

CREATE OR REPLACE TRIGGER conversation_insert_trigger
  BEFORE INSERT ON conversation FOR EACH ROW
  BEGIN
    SELECT conversation_seq.nextval INTO :new.id from dual;
  END;
/
CREATE OR REPLACE TRIGGER message_insert_trigger
  BEFORE INSERT ON message FOR EACH ROW
  BEGIN
    SELECT message_seq.nextval INTO :new.id from dual;
    --:new.send_date := current_date;
  END;
/
CREATE OR REPLACE TRIGGER offer_insert_trigger
  BEFORE INSERT ON offer FOR EACH ROW
  BEGIN
    SELECT offer_seq.nextval INTO :new.id from dual;
    --:new.offered_date := current_date;
  END;
/

--stworzenie procedur i funkcji w paczce
CREATE OR REPLACE PACKAGE utilities
AS
  PROCEDURE finalize_exchange(offer_id offer.id%TYPE);
  PROCEDURE remove_conversation(conv_id conversation.id%TYPE, user_id users.id%TYPE);
  FUNCTION get_hash_val(p_in VARCHAR2)
    RETURN VARCHAR2;
END;
/
CREATE OR REPLACE PACKAGE BODY utilities AS
  PROCEDURE finalize_exchange(offer_id offer.id%TYPE)
  AS
    v_offer offer%ROWTYPE;
    BEGIN
      SELECT * INTO v_offer FROM offer WHERE id = offer_id;
      UPDATE product SET exchanged = 1 WHERE id = v_offer.product_id;
      UPDATE product SET exchanged = 1
      WHERE id IN (
        SELECT product_id FROM offered_products_list WHERE offer_id = v_offer.id
      );
      UPDATE offer SET exchange_date = current_date WHERE id = v_offer.id;
      COMMIT;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      raise_application_error(-20001, 'Offer does not exists!');
      WHEN TOO_MANY_ROWS THEN
      raise_application_error(-20002, 'Duplicated offer data!');
      WHEN OTHERS THEN
      raise_application_error(-29999, 'Unexpected error!');
    END;
  PROCEDURE remove_conversation(conv_id conversation.id%TYPE, user_id users.id%TYPE)
  AS
    row_count_as_sender   INTEGER;
    row_count_as_receiver INTEGER;
    BEGIN
      SELECT count(1) INTO row_count_as_sender FROM conversation WHERE id = conv_id AND init_sender = user_id;
      SELECT count(1) INTO row_count_as_receiver FROM conversation WHERE id = conv_id AND init_receiver = user_id;

      IF row_count_as_sender = row_count_as_receiver THEN
        raise_application_error(-20003, 'Data is inconsistent. Sender and receiver are the same person');
      ELSIF row_count_as_sender > 0 THEN
        UPDATE conversation SET sender_deleted = 1 WHERE id = conv_id;
      ELSIF row_count_as_receiver > 0 THEN
        UPDATE conversation SET receiver_deleted = 1 WHERE id = conv_id;
      ELSE
        RAISE NO_DATA_FOUND;
      END IF;
    END;
  FUNCTION get_hash_val(p_in VARCHAR2)
    RETURN VARCHAR2
  IS
    l_hash VARCHAR2(2000);
    BEGIN
      l_hash := RAWTOHEX(UTL_RAW.cast_to_raw(DBMS_OBFUSCATION_TOOLKIT.md5(input_string => p_in)));
      RETURN l_hash;
    END;
END;
/
-- przykladowe dane
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
VALUES ('Adam', 'Nowak', 'a_nowak', 'pass1', 'anowak@examle.com', 'Białystok', 0, 0);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
VALUES ('Tomasz', 'Kowalski', 't_kowalski', 'pass2', 'tkowalski@examle.com', 'Warszawa', 1, 0);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
VALUES ('Sebastian', 'Wiśniewski', 's_wisniewski', 'pass3', 'swisniewski@examle.com', 'Gdańsk', 1, 0);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
VALUES ('Bartosz', 'Brzozowski', 'b_brzozowski', 'pass4', 'bbrzozowski@examle.com', 'Białystok', 0, 1);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
VALUES ('Konrad', 'Zalewski', 'k_zalewski', 'pass5', 'kzalewski@examle.com', 'Gdańsk', 0, 0);

INSERT INTO category(name, parentCategory) VALUES ('elektronika', NULL);
INSERT INTO category(name, parentCategory) VALUES ('dom i ogród', NULL);
INSERT INTO category(name, parentCategory) VALUES ('komputery', 1);
INSERT INTO category(name, parentCategory) VALUES ('stacjonarne', 3);
INSERT INTO category(name, parentCategory) VALUES ('laptopy', 3);
INSERT INTO category(name, parentCategory) VALUES ('telefony', 1);
INSERT INTO category(name, parentCategory) VALUES ('motoryzacja', NULL);

INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (2, 'root', 'description examle 1', 1, 1, TO_DATE('2017/11/01', 'yyyy/mm/dd'), 0, '/images/product/1/1.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (5, 'elekronika', 'description examle 2', 1, 2, TO_DATE('2017/10/15', 'yyyy/mm/dd'), 0, '/images/product/2/2.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (2, 'elektronika 2', 'description examle 3', 2, 3, TO_DATE('2017/10/11', 'yyyy/mm/dd'), 0, '/images/product/3/3.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (3, 'dom i ogród', 'description examle 4', 2, 4, TO_DATE('2017/09/20', 'yyyy/mm/dd'), 0, '/images/product/4/5.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (5, 'motoryzacja', 'description examle 5', 7, 3, TO_DATE('2017/08/11', 'yyyy/mm/dd'), 0, '/images/product/5/5.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (4, 'komputery stacjonarne', 'description examle 6', 4, 2, TO_DATE('2017/07/11', 'yyyy/mm/dd'), 0, '/images/product/6/6.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (4, 'laptopy', 'description examle 7', 5, 7, TO_DATE('2017/06/01', 'yyyy/mm/dd'), 1, '/images/product/7/7.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (1, 'telefony', 'description examle 8', 6, 1, TO_DATE('2017/10/24', 'yyyy/mm/dd'), 0, '/images/product/8/8.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (1, 'motoryzacja 2', 'description examle 9', 7, 1, TO_DATE('2017/10/05', 'yyyy/mm/dd'), 0, '/images/product/9/9.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (4, 'stacjonarne 2', 'description examle 10', 4, 7, TO_DATE('2017/07/22', 'yyyy/mm/dd'), 0, '/images/product/10/10.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (3, 'motoryzacja 3', 'description examle 11', 7, 5, TO_DATE('2017/05/22', 'yyyy/mm/dd'), 1, '/images/product/11/11.png');
INSERT INTO product(owner_id, title, description, category_id, exchange_for, add_date, exchanged, image_path)
VALUES (2, 'stacjonarne 3', 'description examle 12', 4, 2, TO_DATE('2017/05/22', 'yyyy/mm/dd'), 0, '/images/product/12/12.png');

INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (1, 2, 1, 0, 0);
INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (4, 5, 2, 0, 0);
INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (5, 2, 3, 0, 0);
INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (2, 3, 4, 0, 0);
INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (4, 5, 5, 0, 0);
INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (3, 4, 6, 0, 0);
INSERT INTO conversation(init_sender, init_receiver, product_id, sender_deleted, receiver_deleted) VALUES (2, 4, 6, 0, 0);

INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (1, 2, 'message text 1', 1, TO_DATE('2017/11/01 10:20:20', 'yyyy/mm/dd HH24:MI:SS'), 1);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (2, 1, 'message text 2', 1, TO_DATE('2017/11/01 11:33:21', 'yyyy/mm/dd HH24:MI:SS'), 1);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (1, 2, 'message text 3', 0, TO_DATE('2017/11/03 14:14:14', 'yyyy/mm/dd HH24:MI:SS'), 1);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (3, 5, 'message text 4', 1, TO_DATE('2017/10/17 18:59:43', 'yyyy/mm/dd HH24:MI:SS'), 2);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (4, 4, 'message text 5', 0, TO_DATE('2017/10/17 23:11:43', 'yyyy/mm/dd HH24:MI:SS'), 2);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (4, 2, 'message text 6', 0, TO_DATE('2017/10/15 10:11:59', 'yyyy/mm/dd HH24:MI:SS'), 3);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (2, 3, 'message text 7', 1, TO_DATE('2017/09/29 23:59:59', 'yyyy/mm/dd HH24:MI:SS'), 4);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (2, 3, 'message text 8', 1, TO_DATE('2017/10/01 09:09:11', 'yyyy/mm/dd HH24:MI:SS'), 4);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (4, 5, 'message text 9', 0, TO_DATE('2017/08/15 16:14:41', 'yyyy/mm/dd HH24:MI:SS'), 5);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (5, 4, 'message text 10', 0, TO_DATE('2017/08/16 17:23:32', 'yyyy/mm/dd HH24:MI:SS'), 5);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (3, 4, 'message text 11', 1, TO_DATE('2017/07/18 05:07:33', 'yyyy/mm/dd HH24:MI:SS'), 6);
INSERT INTO message(sender_id, receiver_id, msg_body, is_displayed, send_date, conversation)
VALUES (2, 4, 'message text 12', 0, TO_DATE('2017/08/16 09:43:54', 'yyyy/mm/dd HH24:MI:SS'), 7);

INSERT INTO offer(offered_date, exchange_date, buyer_id, product_id, rate) VALUES (TO_DATE('2017/11/03 15:33:14', 'yyyy/mm/dd HH24:MI:SS'), NULL, 1, 1, -1);
INSERT INTO offer(offered_date, exchange_date, buyer_id, product_id, rate) VALUES (TO_DATE('2017/10/18 08:19:11', 'yyyy/mm/dd HH24:MI:SS'), NULL, 4, 5, -1);
INSERT INTO offer(offered_date, exchange_date, buyer_id, product_id, rate) VALUES (TO_DATE('2017/06/11 11:45:32', 'yyyy/mm/dd HH24:MI:SS'), TO_DATE('2017/06/22 13:42:12', 'yyyy/mm/dd HH24:MI:SS'), 3, 7, 7);
INSERT INTO offer(offered_date, exchange_date, buyer_id, product_id, rate) VALUES (TO_DATE('2017/09/20 20:43:01', 'yyyy/mm/dd HH24:MI:SS'), NULL, 2, 4, -1);

INSERT INTO offered_products_list VALUES (1, 8);
INSERT INTO offered_products_list VALUES (1, 9);
INSERT INTO offered_products_list VALUES (2, 10);
INSERT INTO offered_products_list VALUES (3, 11);
INSERT INTO offered_products_list VALUES (4, 12);

COMMIT;

-- oznacz ofertę jako zakończoną
DECLARE
BEGIN
  utilities.finalize_exchange(2);
END;
/
-- po zaladowaniu danych testowych dodaj triggery z automatycznym ustawianiem dat
CREATE OR REPLACE TRIGGER product_insert_trigger
  BEFORE INSERT ON product FOR EACH ROW
  BEGIN
    SELECT product_seq.nextval INTO :new.id from dual;
    :new.add_date := current_date;
  END;
/
CREATE OR REPLACE TRIGGER message_insert_trigger
  BEFORE INSERT ON message FOR EACH ROW
  BEGIN
    SELECT message_seq.nextval INTO :new.id from dual;
    :new.send_date := current_date;
  END;
/
CREATE OR REPLACE TRIGGER offer_insert_trigger
  BEFORE INSERT ON offer FOR EACH ROW
  BEGIN
    SELECT offer_seq.nextval INTO :new.id from dual;
    :new.offered_date := current_date;
  END;
/
--propozycja perspektywy - podsumowanie konwersacji, które można wykorzystać przy wyświetlaniu aktualnych rozmów
create or replace view conversation_heading as
  select c.id, p.title, p.image_path, p.id as product_id, u.id as sender, u2.id as receiver, m.msg_body, m.send_date, c.sender_deleted, c.receiver_deleted, m.is_displayed
  from product p, conversation c, users u, users u2, message m
  where p.id = c.product_id and m.conversation = c.id and u.id = m.sender_id and u2.id = m.receiver_id
        and m.send_date = (
    select max(send_date) from message m2 where m2.conversation = c.id
  );

select * from conversation_heading order by id;

-- update
create or replace TRIGGER conv_h_update_trigger INSTEAD OF UPDATE on conversation_heading
  BEGIN
    insert into message(sender_id, receiver_id, msg_body, conversation) VALUES (:new.sender, :new.receiver, :new.msg_body, :old.id);
  END;
/
update conversation_heading set sender = 2, receiver = 1, msg_body = 'new message' where id = 1;

-- insert
create or replace TRIGGER con_h_insert_trigger INSTEAD OF INSERT on conversation_heading
  DECLARE
    next_conv_id conversation.id%TYPE;
  BEGIN
    insert into conversation(init_sender, init_receiver, product_id) VALUES (:new.sender, :new.receiver, :new.product_id) RETURNING id into next_conv_id;
    insert into message(sender_id, receiver_id, msg_body, conversation) VALUES (:new.sender, :new.receiver, :new.msg_body, next_conv_id);
  END;
/
insert into conversation_heading(sender, receiver, msg_body, product_id) values (1,3, 'new message test 1', 2);

-- testowanie usuwania
DECLARE
BEGIN
  utilities.remove_conversation(8,1);
END;
/
--pobranie nieusuniętych rozmów, w których uczestniczy x
select ch.* from conversation_heading ch where (sender = 1 and sender_deleted = 0) or (receiver = 1 and receiver_deleted = 0);
select id from conversation_heading where (sender = 1 or receiver =1) and (sender =2 or receiver =2) and product_id = 1;

-- jak wykonanie zakończyło się z ostrzeżeniem to tutaj jest błąd
select line, position, text
from user_errors
order by sequence;

select 'drop trigger ' || trigger_name || ';' stmt from user_triggers;
drop trigger MESSAGE_INSERT_TRIGGER;
drop trigger CON_H_INSERT_TRIGGER;
drop trigger CONVERSATION_INSERT_TRIGGER;
drop trigger OFFER_INSERT_TRIGGER;
drop trigger PRODUCT_INSERT_TRIGGER;
drop trigger USERS_INSERT_TRIGGER;
drop trigger CATEGORY_INSERT_TRIGGER;
