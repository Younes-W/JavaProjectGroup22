package Group22.GUI;

import Group22.API.Dashboard;
import Group22.API.Drone;
import Group22.API.DroneDynamics;
import Group22.API.DroneType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Map;

public class MainController {

    // FXML Attributes

    @FXML
    private ListView<Integer> droneIdListView;

    @FXML
    private Label droneTypeLabel;

    @FXML
    private Label droneCreatedLabel;

    @FXML
    private Label droneSerialNumberLabel;

    @FXML
    private Label droneCarriageWeightLabel;

    @FXML
    private Label droneCarriageTypeLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Label noDroneSelectedLabel;

    /*@FXML
    private Label droneStatusLabel;

    @FXML
    private Label droneLastSeenLabel;*/

    // ----------

    private Dashboard dashboard;

    private Map<Integer, Drone> droneMap;

    // ----------

    // happens automatically
    public void initialize() {
        System.out.println("Initializing Main Controller");

        dashboard = new Dashboard();
        loadDroneIds(); // Load drones into List View

        // Label
        resetLabels();

        // Listener
        droneIdListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> onDroneSelected(newValue));

    }

    // LIST VIEW

    // Fill List View with Drone Ids
    private void loadDroneIds() {
        // Load Drones into Map
        droneMap = dashboard.getDrones();

        // Create ObservableList
        ObservableList<Integer> droneIds = FXCollections.observableArrayList(droneMap.keySet());

        // Sort List
        FXCollections.sort(droneIds);

        // Put Ids into ListView
        droneIdListView.setItems(droneIds);

        // Reset Selection
        droneIdListView.getSelectionModel().clearSelection();

        // Reset Scroll Bar
        droneIdListView.scrollTo(0);
    }

    // -------------

    // DRONE INFORMATION

    // Gets called when Drone is selected
    private void onDroneSelected(Integer droneId) {

        // When Drone was found, show Info
        if(droneId != null) {
            Drone selectedDrone = droneMap.get(droneId); // Get Drone from ID
            if(selectedDrone != null) {
                // Make Labels Visable
                makeLabelsVisable();

                // Show Information
                droneTypeLabel.setText("Drone Type: " + (dashboard.getDroneType(selectedDrone)).getTypename());
                droneCreatedLabel.setText("Drone Created: " + selectedDrone.getCreated());
                droneSerialNumberLabel.setText("Drone Serial Number: " + selectedDrone.getSerialNumber());
                droneCarriageWeightLabel.setText("Drone Carriage Weight: " + selectedDrone.getCarriageWeight());
                droneCarriageTypeLabel.setText("Drone Carriage Type: " + selectedDrone.getCarriageType());

                /*if (selectedDrone.getDynamicsCount() > 0) { // Verifiziere, ob die Liste nicht leer ist
                    DroneDynamics dynamics = selectedDrone.getDynamics(10); // Hole den ersten Eintrag
                    droneLastSeenLabel.setText("Last Seen: " + dynamics.getLastSeen());
                    droneStatusLabel.setText("Status: " + dynamics.getStatus());
                } else {
                    // Wenn keine Dynamics-Daten vorhanden sind
                    droneLastSeenLabel.setText("Last Seen: -");
                    droneStatusLabel.setText("Status: -");
                }*/
            }
        }
    }

    private void resetLabels() {
        noDroneSelectedLabel.setVisible(true);
        droneTypeLabel.setVisible(false);
        droneCreatedLabel.setVisible(false);
        droneSerialNumberLabel.setVisible(false);
        droneCarriageWeightLabel.setVisible(false);
        droneCarriageTypeLabel.setVisible(false);
    }

    private void makeLabelsVisable(){
        noDroneSelectedLabel.setVisible(false);
        droneTypeLabel.setVisible(true);
        droneCreatedLabel.setVisible(true);
        droneSerialNumberLabel.setVisible(true);
        droneCarriageWeightLabel.setVisible(true);
        droneCarriageTypeLabel.setVisible(true);
    }

    // --------------


    @FXML
    private void refresh() {
        System.out.println("Refreshing..");

        // Refresh DroneIds
        initialize();
    }
}
