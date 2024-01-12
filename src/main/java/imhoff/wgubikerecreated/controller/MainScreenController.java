package imhoff.wgubikerecreated.controller;

import imhoff.wgubikerecreated.model.Inventory;
import imhoff.wgubikerecreated.model.Part;
import imhoff.wgubikerecreated.model.Product;

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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *  Class: MainScreenController gives functionality to MainScreen allowing user to interact with app
 */

public class MainScreenController implements Initializable {

    @FXML private TextField productSearch;
    @FXML private TextField partSearch;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInventoryCol;
    @FXML private TableColumn<Product, Double> productPriceCol;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableView<Product> mainScreenProductsTable;
    @FXML private TableView<Part> mainScreenPartsTable;
    @FXML private Button modifyPartButton;
    @FXML private Button addPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button exitMain;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;

    Stage stage;

    /**
     * Method: initialize generates tables and values for part and product tables.
     *
     * @param url defines resource location
     * @param resourceBundle accesses local resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainScreenPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainScreenProductsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Method mainScreenAddPartsClick allows part to be added to AddPart.fxml.
     *
     * @param event when 'Add' button in Parts is clicked allows part to be added
     */
    @FXML
    void mainScreenAddPartsClick(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Method: mainScreenModifyPartsClick loads ModifyPart.fxml.
     *
     * @param event when button 'Modify' in Parts is clicked the ModifyPart screen loads
     */
    @FXML
    void mainScreenModifyPartsClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/imhoff/wgubikerecreated/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainScreenPartsTable.getSelectionModel().getSelectedIndex(),mainScreenPartsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part first");
            alert.show();
        }
    }

    /**
     * Method: mainScreenAddProductsClick loads the AddProduct.fxml.
     *
     * @param event when 'Add' button in Products is clicked AddProduct is loaded
     */
    @FXML
    void mainScreenAddProductsClick(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/AddProduct.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Method: mainScreenModifyProductsClick loads ModifyProduct.fxml.
     *
     * @param event when 'Modify' button in Product is clicked ModifyProduct is loaded
     */
    @FXML
    void mainScreenModifyProductsClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/imhoff/wgubikerecreated/wgubike/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(mainScreenProductsTable.getSelectionModel().getSelectedIndex(), mainScreenProductsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product to modify");
            alert.show();
        }
    }

    /**
     * Method: mainScreenExitButton app is exited.
     *
     * @param ExitButton when button "Exit" is clicked app is exited.
     */
    public void mainScreenExitButton(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Method: mainScreenDeletePartButton selected part is deleted.
     *
     * @param event when 'Delete' button is clicked in Parts the item is deleted.
     */
    @FXML
    void mainScreenDeletePartButton(ActionEvent event) {
        Part selectedPart = mainScreenPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert noSelectedPart = new Alert(Alert.AlertType.ERROR);
            noSelectedPart.setTitle("Confirmation");
            noSelectedPart.setHeaderText("Error");
            noSelectedPart.setContentText("No part selected");
            noSelectedPart.showAndWait();
        } else {
            Alert deletePart = new Alert(Alert.AlertType.CONFIRMATION);
            deletePart.setTitle("Confirmation");
            deletePart.setHeaderText("Caution!");
            deletePart.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = deletePart.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Method: mainScreenDeleteProductButton selected product is deleted and checks it for associated parts.
     *
     * @param event when 'Delete' button is clicked in Product part is deleted.
     */

    @FXML
    void mainScreenDeleteProductButton(ActionEvent event) {

        Product selectedProduct = mainScreenProductsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null){
            Alert noSelectedProduct = new Alert(Alert.AlertType.ERROR);
            noSelectedProduct.setTitle("Confirmation");
            noSelectedProduct.setHeaderText("Error");
            noSelectedProduct.setContentText("No product selected");
            noSelectedProduct.showAndWait();
        } else {
            Alert deleteProduct = new Alert(Alert.AlertType.CONFIRMATION);
            deleteProduct.setTitle("Confirmation");
            deleteProduct.setHeaderText("Caution!");
            deleteProduct.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = deleteProduct.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> getAllAssociatedParts = selectedProduct.getAllAssociatedParts();

                if (getAllAssociatedParts.size() >= 1) {
                    Alert relatedPartsError = new Alert(Alert.AlertType.ERROR);
                    relatedPartsError.setTitle("Error");
                    relatedPartsError.setContentText("All related parts in product must be removed before deleting selected product.");
                    relatedPartsError.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Method: mainScreenPartSearch text is entered to text box of Part and part names are searched for.
     *
     * @param event when 'enter' is keyed  text is searched.
     */
    @FXML
    void mainScreenPartSearch(ActionEvent event) {
        String searchText = partSearch.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            mainScreenPartsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }

    /**
     * Method: mainScreenProductSearch text is entered to text box of Product and product names are searched for.
     *
     * @param event when 'enter' is keyed  text is searched.
     */
    @FXML
    void mainScreenProductSearch(ActionEvent event) {
        String searchText = productSearch.getText();
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            while (results.size() == 0 ) {
                int productID = Integer.parseInt(searchText);
                results.add(Inventory.lookupProduct(productID));
            }
            mainScreenProductsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Product not found");
            noParts.showAndWait();
        }
    }
}