/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import domain.ItemType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author syntel
 */
public class ItemTypeService {
    Connection con;
    
    public ItemTypeService(Connection con) {
        this.con = con;
    }
    
    public boolean add(ItemType itemType) {
        try {
            CallableStatement statement = con.prepareCall("{call AddItemType(?, ?)}");
            statement.setString(1, itemType.getItem_type_id());
            statement.setString(2, itemType.getItem_type());
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean update(ItemType itemType) {
        try {
            CallableStatement statement = con.prepareCall("{call UpdateItemType(?, ?)}");
            statement.setString(1, itemType.getItem_type_id());
            statement.setString(2, itemType.getItem_type());
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean deleteById(String id) {
        try {
            CallableStatement statement = con.prepareCall("{call DeleteItemType(?)}");
            statement.setString(1, id);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public ArrayList<ItemType> getAll() {
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Item_Types");
            statement.execute();
            ResultSet results = statement.getResultSet();
            while(results.next()) {
                ItemType itemType = new ItemType(results.getString(1), results.getString(2));
                itemTypes.add(itemType);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return itemTypes;
    }
    
    public ItemType getByID(String id) {
        ItemType itemType = null;

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Item_Types WHERE Item_Type_id = ?");
            statement.setString(1, id);
            statement.execute();
            ResultSet results = statement.getResultSet();
            itemType = new ItemType(results.getString(1), results.getString(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return itemType;
    }
}
