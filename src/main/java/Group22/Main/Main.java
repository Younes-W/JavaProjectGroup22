package Group22.Main;

import Group22.API.Dashboard;
import Group22.Errorhandling.Logging;
import Group22.GUI.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Entry point for the Drone Application. Initializes the application
 * and transitions from the loading screen to the main UI.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Logging.initialize("Logfile.log");
        try {
            // Configure primary stage settings
            primaryStage.setTitle("Drone Application");
            Image icon = new Image("drone.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);
            primaryStage.setResizable(false);

            // Load the loading screen
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/GUI/LoadingScreen.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScreenScene = new Scene(loadingRoot);
            loadingScreenScene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());
            primaryStage.setScene(loadingScreenScene);
            primaryStage.show();

            // Prepare the main scene
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/Main.fxml"));
            Parent mainRoot = mainLoader.load();
            Scene mainScene = new Scene(mainRoot);
            mainScene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());
            MainController mainController = mainLoader.getController();

            // Background thread to create the dashboard
            Thread initThread = new Thread(() -> {
                try {
                    // Create Dashboard in the background
                    Dashboard dashboard = new Dashboard();

                    // After successful creation, update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        mainController.setDashboard(dashboard);
                        mainController.loadDroneIds();
                        mainController.loadDroneTypeIds();
                        mainController.resetLabels();
                        primaryStage.setScene(mainScene);
                    });
                } catch (Exception ex) {
                    Logging.error("Error during initialization: " + ex.getMessage());
                }
            });
            initThread.start();

        }catch(IOException e){
            Logging.error("Error during GUI initialization " + e.getMessage());
        }
    }
}

