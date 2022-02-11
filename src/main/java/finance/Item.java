package finance;

import org.json.simple.JSONObject;

/**
 * Creates an Item object which describes the physical object transferred in a
 * transaction
 *
 * @author Dylan Munro
 */
public class Item {

    private final double fee;
    private final String name;
    private final String category;

    /**
     * Determines if a JSONObject contains required fields for an item
     *
     * @param obj The JSONObject being checked for properties of an item
     * 
     * @return true if JSONObject is a valid item
     * 
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

    /**
     * Creates an item given a fee and name but no category. 
     * The category is set to "Other" by default
     * 
     * @param fee The change in Account value that a single Item causes
     * @param name The name of the Item
     */
    public Item(double fee, String name) {
        this(fee, name, "Other");
    }

    /**
     * Creates an item given a fee, name, and category
     *
     * @param fee The change in Account value that a single Item causes
     * @param name The name of the Item
     * @param category Broad classification which links multiple items together
     */
    public Item(double fee, String name, String category) {
        this.category = category;
        this.fee = fee;
        this.name = name;
    }

    /**
     * Creates an item from its JSON representation
     *
     * @param obj JSONObject representation of item
     * @throws CorruptJSONObjectException
     */
    public Item(JSONObject obj) throws CorruptJSONObjectException {
        if (isValidJSONItem(obj)) {
            this.name = obj.get("name").toString();
            this.fee = Double.parseDouble(obj.get("fee").toString());
            if (obj.get("category") == null) {
                this.category = "Other";
            } else {
                this.category = obj.get("category").toString();
            }
        } else {
            throw new CorruptJSONObjectException("The JSON file contains invalid item declarations");
        }
    }

    /**
     * The category describes a broad classification which links multiple items
     *
     * @return The category of the item
     */
    public String getCategory() {
        return category;
    }

    /**
     * The fee describes the change that a single item brings to the value of an
     * account
     *
     * @return The fee of the item
     */
    public double getFee() {
        return fee;
    }

    /**
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @return The item's name, fee, and category formatted in a string
     */
    @Override
    public String toString() {
        return "Item name: " + name + "\nCost: " + fee + "\nCategory: " + category;
    }

}
