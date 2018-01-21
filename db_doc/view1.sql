create or replace view conversation_heading as
  select c.id, p.title, p.image_path, p.id as product_id, sender_id as sender, m.receiver_id as receiver, m.msg_body, m.send_date,
    c.sender_deleted, c.receiver_deleted, m.is_displayed
  from product p, conversation c, message m
  where p.id = c.product_id and m.conversation = c.id and m.send_date = (
    select max(send_date) from message m2 where m2.conversation = c.id
  );

-- update
create or replace TRIGGER conv_h_update_trigger INSTEAD OF UPDATE on conversation_heading
  BEGIN
    insert into message(sender_id, receiver_id, msg_body, conversation) VALUES (:new.sender, :new.receiver, :new.msg_body, :old.id);
  END;
/

select * from conversation_heading;
-- insert
create or replace TRIGGER con_h_insert_trigger INSTEAD OF INSERT on conversation_heading
  DECLARE
    next_conv_id conversation.id%TYPE;
  BEGIN
    insert into conversation(init_sender, init_receiver, product_id) VALUES (:new.sender, :new.receiver, :new.product_id) RETURNING id into next_conv_id;
    insert into message(sender_id, receiver_id, msg_body, conversation) VALUES (:new.sender, :new.receiver, :new.msg_body, next_conv_id);
  END;

/
commit;