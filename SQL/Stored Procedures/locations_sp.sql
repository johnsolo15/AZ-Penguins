<<<<<<< HEAD
CREATE OR REPLACE PROCEDURE sp_insert_location(u_id varchar2, tax number, st varchar2, ct varchar2, sta varchar2, co varchar2, zip varchar2)
AS
BEGIN
  INSERT into locations(user_id, tax_rate, street, city, state, country, zip) Values(u_id, tax, st, ct, sta, co, zip);
END;

--get location id given user id
create or replace FUNCTION fn_getLocationId(u_id varchar2) RETURN number
AS
  --declaation
  locId number ;
BEGIN
  SELECT location_id INTO locId
  FROM LOCATIONS
  WHERE USER_ID = u_id;
  RETURN locId;
END;
=======
create or replace procedure sp_insert_location(locId varchar2, userId varchar2, tax number, street varchar2, city varchar2, state varchar2, country varchar2, zip varchar2)
as 
begin
  insert into locations
  values(locId, userId, tax, street, city, state, country, zip);
end;

create or replace procedure sp_update_location(locId varchar2, userId varchar2, tax number, str varchar2, cty varchar2, sta varchar2, cntry varchar2, code varchar2)
as 
begin
  update locations
  set user_id = userId, tax_rate = tax, street = str, city = cty, state = sta, country = cntry, zip = code
  where location_id = locId;
end;
>>>>>>> 68c11d6f23183667a344dfedbf15d6402ce04347
