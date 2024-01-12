package imhoff.wgubikerecreated.controller;

import imhoff.wgubikerecreated.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *  Class: AddPartController holds logic for UI of AddPart.fxml
 */
public class AddPartController implements Initializable {

    @FXML private Label MachineIDorCompany;
    @FXML private RadioButton PartInHouseRadio;
    @FXML private RadioButton PartOutsourcedRadio;
    @FXML private Button addPartSaveButton;
    @FXML private Button addPartCancelButton;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInventory;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMachineID;
    @FXML private TextField addPartID;

    private static int id = 50;
    /**
     * Method: onActionAddPartOutsourced, when button is pressed the AddPart.fxml form changes Machine ID to Company Name.
     *
     * @param event the AddPart form changes Machine ID to Company Name.
     */
    @FXML
    void onActionAddPartOutsourced(ActionEvent event) {
        MachineIDorCompany.setText("Outsourced");
    }

    /**
     * Method: onActionAddPartInHouse when selected the text is set to Machine ID.
     *
     * @param event the AddPart form text is set to Machine ID.
     */
    @FXML
    void onActionAddPartInHouse(ActionEvent event) {
        MachineIDorCompany.setText("Machine ID");
    }

    /**
     * Method: onPartCancelAction when user pushes cancel button the UI screen is exited and MainScreen.fxml is displayed
     *
     * @param event the action of clicking the button loads the MainScreen.fxml
     */
    @FXML
    public void addPartCancelAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/imhoff/wgubike/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();

    }

    /**
     * Method: addPartSaveButton. When selected the part is saved.
     *
     * @param event the part is saved when the button is clicked.
     */
    @FXML
    void addPartSaveButton (ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(addPartID.getText());
            String name = addPartName.getText();
            int inStock = Integer.parseInt(addPartInventory.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int machineId;
            String companyName;

            // Name cannot be blank
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: The name field is empty");
                alert.showAndWait();
                return;
            }

            // Max must be larger than Min.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Max must be greater than min");
                alert.showAndWait();
                return;
            }

            //Inventory value must be within min and max.
            if (inStock < min || max < inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Inventory value must be within min and max");
                alert.showAndWait();
                return;
            }

            if (PartInHouseRadio.isSelected()) {
                machineId = Integer.parseInt(addPartMachineID.getText());
                InHouse addInHousePart = new InHouse(id, name, price, inStock, min, max, machineId);
                Inventory.addPart(addInHousePart);
            }

            if (PartOutsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced addOutsourcedPart = new Outsourced(id, name, price, inStock, min, max, companyName);
                Inventory.addPart(addOutsourcedPart);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Error: A numerical value is required for: Inventory, Price, Max, Min and Machine ID");
            alert.showAndWait();
        }
    }

    /**
     * Method: getPartIDCount increments value of id by one every time new part is added.
     *
     * @return the part id value
     */
    public static int getPartIDCount() {
        id++;
        return id;
    }

    /**
     * Method: initialize sets the PartID to the text box 'ID'.
     *
     * @param url defines resource location
     * @param resourceBundle accesses local resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartInHouseRadio.setSelected(true);
        id = getPartIDCount();
        addPartID.setText(String.valueOf(id));
        addPartMachineID.setText(String.valueOf(id));
    }
}