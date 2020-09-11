/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import org.json.simple.JSONObject;

/**
 *
 * @author dylmu
 */
public class Item {

    private double fee;
    private String name;
    private String category;

    /**
     * Determines if JSONObject contains required fields for an item
     * 
     * @param obj The Item JSONObject being checked
     * @return true if JSONObject is a valid item
     * @throws CorruptJSONObjectException 
     */
    private boolean isValidJSONItem(JSONObject obj)
            throws CorruptJSONObjectException {
        if (obj.get("name") == null) {
            throw new CorruptJSONObjectException("Item does not have a name");
        }
        if (obj.get("fee") == null) {
            throw new CorruptJSONObjectException("Item does not have a fee");
        }
        return true;
    }

    public Item(double fee, String name) {
        this(fee, name, "other");
    }

    public Item(double fee, String name, String category) {
        this.category = category;
        this.fee = fee;
        this.name = name;
    }

    public Item(JSONObject obj) throws CorruptJSONObjectException {
        if (isValidJSONItem(obj)) {
            this.name = obj.get("name").toString();
            this.fee = Double.parseDouble(obj.get("fee").toString());
            if (obj.get("category") == null) {
                this.category = "other";
            } else {
                this.category = obj.get("category").toString();
            }
        }

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name + fee + category;
    }

}
