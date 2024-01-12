package imhoff.wgubikerecreated.controller;


import imhoff.wgubikerecreated.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *  Class: AddProductController modifies product inventory.
 */

public class AddProductController implements Initializable {

    // The ObservableList holds the associatedPartsList.
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField addProductSearchBox;
    @FXML private TextField addProductID;
    @FXML private TextField addProductName;
    @FXML private TextField addProductInv;
    @FXML private TextField addProductPrice;
    @FXML private TextField addProductMax;
    @FXML private TextField addProductMin;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<?, ?> addProductPartIDCol;
    @FXML private TableColumn<?, ?> addPartNameCol;
    @FXML private TableColumn<?, ?> addProductInventoryCol;
    @FXML private TableColumn<?, ?> addProductPriceCol;
    @FXML private TableColumn<?, ?> associatedProductIDCol;
    @FXML private TableColumn<?, ?> associatedPartNameCol;
    @FXML private TableColumn<?, ?> associatedInventoryCol;
    @FXML private TableColumn<?, ?> associatedPriceCol;
    @FXML private Button addProductCancelButton;
    @FXML private Button addProductSaveButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button addProductAddButton;

    private static int id = 1200;

    /**
     * Method: initialize this starts and generates data in table for products and associated parts.
     *
     * @param resourceBundle accesses local resources
     * @param url defines resource location
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id = getProductIDCount();
        addProductID.setText(String.valueOf(id));

        addProductTable.setItems(Inventory.getAllParts());
        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add parts to associated table (bottom)
        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Method: addProductCancel navigates user to MainScreen.fxml.
     *
     * @param event on clicking MainScreen is displayed
     */
    @FXML
    public void addProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Method: productSavePushed saves a new product when button is clicked.
     *
     * @param event when clicked, product is saved.
     */
    @FXML
    void productSavePushed(ActionEvent event) throws IOException {
        try {
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            // max must be larger than min.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than min");
                alert.showAndWait();
                return;
            }

            //Inventory value must be within min and max.
            if (stock < min || max < stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }



            Product product = new Product(id, name, price, stock, min, max);

            for (Part part: associatedPartsList) {
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }

            Inventory.getAllProducts().add(product);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Error: A numerical value is required for: Inventory, Price, Max, and Min.");
            alert.showAndWait();
        }
    }

    /**
     * Method: addProductAdd generates part to ObservableList.
     *
     * @param event when button 'Add' is selected new part is added.
     */

    @FXML
    void addProductAdd(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        }
        else if (!associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Method: removeAssocPartButton removes selected part from ObservableList.
     *
     * @param event when button 'Remove Associated Part' is selected new part is removed.
     */
    @FXML
    void removeAssocPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        }
        else if (associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }


    /**
     * Method: addProductPartSearch allows typed text to search for parts.
     *
     * @param event retrieves search text
     */
    @FXML
    void addProductPartSearch(ActionEvent event) {
        String searchText = addProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }

    /**
     * Method: getProductIDCount increments value of id by one every time new product is added.
     */
    public static int getProductIDCount() {
        id++;
        return id;
    }

}