package de.frauas.fb2.javaproject.ws25.group22.gui;

import de.frauas.fb2.javaproject.ws25.group22.api.Dashboard;
import de.frauas.fb2.javaproject.ws25.group22.api.Drone;
import de.frauas.fb2.javaproject.ws25.group22.api.DroneDynamics;
import de.frauas.fb2.javaproject.ws25.group22.api.DroneType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Controller class for the main UI of the Drone Application.
 * Handles user interactions and updates UI components based on drone data.
 *
 * @author Maxim Wenkemann, Torben Fechner, Younes Wimmer
 */
public class MainController {

    @FXML private TabPane mainTabPane;
    @FXML private ListView<Integer> droneIdListView;
    @FXML private Label droneTypeLabel;
    @FXML private Label droneCreatedLabel;
    @FXML private Label droneSerialNumberLabel;
    @FXML private Label droneCarriageWeightLabel;
    @FXML private Label droneCarriageTypeLabel;
    @FXML private Label noDroneSelectedLabel;
    @FXML private Button droneDynamicsButton;
    @FXML private VBox droneInfoVBox;
    @FXML private VBox droneDynamicsVBox;
    @FXML private Label dynamicsStatusLabel;
    @FXML private Label dynamicsBatteryStatusLabel;
    @FXML private Label dynamicsBatteryPercentLabel;
    @FXML private Label dynamicsTimestampLabel;
    @FXML private Label dynamicsLatitudeLabel;
    @FXML private Label dynamicsLongitudeLabel;
    @FXML private Label dynamicsSpeedLabel;
    @FXML private Label dynamicsAlignmentRollLabel;
    @FXML private Label dynamicsAlignmentYawLabel;
    @FXML private Label dynamicsAlignmentPitchLabel;
    @FXML private Label dynamicsLastSeenLabel;
    @FXML private Label dynamicsDistanceLabel;
    @FXML private Label dynamicsSpeedOTLabel;
    @FXML private Label dynamicsBatteryConsumptionLabel;
    @FXML private HBox navigationButtons;
    @FXML private Tab droneTypeTab;
    @FXML private Tab droneTab;
    @FXML private ListView<Integer> droneTypeIdListView;
    @FXML private Label droneTypeManufacturerLabel;
    @FXML private Label noDroneTypeSelectedLabel;
    @FXML private Label droneTypeTypenameLabel;
    @FXML private Label droneTypeWeightLabel;
    @FXML private Label droneTypeMaximumSpeedLabel;
    @FXML private Label droneTypeBatteryCapacityLabel;
    @FXML private Label droneTypeControlRangeLabel;
    @FXML private Label droneTypeMaximumCarriageLabel;
    @FXML private VBox droneTypesVBox;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Dashboard dashboard;

    /**
     * Initializes the MainController by setting up listeners and default UI states.
     */
    public void initialize() {
        LOGGER.info("Initializing Main Controller");
        droneIdListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onDroneSelected(newValue));
        droneTypeIdListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> onDroneTypeSelected(newVal));
        droneTypeLabel.setOnMouseClicked(event -> onDroneTypeLabelClicked());
    }

    /**
     * Loads drone IDs into the droneIdListView.
     */
    public void loadDroneIds() {
        droneIdListView.getSelectionModel().clearSelection();
        ObservableList<Integer> droneIds = FXCollections.observableArrayList(dashboard.getDrones().keySet());
        FXCollections.sort(droneIds);
        droneIdListView.setItems(droneIds);
        droneIdListView.scrollTo(0);
    }

    /**
     * Loads drone type IDs into the droneTypeIdListView.
     */
    public void loadDroneTypeIds() {
        droneTypeIdListView.getSelectionModel().clearSelection();
        ObservableList<Integer> droneTypeIds = FXCollections.observableArrayList(dashboard.getDroneTypes().keySet());
        FXCollections.sort(droneTypeIds);
        droneTypeIdListView.setItems(droneTypeIds);
        droneTypeIdListView.scrollTo(0);
    }

    // Handles the selection of a drone and updates the UI accordingly
    private void onDroneSelected(Integer droneId) {
        if (droneId != null && dashboard.getDrones().containsKey(droneId)) {
            Drone selectedDrone = dashboard.getDrone(droneId);
            if (selectedDrone != null) {
                noDroneSelectedLabel.setVisible(false);
                if (droneDynamicsVBox.isVisible()) {
                    dashboard.setSelectedDrone(selectedDrone);
                    makeDroneDynamicsLabelsVisible();
                    setDroneDynamicsLabels(selectedDrone);
                } else {
                    droneDynamicsVBox.setVisible(false);
                    makeDroneLabelsVisible();
                    setDroneLabels(selectedDrone);
                }
            }
        }
    }

    // Handles the selection of a drone type and updates the UI accordingly
    private void onDroneTypeSelected(Integer droneTypeId) {
        if (droneTypeId != null && dashboard.getDroneTypes().containsKey(droneTypeId)) {
            DroneType selectedDroneType = dashboard.getDroneTypes().get(droneTypeId);
            makeDroneTypeLabelsVisible();
            setDroneTypeLabels(selectedDroneType);
        }
    }

    /**
     * Resets UI labels and visibility to default state.
     */
    public void resetLabels() {
        noDroneSelectedLabel.setVisible(true);
        droneInfoVBox.setVisible(false);
        droneDynamicsVBox.setVisible(false);
        droneTypesVBox.setVisible(false);
        LOGGER.info("Resetting Labels done");
    }

    // Makes drone information labels visible and hides dynamics labels
    private void makeDroneLabelsVisible() {
        noDroneSelectedLabel.setVisible(false);
        droneInfoVBox.setVisible(true);
        droneDynamicsVBox.setVisible(false);
    }

    // Makes drone type labels visible
    private void makeDroneTypeLabelsVisible() {
        noDroneTypeSelectedLabel.setVisible(false);
        droneTypesVBox.setVisible(true);
    }

    // Makes drone dynamics labels visible and hides other labels
    private void makeDroneDynamicsLabelsVisible() {
        droneInfoVBox.setVisible(false);
        droneDynamicsVBox.setVisible(true);
        noDroneTypeSelectedLabel.setVisible(false);
        droneTypesVBox.setVisible(false);
    }

    // Sets the drone information labels based on the selected drone
    private void setDroneLabels(Drone selectedDrone) {
        DroneType type = dashboard.getDroneType(selectedDrone);
        droneTypeLabel.setText("Type: " + (type != null ? type.getTypename() : "Unknown"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        droneCreatedLabel.setText("Created: " + selectedDrone.getCreated().format(dateFormat));
        droneSerialNumberLabel.setText("Serial Number: " + selectedDrone.getSerialNumber());
        droneCarriageWeightLabel.setText("Carriage Weight: " + selectedDrone.getCarriageWeight());
        droneCarriageTypeLabel.setText("Carriage Type: " + selectedDrone.getCarriageType());
    }

    // Sets the drone type labels based on the selected drone type
    private void setDroneTypeLabels(DroneType selectedDroneType) {
        droneTypeManufacturerLabel.setText("Manufacturer: " + selectedDroneType.getManufacturer());
        droneTypeTypenameLabel.setText("Typename: " + selectedDroneType.getTypename());
        droneTypeWeightLabel.setText("Weight: " + selectedDroneType.getWeight());
        droneTypeMaximumSpeedLabel.setText("Maximum Speed: " + selectedDroneType.getMaxSpeed());
        droneTypeBatteryCapacityLabel.setText("Battery Capacity: " + selectedDroneType.getBatteryCapacity());
        droneTypeControlRangeLabel.setText("Control Range: " + selectedDroneType.getControlRange());
        droneTypeMaximumCarriageLabel.setText("Maximum Carriage: " + selectedDroneType.getMaxCarriage());
    }

    // Sets the drone dynamics labels based on the selected drone's dynamics
    private void setDroneDynamicsLabels(Drone selectedDrone) {
        int offset = dashboard.getOffset();
        if (offset >= selectedDrone.getDynamicsCount() || offset < 0) {
            return;
        }
        DroneDynamics firstDynamics = selectedDrone.getDynamics(offset);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        dynamicsTimestampLabel.setText("Timestamp: " + firstDynamics.getTimestamp().format(dateFormat));
        dynamicsSpeedLabel.setText("Speed: " + firstDynamics.getSpeed());
        dynamicsAlignmentRollLabel.setText("Align Roll: " + firstDynamics.getAlignRoll());
        dynamicsAlignmentPitchLabel.setText("Align Pitch: " + firstDynamics.getAlignPitch());
        dynamicsAlignmentYawLabel.setText("Align Yaw: " + firstDynamics.getAlignYaw());
        dynamicsLongitudeLabel.setText("Longitude: " + firstDynamics.getLongitude());
        dynamicsLatitudeLabel.setText("Latitude: " + firstDynamics.getLatitude());
        dynamicsBatteryStatusLabel.setText("Battery Status: " + firstDynamics.getBatteryStatus());
        dynamicsLastSeenLabel.setText("Last Seen: " + firstDynamics.getLastSeen().format(dateFormat));
        dynamicsStatusLabel.setText("Status: " + firstDynamics.getStatus());
        dynamicsDistanceLabel.setText("Distance: " +
                String.format("%.2f", selectedDrone.calculateDistanceUpTo(offset)) + " km");
        dynamicsSpeedOTLabel.setText("Speed Over Time: " +
                String.format("%.2f", selectedDrone.calculateAverageSpeedUpTo(offset)) + " km/h");
        DroneType type = dashboard.getDroneType(selectedDrone);
        double batteryPercent = firstDynamics.calculateBatteryPercentage(type);
        dynamicsBatteryPercentLabel.setText("Battery In Percent: " + String.format("%.2f", batteryPercent));
        dynamicsBatteryConsumptionLabel.setText("Battery Consumption In Percent: " +
                String.format("%.2f", firstDynamics.calculateBatteryConsumptionPercentage(batteryPercent)));
    }

    // Handles click events on the drone type label to navigate to the corresponding drone type details
    private void onDroneTypeLabelClicked() {
        Integer selectedId = droneIdListView.getSelectionModel().getSelectedItem();
        if (selectedId == null) return;
        Drone selectedDrone = dashboard.getDrone(selectedId);
        if (selectedDrone == null) return;
        DroneType droneType = dashboard.getDroneType(selectedDrone);
        if (droneType == null) return;
        Integer droneTypeId = droneType.getId();
        mainTabPane.getSelectionModel().select(droneTypeTab);
        droneTypeIdListView.getSelectionModel().select(droneTypeId);
        droneTypeIdListView.scrollTo(droneTypeId);
    }

    @FXML
    private void refreshButton() {
        LOGGER.info("Refreshing.. ");
        dashboard.apiRefresh();
        resetLabels();
        loadDroneIds();
        loadDroneTypeIds();
        droneDynamicsButton.setText("Drone Dynamics");
        navigationButtons.setVisible(false);
        mainTabPane.getSelectionModel().select(droneTab);
        updateDroneDynamicsButtonStyle();
    }

    @FXML
    private void setDroneDynamicsButton() {
        Integer currentSelection = droneIdListView.getSelectionModel().getSelectedItem();
        if (currentSelection != null) {
            boolean showingDynamics = droneDynamicsVBox.isVisible();
            if (!showingDynamics) {
                droneInfoVBox.setVisible(false);
                droneDynamicsVBox.setVisible(true);
                droneDynamicsButton.setText("Drone Information");
                navigationButtons.setVisible(true);
                Drone selectedDrone = dashboard.getDrone(currentSelection);
                if (selectedDrone != null) {
                    dashboard.setSelectedDrone(selectedDrone);
                    setDroneDynamicsLabels(selectedDrone);
                }
            } else {
                droneDynamicsVBox.setVisible(false);
                droneInfoVBox.setVisible(true);
                droneDynamicsButton.setText("Drone Dynamics");
                navigationButtons.setVisible(false);
            }
            updateDroneDynamicsButtonStyle();
        }
    }

    @FXML
    private void updateDroneDynamicsButtonStyle() {
        droneDynamicsButton.getStyleClass().removeAll("button-dynamics-selected", "button-dynamics-unselected");
        if (droneDynamicsVBox.isVisible()) {
            droneDynamicsButton.getStyleClass().add("button-dynamics-selected");
        } else {
            droneDynamicsButton.getStyleClass().add("button-dynamics-unselected");
        }
    }

    // Navigation for drone dynamics pagination
    @FXML private void onNextDynamicClicked1() { proceedNextDynamic(1); }
    @FXML private void onNextDynamicClicked5() { proceedNextDynamic(5); }
    @FXML private void onNextDynamicClicked50() { proceedNextDynamic(50); }
    @FXML private void onNextDynamicClicked500() { proceedNextDynamic(500); }
    @FXML private void onPrevDynamicClicked1() { proceedNextDynamic(-1); }
    @FXML private void onPrevDynamicClicked5() { proceedNextDynamic(-5); }
    @FXML private void onPrevDynamicClicked50() { proceedNextDynamic(-50); }
    @FXML private void onPrevDynamicClicked500() { proceedNextDynamic(-500); }

    // Advances or goes back in dynamics data by a given number of steps
    private void proceedNextDynamic(int steps) {
        Drone selectedDrone = dashboard.getSelectedDrone();
        if (selectedDrone == null) {
            return;
        }
        dashboard.updateSelectedDynamics(steps);
        setDroneDynamicsLabels(selectedDrone);
    }

    /**
     * Sets the dashboard instance for the controller.
     *
     * @param dashboard the Dashboard to set.
     */
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

}
