package imhoff.wgubikerecreated.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Class: Inventory puts products and parts in observable array lists.
 */
public class Inventory {
    /**
     * allParts : list of parts inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Method: getAllParts prints all parts in parts observable list.
     * @return returns all parts in allParts to observable list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * allProducts : list of product inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method: getAllProducts prints all products in products observable list.
     * @return returns all products in allProducts to observable list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * Method: addPart a part is added to allParts observable list.
     *
     * @param newPart part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Method: addProduct a product is added to allProducts observable list.
     *
     * @param newProduct part to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Method: lookupPart looks for partID using for loop.
     *
     * @param partID partID to add.
     * @return partID if found otherwise null.
     */
    public static Part lookupPart(int partID) {
        for(Part part: Inventory.getAllParts()) {
            if (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No item found");
        alert.show();
        return null;
    }

    /**
     * Method: lookupProduct looks for product name using for loop.
     *
     * @param productID productID to add.
     * @return productID if found otherwise null.
     */
    public static Product lookupProduct(int productID) {
        for(Product product: Inventory.getAllProducts()){
            if (product.getId() == productID)
                return product;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No item found");
        alert.show();
        return null;
    }

    /**
     * Method: lookupPart searches observablelist of parts and returns the parts.
     *
     * @param partName name of part
     * @return parts found in observableList
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * Method: lookupProduct searches observablelist of products and returns the products.
     *
     * @param productName name of product
     * @return products found in observableList
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * Method: updatePart updates part to observable list.
     *
     * @param index location on list.
     * @param selectedPart name of selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Method: updateProduct updates the product to observable list.
     *
     * @param index location on list.
     * @param selectedProduct name of selected product.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Method: deletePart deletes part from observable list.
     *
     * @param selectedPart selected part name to be deleted
     */
    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method: deleteProduct deletes product from observable list.
     *
     * @param selectedProduct selected product name to be deleted.
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
}