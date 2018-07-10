-----------Alterations to add TimeStamps----------------------
--First we make sure no values in the selected fields
UPDATE ORDERS
SET PLACED_TIMESTAMP = null;

UPDATE ORDERS
SET DELIVERY_TIMESTAMP = null;

--then we change the data type of the fields
ALTER TABLE ORDERS
  MODIFY( PLACED_TIMESTAMP timestamp(9), DELIVERY_TIMESTAMP timestamp(9));
  
--we also updated the procedures addOrder and updateOrder
create or replace PROCEDURE AddOrder(OrderID VARCHAR2, UserID VARCHAR2, 
Tip NUMBER, TotalPrice NUMBER, PlacedTimestamp timestamp, DeliveryTimestamp timestamp,
CardID VARCHAR2, Instructions VARCHAR2, DeliveryMethodID VARCHAR2, 
StoreID VARCHAR2, DeliveryStatusID VARCHAR2)
AS
BEGIN
  INSERT INTO ORDERS (ORDER_ID, USER_ID, TIP, TOTAL_PRICE, PLACED_TIMESTAMP, 
  DELIVERY_TIMESTAMP, CARD_ID, INSTRUCTIONS, DELIVERY_METHOD_ID, STORE_ID, 
  DELIVERY_STATUS_ID)
    VALUES (OrderID, UserID, Tip, TotalPrice, PlacedTimestamp, 
    DeliveryTimestamp, CardID, Instructions, DeliveryMethodID, StoreID, 
    DeliveryStatusID);
END;

create or replace PROCEDURE UpdateOrder(OrderID VARCHAR2, UserID VARCHAR2, 
Tip NUMBER, TotalPrice NUMBER, PlacedTimestamp timestamp, DeliveryTimestamp timestamp,
CardID VARCHAR2, Instructions VARCHAR2, DeliveryMethodID VARCHAR2, 
StoreID VARCHAR2, DeliveryStatusID VARCHAR2)
AS
BEGIN
  UPDATE ORDERS
  SET USER_ID=UserID, TIP=Tip, TOTAL_PRICE=TotalPrice, 
  PLACED_TIMESTAMP=PlacedTimestamp, DELIVERY_TIMESTAMP=DeliveryTimestamp, 
  CARD_ID=CardID, INSTRUCTIONS=Instructions, 
  DELIVERY_METHOD_ID=DeliveryMethodID, STORE_ID=StoreID, 
  DELIVERY_STATUS_ID=DeliveryStatusID
  WHERE ORDER_ID=OrderID;
END;
--------------------------------------------------------------------------------
