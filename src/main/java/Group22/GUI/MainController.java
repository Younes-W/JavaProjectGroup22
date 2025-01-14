package Group22.GUI;

import Group22.API.Dashboard;
import Group22.API.Drone;
import Group22.API.DroneDynamics;
import Group22.API.DroneType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class MainController {

    // FXML Attributes

    @FXML
    private TabPane mainTabPane;

    // Tab 1

    @FXML
    private Tab droneTab;

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

    // Tab 2

    @FXML
    private Tab droneTypeTab;

    @FXML
    private ListView<Integer> droneTypeIdListView;

    @FXML
    private Label droneTypeManufacturerLabel;

    @FXML
    private Label noDroneTypeSelectedLabel;

    @FXML
    private Label droneTypeTypenameLabel;

    @FXML
    private Label droneTypeWeightLabel;

    @FXML
    private Label droneTypeMaximumSpeedLabel;

    @FXML
    private Label droneTypeBatteryCapacityLabel;

    @FXML
    private Label droneTypeControlRangeLabel;

    @FXML
    private Label droneTypeMaximumCarriageLabel;

    // ----------

    private Dashboard dashboard;

    // Maps for Drones and DroneTypes
    private Map<Integer, Drone> droneMap;
    private Map<Integer, DroneType> droneTypeMap;

    private Integer selectedDroneId = null;

    // ----------

    // happens automatically
    public void initialize() {
        System.out.println("Initializing Main Controller");

        dashboard = new Dashboard();

        selectedDroneId = null;

        // Load Drones into List View
        loadDroneIds();

        // Load Drone Types into List View
        loadDroneTypeIds();

        // Label
        resetLabels();

        // Listener for Drones
        droneIdListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> onDroneSelected(newValue));

        // Listener for DroneTypes
        droneTypeIdListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onDroneTypeSelected(newVal));

        // Click-Event for doneTypeLabel
        droneTypeLabel.setOnMouseClicked(event -> onDroneTypeLabelClicked());
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

    // Fill List View with DroneType Ids
    private void loadDroneTypeIds() {
        // Load DroneTypes into Map
        droneTypeMap = dashboard.getDroneTypes();

        // Create ObservableList
        ObservableList<Integer> droneTypeIds = FXCollections.observableArrayList(droneTypeMap.keySet());

        // Sort List
        FXCollections.sort(droneTypeIds);

        // Put Ids into ListView
        droneTypeIdListView.setItems(droneTypeIds);

        // Reset Selection
        droneTypeIdListView.getSelectionModel().clearSelection();

        // Reset Scroll Bar
        droneTypeIdListView.scrollTo(0);
    }

    // -------------

    // DRONE INFORMATION

    // Gets called when Drone is selected
    private void onDroneSelected(Integer droneId) {
        // When Drone was found, show Info
        if(droneId != null && droneMap.containsKey(droneId)) {
            selectedDroneId = droneId;
            Drone selectedDrone = droneMap.get(droneId); // Get Drone from ID
            if(selectedDrone != null) {

                // Make Labels Visable
                makeDroneLabelsVisable();

                // Show Information
                setDroneLabels(selectedDrone);
            }
        }
    }

    // Gets called when Drone Type is selected
    private void onDroneTypeSelected(Integer droneTypeId) {
        // Test if Id is valid
        if(droneTypeId != null && droneTypeMap.containsKey(droneTypeId)) {
            DroneType selectedDroneType = droneTypeMap.get(droneTypeId);

                // Make Labels Visable
                makeDroneTypeLabelsVisable();

                // Show Information
                setDroneTypeLabels(selectedDroneType);
        }
    }

    private void resetLabels() {
        // Tab 1
        noDroneSelectedLabel.setVisible(true);
        droneTypeLabel.setVisible(false);
        droneCreatedLabel.setVisible(false);
        droneSerialNumberLabel.setVisible(false);
        droneCarriageWeightLabel.setVisible(false);
        droneCarriageTypeLabel.setVisible(false);

        // Tab 2
        noDroneTypeSelectedLabel.setVisible(true);
        droneTypeManufacturerLabel.setVisible(false);
        droneTypeTypenameLabel.setVisible(false);
        droneTypeWeightLabel.setVisible(false);
        droneTypeMaximumSpeedLabel.setVisible(false);
        droneTypeBatteryCapacityLabel.setVisible(false);
        droneTypeControlRangeLabel.setVisible(false);
        droneTypeMaximumCarriageLabel.setVisible(false);
    }

    private void makeDroneLabelsVisable(){
        noDroneSelectedLabel.setVisible(false);
        droneTypeLabel.setVisible(true);
        droneCreatedLabel.setVisible(true);
        droneSerialNumberLabel.setVisible(true);
        droneCarriageWeightLabel.setVisible(true);
        droneCarriageTypeLabel.setVisible(true);
    }

    private void makeDroneTypeLabelsVisable(){
        noDroneTypeSelectedLabel.setVisible(false);
        droneTypeManufacturerLabel.setVisible(true);
        droneTypeTypenameLabel.setVisible(true);
        droneTypeWeightLabel.setVisible(true);
        droneTypeMaximumSpeedLabel.setVisible(true);
        droneTypeBatteryCapacityLabel.setVisible(true);
        droneTypeControlRangeLabel.setVisible(true);
        droneTypeMaximumCarriageLabel.setVisible(true);
    }

    private void setDroneLabels(Drone selectedDrone){
        droneTypeLabel.setText("Drone Type: " + (dashboard.getDroneType(selectedDrone)).getTypename());
        droneCreatedLabel.setText("Drone Created: " + selectedDrone.getCreated());
        droneSerialNumberLabel.setText("Drone Serial Number: " + selectedDrone.getSerialNumber());
        droneCarriageWeightLabel.setText("Drone Carriage Weight: " + selectedDrone.getCarriageWeight());
        droneCarriageTypeLabel.setText("Drone Carriage Type: " + selectedDrone.getCarriageType());
    }

    private void setDroneTypeLabels(DroneType selectedDroneType){
        droneTypeManufacturerLabel.setText("Manufacturer: " + selectedDroneType.getManufacturer());
        droneTypeTypenameLabel.setText("Typename: " + selectedDroneType.getTypename());
        droneTypeWeightLabel.setText("Weight: " + selectedDroneType.getWeight());
        droneTypeMaximumSpeedLabel.setText("Maximum Speed: " + selectedDroneType.getMaxSpeed());
        droneTypeBatteryCapacityLabel.setText("Battery Capacity: " + selectedDroneType.getBatteryCapacity());
        droneTypeControlRangeLabel.setText("Control Range: " + selectedDroneType.getControlRange());
        droneTypeMaximumCarriageLabel.setText("Maximum Carriage: " + selectedDroneType.getMaxCarriage());
    }

    // --------------

    private void onDroneTypeLabelClicked() {
        // 1) Aus dem aktuellen Text der `droneTypeLabel` herausfinden, welche ID das ist
        //    oder besser: Halte die ID als Feld, sobald du onDroneSelected aufrufst.

        if (selectedDroneId == null) {
            return; // Keine Drohne ausgewählt
        }

        Drone selectedDrone = droneMap.get(selectedDroneId);
        if (selectedDrone == null) {
            return;
        }

        DroneType droneType = dashboard.getDroneType(selectedDrone);
        if (droneType == null) {
            return;
        }

        Integer droneTypeId = droneType.getID();
        // Der Key, der in `droneTypeMap` verwendet wird.

        // 2) Wechsle in den 2. Tab
        mainTabPane.getSelectionModel().select(droneTypeTab);

        // 3) Markiere die `droneTypeId` in der Liste
        droneTypeIdListView.getSelectionModel().select(droneTypeId);
        droneTypeIdListView.scrollTo(droneTypeId);
    }

    @FXML
    private void refresh() {
        System.out.println("Refreshing..");

        // Refresh everything
        initialize();
    }
}
