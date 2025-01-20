package Group22.GUI;

import Group22.API.*;
import Group22.Errorhandling.Logging;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


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

    @FXML
    private Button droneDynamicsButton;

    @FXML
    private VBox droneInfoVBox;

    @FXML
    private VBox droneDynamicsVBox;

    @FXML
    private Label dynamicsDroneLabel;

    @FXML
    private Label dynamicsStatusLabel;

    @FXML
    private Label dynamicsBatteryStatusLabel;
    @FXML
    private Label dynamicsBatteryPercentLabel;

    @FXML
    private Label dynamicsTimestampLabel;

    @FXML
    private Label dynamicsLatitudeLabel;

    @FXML
    private Label dynamicsLongitudeLabel;

    @FXML
    private Label dynamicsSpeedLabel;

    @FXML
    private Label dynamicsAlignmentRollLabel;
    @FXML
    private Label dynamicsAlignmentYawLabel;
    @FXML
    private Label dynamicsAlignmentPitchLabel;
    @FXML
    private Label dynamicsLastSeenLabel;
    @FXML
    private Label dynamicsDistanceLabel;
    @FXML
    private Label dynamicsSpeedOTLabel;
    @FXML
    private Label dynamicsBatteryConsumptionLabel;
    @FXML
    private HBox navigationButtons;

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

    @FXML
    private VBox droneTypesVBox;

    // ----------

    private Dashboard dashboard;

    private Integer selectedDroneId = null;

    private boolean isDroneDynamicsSelected = false;

    private Logging logging;

    // ----------

    // happens automatically
    public void initialize() {
        logging.info("Initializing Main Controller");

        selectedDroneId = null;

        //AUTO API REFRESH 5 Minutes TODO
        startPeriodicRefresh(15);

        // Listener for Drones
        droneIdListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> onDroneSelected(newValue));

        // Listener for DroneTypes
        droneTypeIdListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onDroneTypeSelected(newVal));

        // Click-Event for doneTypeLabel
        droneTypeLabel.setOnMouseClicked(event -> onDroneTypeLabelClicked());

        // Reset Drone Dynamics Button
        updateDroneDynamicsButtonStyle();
    }

    //API Auto-refresh TODO
    private void startPeriodicRefresh(int minutes) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.minutes(minutes),
                        event -> {
                            // 1) Dashboard refreshen
                            dashboard.apiRefresh();

                            resetLabels();
                            loadDroneIds();
                            loadDroneTypeIds();
                            // Falls du mehr machen willst, z. B. selectedDrone neu anzeigen,
                            // kannst du das hier ebenfalls tun.

                            logging.info("Automatischer API-Refresh um "
                                    + java.time.LocalTime.now());
                        }
                )
        );
        // Unendlich oft wiederholen
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // LIST VIEW

    // Fill List View with Drone Ids
    public void loadDroneIds() {

        // Reset Selection
        droneIdListView.getSelectionModel().clearSelection();

        // Create ObservableList
        ObservableList<Integer> droneIds = FXCollections.observableArrayList((dashboard.getDrones()).keySet());

        // Sort List
        FXCollections.sort(droneIds);

        // Put Ids into ListView
        droneIdListView.setItems(droneIds);

        // Reset Scroll Bar
        droneIdListView.scrollTo(0);
    }

    // Fill List View with DroneType Ids
    public void loadDroneTypeIds() {
        // Reset Selection
        droneTypeIdListView.getSelectionModel().clearSelection();

        // Create ObservableList
        ObservableList<Integer> droneTypeIds = FXCollections.observableArrayList((dashboard.getDroneTypes()).keySet());

        // Sort List
        FXCollections.sort(droneTypeIds);

        // Put Ids into ListView
        droneTypeIdListView.setItems(droneTypeIds);

        // Reset Scroll Bar
        droneTypeIdListView.scrollTo(0);
    }

    // -------------

    // DRONE INFORMATION

    // Gets called when Drone is selected
    private void onDroneSelected(Integer droneId) {
        //Offset == 0, etc.
        dashboard.uiRefresh();
        // When Drone was found, show Info
        if(droneId != null && (dashboard.getDrones()).containsKey(droneId)) {
            selectedDroneId = droneId;
            Drone selectedDrone = (dashboard.getDrones()).get(droneId); // Get Drone from ID

            if(selectedDrone != null) {
                noDroneSelectedLabel.setVisible(false);

                if (isDroneDynamicsSelected == true) {

                    dashboard.setSelectedDrone(selectedDrone);
                    makeDroneDynamicsLabelsVisible();
                    setDroneDynamicsLabels(dashboard.getSelectedDrone());

                }else {

                    droneDynamicsVBox.setVisible(false);
                    // Make Labels Visable
                    makeDroneLabelsVisible();

                    // Show Information
                    setDroneLabels(selectedDrone);
                }
            }
        }
    }

    // Gets called when Drone Type is selected
    private void onDroneTypeSelected(Integer droneTypeId) {
        // Test if Id is valid
        if(droneTypeId != null && (dashboard.getDroneTypes()).containsKey(droneTypeId)) {
            DroneType selectedDroneType = (dashboard.getDroneTypes()).get(droneTypeId);

                // Make Labels Visable
                makeDroneTypeLabelsVisible();

                // Show Information
                setDroneTypeLabels(selectedDroneType);
        }
    }

    public void resetLabels() {
        // Tab 1
        noDroneSelectedLabel.setVisible(true);
        droneInfoVBox.setVisible(false);

        //Drone Dynamics
        droneDynamicsVBox.setVisible(false);

        // Tab 2
        droneTypesVBox.setVisible(false);

        System.out.println("Resetlabels fertig");
    }

    private void makeDroneLabelsVisible(){
        noDroneSelectedLabel.setVisible(false);
        droneInfoVBox.setVisible(true);
        droneDynamicsVBox.setVisible(false);
    }

    private void makeDroneTypeLabelsVisible(){
        noDroneTypeSelectedLabel.setVisible(false);
        droneTypesVBox.setVisible(true);
    }

    private void makeDroneDynamicsLabelsVisible() {
        // Tab 1
        droneInfoVBox.setVisible(false);
        droneDynamicsVBox.setVisible(true);

        // Tab 2
        noDroneTypeSelectedLabel.setVisible(false);
        droneTypesVBox.setVisible(false);
    }

    private void setDroneLabels(Drone selectedDrone){
        droneTypeLabel.setText("Type: " + (dashboard.getDroneType(selectedDrone)).getTypename());
        droneCreatedLabel.setText("Created: " + selectedDrone.getCreated());
        droneSerialNumberLabel.setText("Serial Number: " + selectedDrone.getSerialNumber());
        droneCarriageWeightLabel.setText("Carriage Weight: " + selectedDrone.getCarriageWeight());
        droneCarriageTypeLabel.setText("Carriage Type: " + selectedDrone.getCarriageType());
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

    private void setDroneDynamicsLabels(Drone selectedDrone){
        int offset = dashboard.getOffset(); //TODO Refresh button Offset Backend
        if (offset >= selectedDrone.getDynamicsCount() || offset < 0) {
            return;
        }
        DroneDynamics firstDynamics = selectedDrone.getDynamics(offset);
        dynamicsTimestampLabel.setText("Timestamp: " + firstDynamics.getTimestamp());
        dynamicsSpeedLabel.setText("Speed: " + firstDynamics.getSpeed());
        dynamicsAlignmentRollLabel.setText("Align Roll: " + firstDynamics.getAlignRoll());
        dynamicsAlignmentPitchLabel.setText("Align Pitch: " + firstDynamics.getAlignPitch());
        dynamicsAlignmentYawLabel.setText("Align Yaw: " + firstDynamics.getAlignYaw());
        dynamicsLongitudeLabel.setText("Longitude: " + firstDynamics.getLongitude());
        dynamicsLatitudeLabel.setText("Latitude: " + firstDynamics.getLatitude());
        dynamicsBatteryStatusLabel.setText("Battery Status: " + firstDynamics.getBatteryStatus());
        dynamicsLastSeenLabel.setText("Last Seen: " + firstDynamics.getLastSeen());
        dynamicsStatusLabel.setText("Status: " + firstDynamics.getStatus());
        dynamicsDistanceLabel.setText("Distance: " + String.format("%.2f",selectedDrone.calculateDistanceUpTo(offset)) + " km"); //TODO Younes
        dynamicsSpeedOTLabel.setText("Speed Over Time: " + String.format("%.2f", selectedDrone.calculateAverageSpeedUpTo(offset)) + " km/h"); //TODO Younes
        dynamicsBatteryPercentLabel.setText("Battery In Percent: " + String.format("%.2f", firstDynamics.getBatteryPercentage(dashboard.getDroneType(selectedDrone))));
        dynamicsBatteryConsumptionLabel.setText("Battery Consumption In Percent: " + String.format("%.2f",firstDynamics.getBatteryConsumptionInPercent(firstDynamics.getBatteryPercentage(dashboard.getDroneType(selectedDrone)))));
    }

    // --------------

    private void onDroneTypeLabelClicked() {
        // 1) Aus dem aktuellen Text der `droneTypeLabel` herausfinden, welche ID das ist
        //    oder besser: Halte die ID als Feld, sobald du onDroneSelected aufrufst.

        if (selectedDroneId == null) {
            return; // Keine Drohne ausgewählt
        }

        Drone selectedDrone = (dashboard.getDrones()).get(selectedDroneId);

        if (selectedDrone == null) {
            return;
        }

        DroneType droneType = dashboard.getDroneType(selectedDrone);
        if (droneType == null) {
            return;
        }

        Integer droneTypeId = droneType.getID();
        // Get Id von Drone

        // 2) Wechsle in den 2. Tab
        mainTabPane.getSelectionModel().select(droneTypeTab);

        // 3) Markiere die `droneTypeId` in der Liste
        droneTypeIdListView.getSelectionModel().select(droneTypeId);
        droneTypeIdListView.scrollTo(droneTypeId);
    }

    @FXML
    private void refresh() {
        logging.info("Refreshing.. ");

        System.out.println("Refresh");

        dashboard.apiRefresh();

        resetLabels();

        loadDroneIds();

        loadDroneTypeIds();

        // Reset Drone Dynamics Button
        droneDynamicsButton.setText("Drone Dynamics");
        isDroneDynamicsSelected = false;
        navigationButtons.setVisible(false);
        updateDroneDynamicsButtonStyle();

        // Reset Drone Id so no drone is selected
        selectedDroneId = null;
    }

    @FXML
    private void setDroneDynamicsButton(){
        if(selectedDroneId != null){
            if (isDroneDynamicsSelected== false){
                droneInfoVBox.setVisible(false);
                droneDynamicsVBox.setVisible(true);
                isDroneDynamicsSelected = true;
                droneDynamicsButton.setText("Drone Information");

                navigationButtons.setVisible(true);

                //NEW: Dynamics filling instant TODO
                Drone selectedDrone = (dashboard.getDrones()).get(selectedDroneId);
                if(selectedDrone!=null){
                    dashboard.setSelectedDrone(selectedDrone);
                    setDroneDynamicsLabels(selectedDrone);
                }

            }else {
                droneDynamicsVBox.setVisible(false);
                droneInfoVBox.setVisible(true);
                isDroneDynamicsSelected = false;
                droneDynamicsButton.setText("Drone Dynamics");
                navigationButtons.setVisible(false);
            }

            // Refresh Button CSS Style
            updateDroneDynamicsButtonStyle();
        }

    }

    @FXML
    private void updateDroneDynamicsButtonStyle() {
        // Reset CSS Class
        droneDynamicsButton.getStyleClass().removeAll("button-dynamics-selected", "button-dynamics-unselected");

        // Add new CSS Class
        if (isDroneDynamicsSelected) {
            droneDynamicsButton.getStyleClass().add("button-dynamics-selected");
        } else {
            droneDynamicsButton.getStyleClass().add("button-dynamics-unselected");
        }
    }

    //RÜCKWERTS / VORWÄRTS
    @FXML
    private void onNextDynamicClicked1() {
        proceedNextDynamic(1);
    }
    @FXML
    private void onNextDynamicClicked5() {
        proceedNextDynamic(5);
    }
    @FXML
    private void onNextDynamicClicked50() {
        proceedNextDynamic(50);
    }
    @FXML
    private void onNextDynamicClicked500() {
        proceedNextDynamic(500);
    }
    @FXML
    private void onPrevDynamicClicked1() {
        proceedNextDynamic(-1);
    }
    @FXML
    private void onPrevDynamicClicked5() {
        proceedNextDynamic(-5);
    }
    @FXML
    private void onPrevDynamicClicked50() {
        proceedNextDynamic(-50);
    }
    @FXML
    private void onPrevDynamicClicked500() {
        proceedNextDynamic(-500);
    }

    private void proceedNextDynamic(int steps) {
        Drone selectedDrone = dashboard.getSelectedDrone();
        if (selectedDrone == null) {
            return;
        }

        dashboard.updateSelectedDynamics(steps);
        setDroneDynamicsLabels(selectedDrone);
    }

    public void setDashboard(Dashboard dashboard){
        this.dashboard = dashboard;
    }
}
