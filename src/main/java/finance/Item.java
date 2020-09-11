package finance;

import org.json.simple.JSONObject;

/**
 * Creates an Item object which describes the physical object transferred in a
 * transaction
 *
 * @author Dylan Munro
 */
public class Item {

    private double fee;
    private String name;
    private String category;

    /**
     * Determines if a JSONObject contains required fields for an item
     *
     * @param obj The JSONObject being checked for properties of an item
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

    /**
     * Constructor for Item class Item category is "other" by default
     *
     * @param fee The change in Account value that a single Item causes
     * @param name The name of the Item
     */
    public Item(double fee, String name) {
        this(fee, name, "other");
    }

    /**
     * Constructor for Item class
     * Item category is "other" by default
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
     *
     * @param obj JSONObject representation of item
     * @throws CorruptJSONObjectException
     */
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

    /**
     * The category describes a broad classification which links multiple items
     *
     * @return The category of the item
     */
    public String getCategory() {
        return category;
    }

    /**
     * The category describes a broad classification which links multiple items
     * It has a default value of "other", but can be changed if desired.
     *
     * @param category The category of transactions the item belongs to
     */
    public void setCategory(String category) {
        this.category = category;
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
     * The fee describes the change that a single item brings to the value of an
     * account
     *
     * @param fee The new fee of the item
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * The name property is the name of the item
     *
     * @return The name of the account
     */
    public String getName() {
        return name;
    }

    /**
     * The name property is the name of the item
     *
     * @param name The new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The item's name, fee, then category formatted in a string
     */
    @Override
    public String toString() {
        return name + fee + category;
    }

}
