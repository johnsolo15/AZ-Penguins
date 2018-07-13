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