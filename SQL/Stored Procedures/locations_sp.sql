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