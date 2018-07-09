create or replace PROCEDURE SP_INS_LOCATIONS(Location_id VARCHAR2, user_id VARCHAR2, tax_rate NUMBER, street VARCHAR2, city VARCHAR2, country VARCHAR2, stat VARCHAR2, zip VARCHAR2)
as
begin 
insert into locations
VALUES (Location_id, user_id, tax_rate, street, city, country, stat, zip);
END;

END;






create or replace PROCEDURE SP_UPDATE_LOCATION(Location_id VARCHAR2, user_id VARCHAR2, street VARCHAR2, city VARCHAR2, country VARCHAR2, stat VARCHAR2, zip VARCHAR2)
is

BEGIN
update LOCATIONS
    set Location_id=Location_id,
    user_id=user_id,
    street=street,
    city=city,
    state=stat,
    country=country,
    zip=zip
    where location_id=location_id;
END;
