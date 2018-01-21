-- przykladowe dane
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
  VALUES ('Adam', 'Nowak', 'a_nowak', utilities.get_hash_val('pass1'), 'anowak@examle.com', 'Białystok', 0, 0);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
  VALUES ('Tomasz', 'Kowalski', 't_kowalski', utilities.get_hash_val('pass2'), 'tkowalski@examle.com', 'Warszawa', 1, 0);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
  VALUES ('Sebastian', 'Wiśniewski', 's_wisniewski', utilities.get_hash_val('pass3'), 'swisniewski@examle.com', 'Gdańsk', 1, 0);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
  VALUES ('Bartosz', 'Brzozowski', 'b_brzozowski', utilities.get_hash_val('pass4'), 'bbrzozowski@examle.com', 'Białystok', 0, 1);
INSERT INTO users(name, last_name, login, user_password, email, city, premium_user, admin)
  VALUES ('Konrad', 'Zalewski', 'k_zalewski', utilities.get_hash_val('pass5'), 'kzalewski@examle.com', 'Gdańsk', 0, 0);

INSERT INTO category(name, parentCategory) VALUES ('root', NULL);
INSERT INTO category(name, parentCategory) VALUES ('elektronika', 1);
INSERT INTO category(name, parentCategory) VALUES ('dom i ogród', 1);
INSERT INTO category(name, parentCategory) VALUES ('komputery', 2);
INSERT INTO category(name, parentCategory) VALUES ('stacjonarne', 4);
INSERT INTO category(name, parentCategory) VALUES ('laptopy', 4);
INSERT INTO category(name, parentCategory) VALUES ('telefony', 2);
INSERT INTO category(name, parentCategory) VALUES ('motoryzacja', 1);

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