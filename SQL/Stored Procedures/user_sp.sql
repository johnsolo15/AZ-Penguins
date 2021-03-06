
SHOW ERRORS;

create or replace procedure sp_update_user(user_num varchar, firstN varchar, lastN varchar, pho varchar, eml varchar, psswd varchar, u_s_id varchar)
as 
begin
  update users
  set first=firstN, last=lastN, phone=pho, email=eml, password=psswd, user_status_id=u_s_id
  where user_id=user_num;
end;

create or replace procedure sp_insert_user(user_id varchar, first varchar, last varchar, phone varchar, email varchar, password varchar, user_status_id varchar)
as 
begin
  insert into users(USER_ID, FIRST, LAST, PHONE, EMAIL, PASSWORD, USER_STATUS_ID)
  values(user_id, first, last, phone, email, password, user_status_id);
  
end;


create or replace procedure sp_delete_user_by_id(user_num varchar2)
as 
begin
  Delete from users where user_id = user_num;
end;

CREATE OR REPLACE FUNCTION fn_getLocationId(u_id varchar2) RETURN number
AS
  --declaation
  locId number ;
BEGIN
  SELECT location_id INTO locId
  FROM LOCATIONS
  WHERE USER_ID = u_id;
  RETURN locId;
END;

