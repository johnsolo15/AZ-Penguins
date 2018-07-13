CREATE OR REPLACE PROCEDURE AddItemType(id VARCHAR2, type VARCHAR2)
AS
BEGIN
  INSERT INTO Item_Types (item_type_id, item_type)
    VALUES (id, type);
END;

CREATE OR REPLACE PROCEDURE UpdateItemType(id VARCHAR2, type VARCHAR2)
AS
BEGIN
  UPDATE Item_Types
  SET item_type = type
  WHERE item_type_id = id;
END;

CREATE OR REPLACE PROCEDURE DeleteItemType(id VARCHAR2)
AS
BEGIN
  DELETE FROM Item_Types
  WHERE item_type_id = id;
END;