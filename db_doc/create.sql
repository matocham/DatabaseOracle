﻿--Stworzenie tabel
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

--Sekewencje inkrementujące id tabel
CREATE SEQUENCE users_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE category_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE product_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE conversation_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE message_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE offer_seq MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCACHE;
commit;

--stworzenie triggerów do autoincrement i tigger has-ującego hasło użytkownika
CREATE OR REPLACE TRIGGER users_insert_trigger
  BEFORE INSERT ON users FOR EACH ROW
  BEGIN
    SELECT users_seq.nextval INTO :new.id from dual;
    :new.user_password := utilities.get_hash_val(:new.user_password);
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
/
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


-- Paczka
CREATE OR REPLACE PACKAGE utilities
AS
  PROCEDURE finalize_exchange(offer_id offer.id%TYPE);
  PROCEDURE remove_conversation(conv_id conversation.id%TYPE, user_id users.id%TYPE);
  FUNCTION get_hash_val(p_in VARCHAR2)
    RETURN VARCHAR2;
END;
/

--Procedury i funkcje do
--Zakończenie wymiany
--PROCEDURE finalize_exchange(offer_id offer.id%TYPE)
--Usunięcie konwersacji/rozmowy
--PROCEDURE remove_conversation(conv_id conversation.id%TYPE, user_id users.id%TYPE)
--Funkcja do hash-owania rekordów np. haseł użytkowników
--FUNCTION get_hash_val(p_in VARCHAR2)
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

      DELETE from offer
        where (product_id in
                (SELECT product_id FROM offered_products_list WHERE offer_id = v_offer.id)
          or product_id = v_offer.product_id)
          and id != v_offer.id;
      DELETE from offered_products_list
        where (product_id = v_offer.product_id
              or product_id in
              (SELECT product_id FROM offered_products_list WHERE offer_id = v_offer.id))
              and offer_id != v_offer.id;

      DELETE from offer where not exists (select 1 from offered_products_list where offer_id = offer.id);
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

-- Tiggery do automatycznego wstawiania daty
-- Tiggery należy dodać po załadowaniu dannych testowych
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
