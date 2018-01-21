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
    o.PRODUCT_ID as PRODUCT_ID,
    u1.login as buyer_login,
    u.LOGIN as owner_login,
    c1.name as exchange_for,
    o.offered_date, o.exchange_date, o.rate,
    c.NAME,
    p.title, p.add_date, p.description, p.exchanged, p.IMAGE_PATH
  FROM offer o, product p, category c, category c1, users u, users u1
  WHERE o.product_id = p.id AND p.category_id = c.ID AND p.owner_id = u.id AND c1.id = p.EXCHANGE_FOR AND o.buyer_id = u1.id;

-- Widok przed optymalizacją
CREATE OR REPLACE VIEW offeredMe AS
  SELECT
    --Za tą oferte
    rownum AS ID,
    mo.owner_login AS OWNER_LOGIN, mo.PRODUCT_ID,
    mo.ID as OFFER_ID , mo.TITLE AS PRODUCT_TITLE, mo.NAME AS PRODUCT_NAME, mo.IMAGE_PATH AS PRODUCT_IMAGE,
    --zaoferowali.
    (SELECT u.login FROM Users u WHERE u.id = p.OWNER_ID) AS FOR_LOGIN,
    p.ID AS FOR_PRODUCT_ID, p.OWNER_ID as FOR_OWNER_ID
    , p.TITLE AS FOR_TITLE, p.DESCRIPTION as FOR_DESCRIPTION,
    (SELECT c.name FROM CATEGORY c WHERE c.id = p.CATEGORY_ID) AS FOR_NAME,
    (SELECT c.name FROM category c WHERE p.exchange_For = c.id ) AS for_exchange_for,
    p.EXCHANGED as FOR_EXCHANGED, p.IMAGE_PATH as FOR_IMAGE_PATH
  FROM myOffers mo, offered_products_list opl ,product p
  WHERE mo.id = opl.offer_id
        AND opl.product_id = p.ID;

-- Widok po optymalizacją
CREATE OR REPLACE VIEW offeredMe AS
  SELECT
    --Za tą oferte
    rownum AS ID,
    mo.owner_login AS OWNER_LOGIN, mo.PRODUCT_ID,
    mo.ID as OFFER_ID , mo.TITLE AS PRODUCT_TITLE, mo.NAME AS PRODUCT_NAME, mo.IMAGE_PATH AS PRODUCT_IMAGE,
    --zaoferowali.
    u.login AS FOR_LOGIN, --(SELECT u.login FROM Users u WHERE u.id = p.OWNER_ID)
    p.ID AS FOR_PRODUCT_ID, p.OWNER_ID as FOR_OWNER_ID
    , p.TITLE AS FOR_TITLE, p.DESCRIPTION as FOR_DESCRIPTION,
    c1.name AS FOR_NAME, --(SELECT c.name FROM CATEGORY c WHERE c.id = p.CATEGORY_ID)
    c2.name AS for_exchange_for, --(SELECT c.name FROM category c WHERE p.exchange_For = c.id )
    p.EXCHANGED as FOR_EXCHANGED, p.IMAGE_PATH as FOR_IMAGE_PATH
  FROM myOffers mo, offered_products_list opl ,product p, category c1, category c2, Users u
  WHERE mo.id = opl.offer_id
        AND opl.product_id = p.ID
        AND c1.id = p.Category_ID
        AND c2.id = p.exchange_For
        AND u.id = p.owner_id;

commit;