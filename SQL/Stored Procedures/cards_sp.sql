create or replace procedure sp_insert_card(card_id varchar2, user_id varchar2, card_number number, expiry_date date, security_code number)
as
begin
  insert into cards
  values (card_id, user_id, card_number, expiry_date, security_code);
end;

create or replace procedure sp_update_card(cardid varchar2, userid varchar2, cardnumber number, expirydate date, securitycode number)
as
begin
  update cards
  set user_id = userid, card_number = cardnumber, expiry_date = expirydate, security_code = securitycode
  where card_id = cardid;
end;