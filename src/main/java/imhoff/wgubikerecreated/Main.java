package imhoff.wgubikerecreated;

import imhoff.wgubikerecreated.model.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class: Main builds an app to manage product inventory for a bike store.
 * <p><b>
 * RUNTIME ERROR: I experienced several runtime errors of "Exception in Application constructor" where the structure
 * of InteliJ's Maven architecture was having a hard time reading the structure I initially wanted to create that
 * included 4 packages: Main, Model, Controller, View. I had to change the structure so my view files were
 * under 'resources', and my main was a stand-alone file not embedded in a package. I was able to make a package for
 * Controller and Model. Through this process I learned how a great deal about the structure of how the IDE InteliJ is
 * built and how the various components communicate with one another.
 * </b></p>
 * <p><b>
 * RUNTIME ERROR: Upon launching the Add Part page, the 'ID' text box was uneditable, however I wanted and needed the
 * text box to have a value auto generated upon this page launching. I received an error when I thought a solution would
 * be to access the button click from the MainScreen.fxml file, but the code I wrote was in error and this reminded me
 * that the fx:controller in the fxml doc needed to be pointing to the correct controller and the MainScreen fx:controller
 * was the MainScreenController not AddPartController. My solution eventually was to import javafx.fxml.Initializable
 * and this allowed me to call "implements Initializable" to the public class and use @Override to initialize the value
 * I needed to pre-populate in the text box for 'ID'.
 * </b></p>
 * <p><b>
 * FUTURE ENHANCEMENT: In the Add Part form and Modify Part forms, when 'In-House' radio button is selected the Machine ID
 * text field does not accept text values. An Improvement would be to incorporate a rule so when the 'Outsourced' radio
 * button is selected the Outsourced text box becomes blank and prompts the user to enter a new value. Then if the user
 * again clicks back to the 'In-House' radio button, the Machine ID text field would repopulate with the value of the
 * Part ID text box. As it currently operates, the Machine ID/Outsourced text fields do not change dynamically when
 * switching between the two radio buttons.
 * </b></p>
 * <p><b>
 * FUTURE ENHANCEMENT: The search fields currently in use search for matches in the parts/product fields. The search is
 * case sensitive so 'Brake' and 'brake' are not the same. Also, if the parts 'Wheel' and 'Trek Bike' are in the list to search
 * from and the user types 'e' both 'Wheel' and 'Trek Bike' will remain because 'e' is in these values. A further
 * enhancement could be to have search begin with the first letter of the word so 'w' would refine the list to 'Wheel'
 * and searching 'T' or 'B' would produce 'Trek Bike'.
 * </b></p>
 * */
public class Main extends Application {

    /**
     * Method: Start method launches the MainScreen.fxml UI.
     *
     * @param stage : primary stage of the app that serves as the main window that is displayed to the user.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/imhoff/wgubikerecreated/wgubike/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 400);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method: main method launches the program.
     *
     * @param args : The args parameter in the main method is an array of String objects
     */
    public static void main(String[] args) {

        Part brakes = new InHouse(1, "Brakes", 15.99, 30, 15, 30, 114);
        Inventory.addPart(brakes);

        Part wheel = new InHouse(2, "Wheel", 121.99, 20, 10, 30, 115);
        Inventory.addPart(wheel);

        Part seat = new InHouse(3, "Seat", 89.99, 10, 5, 15, 116);
        Inventory.addPart(seat);

        Product GiantBike = new Product(1000, "Giant Bike", 799.99, 5, 1, 10);
        Inventory.addProduct(GiantBike);

        Product Tricycle = new Product(1001, "Tricycle", 399.99, 3, 1, 4);
        Inventory.addProduct(Tricycle);

        launch();
    }

}