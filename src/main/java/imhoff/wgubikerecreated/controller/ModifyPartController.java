package imhoff.wgubikerecreated.controller;

import imhoff.wgubikerecreated.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class: ModifyPartController is controller for ModifyPartController class.
 */
public class ModifyPartController  {

    @FXML private RadioButton ModifyPartInHouseRadio;
    @FXML private RadioButton ModifyPartOutsourcedRadio;
    @FXML private Label machineIDorCompanyLabel;
    @FXML private TextField modifyPartID;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInv;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField machineIDorCompanyText;
    @FXML private Button modifyPartCancelButton;

    private int currentIndex = 0;

    /**
     * Method: modifyPartCancelAction returns user to MainScreen.
     *
     * @param event when 'Cancel' button is clicked, return to MainScreen
     */

    @FXML
    public void modifyPartCancelAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/imhoff/wgubikerecreated/wgubike/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Method: sendPart takes values from MainScreen to populate text fields of ModifyPart
     *
     * @param part user clicks "In-House" or "Outsourced" to generate the event
     * @param selectedIndex Machine ID / Company Name is the last index for modification
     */

    public void sendPart(int selectedIndex, Part part){

        currentIndex = selectedIndex;

        if (part instanceof InHouse) {
            ModifyPartInHouseRadio.setSelected(true);
            machineIDorCompanyLabel.setText("Machine ID");
            machineIDorCompanyText.setText(String.valueOf(((InHouse) part).getMachineID()));
        }

        if (part instanceof Outsourced) {
            ModifyPartOutsourcedRadio.setSelected(true);
            machineIDorCompanyLabel.setText("Company Name");
            machineIDorCompanyText.setText(((Outsourced) part).getCompanyName());
        }

        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
    }

    /**
     * Method: onActionAddPartInHouse set label to 'Machine ID'.
     *
     * @param event when radio button for 'In-House' is selected.
     */
    @FXML
    public void onActionAddPartInHouse(ActionEvent event) {

        machineIDorCompanyLabel.setText("Machine ID");
    }

    /**
     * Method: onActionAddPartOutsourced set label to 'Company Name'.
     *
     * @param event when radio button for 'Outsourced' is selected.
     */
    @FXML
    public void onActionAddPartOutsourced(ActionEvent event) {

        machineIDorCompanyLabel.setText("Outsourced");
    }


    /**
     * Method: modifyPartSaveButton button to save modified part.
     *
     * @param event when 'Save' button is clicked.
     */
    @FXML
    void modifyPartSaveButton(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inStock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            int machineId;
            String companyName;

            //Min must be less than max.
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: The Max must be greater or equal to Min.");
                alert.showAndWait();
                return;
            }
            //Inventory must be within min and max values.
            if (inStock > max || inStock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Inventory must be within min and max range.");
                alert.showAndWait();
                return;
            }
            //name must be populated
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: The name field is empty");
                alert.showAndWait();
                return;
            }

            //In-House Radio selected
            if (ModifyPartInHouseRadio.isSelected()) {
                machineId = Integer.parseInt(machineIDorCompanyText.getText());
                InHouse updatedPart = new InHouse(partID, name, price, inStock, min, max, machineId);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            //Outsourced radio selected
            if (ModifyPartOutsourcedRadio.isSelected()) {
                companyName = machineIDorCompanyText.getText();
                Outsourced updatedPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            //back to MainScreen
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/imhoff/wgubike/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        //Machine ID must be numerical value not text.
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error: A numerical value is required for: Inventory, Price, Max, Min and Machine ID");
            alert.showAndWait();
        }
    }
}
