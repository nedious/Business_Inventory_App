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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class: ModifyProductController is the controller for ModifyProductController class.
 * implements Initializable makes program fire off the method initialize when the program begins.
 */

public class ModifyProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField modifyProductSearchBox;
    @FXML private TextField modifyProductID;
    @FXML private TextField modifyProductName;
    @FXML private TextField modifyProductInv;
    @FXML private TextField modifyProductPrice;
    @FXML private TextField modifyProductMax;
    @FXML private TextField modifyProductMin;
    @FXML private TableView<Part> modifyProductTable;
    @FXML private TableColumn<?, ?> modifyProductPartIDCol;
    @FXML private TableColumn<?, ?> modifyPartNameCol;
    @FXML private TableColumn<?, ?> modifyProductInventoryCol;
    @FXML private TableColumn<?, ?> modifyProductPriceCol;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableColumn<?, ?> associatedProductIDCol;
    @FXML private TableColumn<?, ?> associatedPartNameCol;
    @FXML private TableColumn<?, ?> associatedInventoryCol;
    @FXML private TableColumn<?, ?> associatedPriceCol;
    @FXML private Button modifyProductCancelButton;
    @FXML private Button modifyProductSaveButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button modifyProductmodifyButton;


    private int currentIndex = 0;

    /**
     * Method: initialize generate table and set values.
     *
     * @param url defines resource location
     * @param resourceBundle accesses local resources
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add parts to associated parts table below product table
        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Method: modifyProductCancel cancels product menu and goes to MainScreen.
     *
     * @param event when user clicks 'Cancel' button.
     */

    @FXML
    public void modifyProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Method: sendProduct get values of product.
     *
     * @param selectedIndex user selects product from table, index number.
     * @param product user selects product from table, product value.
     */

    @FXML
    public void sendProduct(int selectedIndex, Product product){
        currentIndex = selectedIndex;
        modifyProductID.setText(String.valueOf(product.getId()));
        modifyProductName.setText(String.valueOf(product.getName()));
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));

        for (Part part: product.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }
    }

    /**
     * Method: addPartProductModify selected part is added to table of associated parts.
     *
     * @param event when part is selected and added by user clicking 'Add' button
     */
    @FXML
    void addPartProductModify(ActionEvent event) {
        Part selectedPart = modifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        }

        else if (!associatedPartsList.contains(selectedPart) || associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Method: removeAssocPartButton selected part is removed from table.
     *
     * @param event part is selected and user clicks button 'Remove Associated Part'.
     */
    @FXML
    void removeAssocPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Input Error");
            inputError.setContentText("Select part from list");
            inputError.showAndWait();
            return;
        }
        if (associatedPartsList.contains(selectedPart)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Caution!");
            alert.setContentText("Do you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Product.deleteAssociatedPart(selectedPart);
                associatedPartsList.remove(selectedPart);
                associatedProductTable.setItems(associatedPartsList);
            }
        }
    }

    /**
     * Method: productSavePushed product is saved.
     *
     * @param event user clicks button 'Save'
     */

    @FXML
    void productSavePushed(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modifyProductID.getText());
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: The Max must be greater or equal to Min.");
                alert.showAndWait();
                return;
            }

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Inventory must be within min and max range.");
                alert.showAndWait();
                return;
            }

            Product updatedProduct = new Product(id, name, price, stock, min, max);
            if (updatedProduct != associatedPartsList) {
                Inventory.updateProduct(currentIndex, updatedProduct);
            }

            for (Part part: associatedPartsList) {
                if (part != associatedPartsList)
                    updatedProduct.addAssociatedParts(part);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/imhoff/wgubike/MainScreen.fxml"));
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
     * Method: modifyProductPartSearch text box takes text and searches for parts in table.
     *
     * @param event for when text is entered and user types 'return' key
     */
    @FXML
    void modifyProductPartSearch(ActionEvent event) {
        String searchText = modifyProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            modifyProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }
}
