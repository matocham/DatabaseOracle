--Widoki
--MyOffers
CREATE OR REPLACE VIEW myOffers AS
SELECT
    rownum AS ID,
    (SELECT u.login FROM Users u WHERE u.id = o.buyer_id) as buyer_login,
    (SELECT u.login FROM Users u WHERE p.owner_id = u.id) as owner_login,
    (SELECT c.name FROM category c WHERE p.exchange_For = c.id ) as exchange_for,
    o.offered_date, o.exchange_date, o.rate,
    c.NAME,
    p.title, p.add_date, p.description, p.exchanged
FROM offer o, product p, category c
WHERE o.product_id = p.id AND p.category_id = c.ID;

SELECT * from myOffers;
Commit;