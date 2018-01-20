-- Widok przed optymalizacją
CREATE OR REPLACE VIEW bad_myOffers AS
  SELECT
    o.id AS ID,
    (SELECT u.login FROM Users u WHERE u.id = o.buyer_id) as buyer_login,
    (SELECT u.login FROM Users u WHERE p.owner_id = u.id) as owner_login,
    (SELECT c.name FROM category c WHERE p.exchange_For = c.id ) as exchange_for,
    o.offered_date, o.exchange_date, o.rate,
    c.NAME,
    p.title, p.add_date, p.description, p.exchanged, p.IMAGE_PATH
  FROM offer o, product p, category c
  WHERE o.product_id = p.id AND p.category_id = c.ID;

--Widok po optymalizacji
CREATE OR REPLACE VIEW myOffers AS
  SELECT
    o.id AS ID,
    u1.login as buyer_login,
    u.LOGIN as owner_login,
    c1.name as exchange_for,
    o.offered_date, o.exchange_date, o.rate,
    c.NAME,
    p.title, p.add_date, p.description, p.exchanged, p.IMAGE_PATH
  FROM offer o, product p, category c, category c1, users u, users u1
  WHERE o.product_id = p.id AND p.category_id = c.ID AND p.owner_id = u.id AND c1.id = p.EXCHANGE_FOR AND o.buyer_id = u1.id;