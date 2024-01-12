package imhoff.wgubikerecreated.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class: Product creates the Product objects.
 */
public class Product {
    /**
     * Product associatedParts list.
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Product ID
     */
    private int id;
    /**
     * Product Name
     */
    private String name;
    /**
     * Product Price
     */
    private double price;
    /**
     * Product inventory
     */
    private int stock;
    /**
     * Product Min level of inventory
     */
    private int min;
    /**
     * Product Max level of inventory
     */
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * setter for id
     *
     * @param id the product ID
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * setter for name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * setter for price
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * setter for stock
     *
     * @param stock product inventory amount
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * setter for min
     *
     * @param min product inventory minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * setter for max
     *
     * @param max product inventory maximum
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * getter for ID
     *
     * @return product ID
     */
    public int getId() {
        return id;
    }

    /**
     * getter for name
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for price
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * getter for stock
     *
     * @return product inventory amount 'in stock'
     */
    public int getStock() {
        return stock;
    }

    /**
     * getter for min
     *
     * @return product inventory minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * getter for max.
     *
     * @return product inventory maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Method: addAssociatedParts adds selected part to associated observable parts list for product.
     *
     * @param part selected part to add to list.
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * Method: deleteAssociatedPart selected part to delete from observable list.
     *
     * @param selectedAssociatedPart part to delete
     * @return true/false based on part deletion success
     */
    public static boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method: getAllAssociatedParts returns parts for selected product.
     *
     * @return the list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}



