DROP TABLE offered_products_list;
DROP TABLE offer;
DROP TABLE message;
DROP TABLE product;
DROP TABLE category;
DROP TABLE users;

CREATE TABLE users (
  id            NUMBER CONSTRAINT user_pk PRIMARY KEY,
  name          VARCHAR2(50),
  last_name     VARCHAR2(50),
  login         VARCHAR2(50),
  user_password VARCHAR2(50),
  email         VARCHAR2(100),
  city          VARCHAR2(60),
  premium_user  NUMBER(1, 0),
  admin         NUMBER(1, 0)
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
  categoryId   NUMBER CONSTRAINT product_category_fk REFERENCES category (id),
  exchange_for NUMBER CONSTRAINT product_exchange_for_fk REFERENCES category (id),
  add_date     DATE,
  exchanged    NUMBER(1, 0),
  image_path   VARCHAR2(1000)
);

CREATE TABLE message (
  id           NUMBER CONSTRAINT message_pk PRIMARY KEY,
  sender_id    NUMBER CONSTRAINT message_sender_fk REFERENCES users (id),
  receiver_id  NUMBER CONSTRAINT message_receiver_fk REFERENCES users (id),
  product_id   NUMBER CONSTRAINT message_product_fk REFERENCES product (id),
  messge       VARCHAR2(1000),
  is_displayed NUMBER(1, 0),
  send_date    DATE
);

CREATE TABLE offer (
  id            NUMBER CONSTRAINT offer_pk PRIMARY KEY,
  offered_date  DATE,
  exchange_date DATE,
  buyer_id      NUMBER CONSTRAINT offer_buyer_fk REFERENCES users (id),
  product_id    NUMBER CONSTRAINT offer_product_fk REFERENCES product (id),
  rate          NUMBER(1, 0)
);

CREATE TABLE offered_products_list (
  offer_id   NUMBER CONSTRAINT offered_pl_offer_fk REFERENCES offer (id),
  product_id NUMBER CONSTRAINT offered_pl_product_fk REFERENCES product (id),
  CONSTRAINT offered_pl_pk PRIMARY KEY (offer_id, product_id)
);

CREATE OR REPLACE PACKAGE utilities
AS
  PROCEDURE finalize_exchange(offer_id offer.id%TYPE);
  FUNCTION get_hash_val(p_in VARCHAR2)
    RETURN VARCHAR2;
END;

CREATE OR REPLACE PACKAGE BODY utilities AS
  PROCEDURE finalize_exchange(offer_id offer.id%TYPE)
  AS
    v_offer offer%ROWTYPE;
    BEGIN
      SELECT * INTO v_offer FROM offer WHERE id = offer_id;
      UPDATE product SET exchanged = 1 WHERE id = v_offer.product_id;
      UPDATE product SET exchanged = 1 WHERE id IN (SELECT product_id FROM offered_products_list WHERE offer_id = v_offer.id);
      UPDATE offer SET exchange_date = current_date WHERE id = v_offer.id;
      COMMIT;
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

-- przykladowe dane
INSERT INTO users VALUES (0, 'Adam', 'Nowak', 'a_nowak', utilities.get_hash_val('pass1'), 'anowak@examle.com', 'Białystok', 0, 0);
INSERT INTO users VALUES (1, 'Tomasz', 'Kowalski', 't_kowalski', utilities.get_hash_val('pass2'), 'tkowalski@examle.com', 'Warszawa', 1, 0);
INSERT INTO users VALUES (2, 'Sebastian', 'Wiśniewski', 's_wisniewski', utilities.get_hash_val('pass3'), 'swisniewski@examle.com', 'Gdańsk', 1, 0);
INSERT INTO users VALUES (3, 'Bartosz', 'Brzozowski', 'b_brzozowski', utilities.get_hash_val('pass4'), 'bbrzozowski@examle.com', 'Białystok', 0, 1);
INSERT INTO users VALUES (4, 'Konrad', 'Zalewski', 'k_zalewski', utilities.get_hash_val('pass5'), 'kzalewski@examle.com', 'Gdańsk', 0, 0);

INSERT INTO category VALUES (0, 'root', NULL);
INSERT INTO category VALUES (1, 'elektronika', 0);
INSERT INTO category VALUES (2, 'dom i ogród', 0);
INSERT INTO category VALUES (3, 'komputery', 1);
INSERT INTO category VALUES (4, 'stacjonarne', 3);
INSERT INTO category VALUES (5, 'laptopy', 3);
INSERT INTO category VALUES (6, 'telefony', 1);
INSERT INTO category VALUES (7, 'motoryzacja', 0);

INSERT INTO product VALUES (0, 1, 'root', 'description examle 1', 0, 1, TO_DATE('2017/11/01', 'yyyy/mm/dd'), 0, 'random_string1/image.png');
INSERT INTO product VALUES (1, 4, 'elekronika', 'description examle 2', 1, 2, TO_DATE('2017/10/15', 'yyyy/mm/dd'), 0, 'random_string2/image.png');
INSERT INTO product VALUES (2, 1, 'elektronika 2', 'description examle 3', 1, 3, TO_DATE('2017/10/11', 'yyyy/mm/dd'), 0, 'random_string3/image.png');
INSERT INTO product VALUES (3, 2, 'dom i ogród', 'description examle 4', 2, 4, TO_DATE('2017/09/20', 'yyyy/mm/dd'), 0, 'random_string4/image.png');
INSERT INTO product VALUES (4, 4, 'motoryzacja', 'description examle 5', 7, 3, TO_DATE('2017/08/11', 'yyyy/mm/dd'), 0, 'random_string5/image.png');
INSERT INTO product VALUES (5, 3, 'komputery stacjonarne', 'description examle 6', 4, 2, TO_DATE('2017/07/11', 'yyyy/mm/dd'), 0, 'random_string6/image.png');
INSERT INTO product VALUES (6, 3, 'laptopy', 'description examle 7', 5, 7, TO_DATE('2017/06/01', 'yyyy/mm/dd'), 1, 'random_string7/image.png');
INSERT INTO product VALUES (7, 0, 'telefony', 'description examle 8', 6, 1, TO_DATE('2017/10/24', 'yyyy/mm/dd'), 0, 'random_string8/image.png');
INSERT INTO product VALUES (8, 0, 'motoryzacja 2', 'description examle 9', 7, 1, TO_DATE('2017/10/05', 'yyyy/mm/dd'), 0, 'random_string9/image.png');
INSERT INTO product VALUES (9, 3, 'stacjonarne 2', 'description examle 10', 4, 7, TO_DATE('2017/07/22', 'yyyy/mm/dd'), 0, 'random_string10/image.png');
INSERT INTO product VALUES (10, 2, 'motoryzacja 3', 'description examle 11', 7, 5, TO_DATE('2017/05/22', 'yyyy/mm/dd'), 1, 'random_string11/image.png');
INSERT INTO product VALUES (11, 1, 'stacjonarne 3', 'description examle 12', 4, 2, TO_DATE('2017/05/22', 'yyyy/mm/dd'), 0, 'random_string12/image.png');


INSERT INTO message VALUES (0, 0, 1, 0, 'message text 1', 1, TO_DATE('2017/11/01 10:20:20', 'yyyy/mm/dd HH24:MI:SS'));
INSERT INTO message VALUES (1, 1, 0, 0, 'message text 2', 1, TO_DATE('2017/11/01 11:33:21', 'yyyy/mm/dd HH24:MI:SS'));
INSERT INTO message VALUES (2, 0, 1, 0, 'message text 3', 0, TO_DATE('2017/11/03 14:14:14', 'yyyy/mm/dd HH24:MI:SS'));

INSERT INTO message VALUES (3, 3, 4, 1, 'message text 4', 1, TO_DATE('2017/10/17 18:59:43', 'yyyy/mm/dd HH24:MI:SS'));
INSERT INTO message VALUES (4, 4, 3, 1, 'message text 5', 0, TO_DATE('2017/10/17 23:11:43', 'yyyy/mm/dd HH24:MI:SS'));

INSERT INTO message VALUES (5, 4, 1, 2, 'message text 6', 0, TO_DATE('2017/10/15 10:11:59', 'yyyy/mm/dd HH24:MI:SS'));

INSERT INTO message VALUES (6, 1, 2, 3, 'message text 7', 1, TO_DATE('2017/09/29 23:59:59', 'yyyy/mm/dd HH24:MI:SS'));
INSERT INTO message VALUES (7, 1, 2, 3, 'message text 8', 1, TO_DATE('2017/10/01 09:09:11', 'yyyy/mm/dd HH24:MI:SS'));

INSERT INTO message VALUES (8, 3, 4, 4, 'message text 9', 0, TO_DATE('2017/08/15 16:14:41', 'yyyy/mm/dd HH24:MI:SS'));
INSERT INTO message VALUES (9, 4, 3, 4, 'message text 10', 0, TO_DATE('2017/08/16 17:23:32', 'yyyy/mm/dd HH24:MI:SS'));

INSERT INTO message VALUES (10, 2, 3, 5, 'message text 11', 1, TO_DATE('2017/07/18 05:07:33', 'yyyy/mm/dd HH24:MI:SS'));
INSERT INTO message VALUES (11, 1, 3, 5, 'message text 12', 0, TO_DATE('2017/08/16 09:43:54', 'yyyy/mm/dd HH24:MI:SS'));

INSERT INTO offer VALUES (0, TO_DATE('2017/11/03 15:33:14', 'yyyy/mm/dd HH24:MI:SS'), NULL, 0, 0, -1);
INSERT INTO offer VALUES (1, TO_DATE('2017/10/18 08:19:11', 'yyyy/mm/dd HH24:MI:SS'), NULL, 3, 4, -1);
INSERT INTO offer VALUES (2, TO_DATE('2017/06/11 11:45:32', 'yyyy/mm/dd HH24:MI:SS'), TO_DATE('2017/06/22 13:42:12', 'yyyy/mm/dd HH24:MI:SS'), 2, 6, 7);
INSERT INTO offer VALUES (3, TO_DATE('2017/09/20 20:43:01', 'yyyy/mm/dd HH24:MI:SS'), NULL, 1, 3, -1);

INSERT INTO offered_products_list VALUES (0, 7);
INSERT INTO offered_products_list VALUES (0, 8);
INSERT INTO offered_products_list VALUES (1, 9);
INSERT INTO offered_products_list VALUES (2, 10);
INSERT INTO offered_products_list VALUES (3, 11);

COMMIT;

-- oznacz ofertę jako zakończoną
DECLARE
BEGIN
  utilities.finalize_exchange(1);
END;
-- informacje o wlasnie zaktualizowanej ofercie
SELECT * FROM offer WHERE id = 1;

-- produkty jakie zostały zaoferonawe za produkt z id 4
SELECT p2.title, p2.exchanged
FROM product p, offer o, offered_products_list opl, product p2
WHERE p.id = o.product_id AND o.id = opl.offer_id AND opl.product_id = p2.id AND p.id = 4;

-- pobranie wszystki wiadomości dotyczących produktu z id 0 w kolejności wysłania
SELECT m.messge FROM message m, product p WHERE m.product_id = p.id AND p.id = 0 ORDER BY m.send_date;