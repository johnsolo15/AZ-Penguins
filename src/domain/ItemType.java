/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author syntel
 */
public class ItemType {
    String item_type_id;
    String item_type;

    public ItemType(String item_type_id, String item_type) {
        this.item_type_id = item_type_id;
        this.item_type = item_type;
    }

    public String getItem_type_id() {
        return item_type_id;
    }

    public void setItem_type_id(String item_type_id) {
        this.item_type_id = item_type_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
    
    @Override
    public String toString() {
        return "Item Type [item_type_id=" + item_type_id + ", item_type=" + item_type + "]";
    }
}
