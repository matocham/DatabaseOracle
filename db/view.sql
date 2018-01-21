CREATE OR REPLACE VIEW myOffers AS
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


CREATE OR REPLACE VIEW offeredMe AS
  SELECT
    --Za tą oferte
    rownum AS ID,
    mo.owner_login AS OWNER_LOGIN,
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

SELECT * from myOffers;
SELECT * FROM offeredMe om;

Commit;