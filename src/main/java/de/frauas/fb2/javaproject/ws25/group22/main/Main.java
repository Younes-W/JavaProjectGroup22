package de.frauas.fb2.javaproject.ws25.group22.main;

import de.frauas.fb2.javaproject.ws25.group22.api.Dashboard;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.Logging;
import de.frauas.fb2.javaproject.ws25.group22.gui.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Entry point for the Drone Application.
 * Initializes logging, sets up the primary stage, and transitions from the loading screen to the main UI.
 *
 * @author Maxim Wenkemann, Torben Fechner
 */
public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * The main method which launches the JavaFX application.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by initializing logging, setting up the primary stage,
     * loading the loading screen, and preparing the main scene.
     *
     * @param primaryStage the primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        Logging.initialize("Logfile.log");
        try {
            primaryStage.setTitle("Drone Application");
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
                    Dashboard dashboard = new Dashboard();

                    Platform.runLater(() -> {
                        mainController.setDashboard(dashboard);
                        mainController.loadDroneIds();
                        mainController.loadDroneTypeIds();
                        mainController.resetLabels();
                        primaryStage.setScene(mainScene);
                    });
                } catch (Exception ex) {
                    LOGGER.severe("Error during initialization: " + ex.getMessage());
                }
            });
            initThread.start();

        } catch (IOException ioException) {
            LOGGER.severe("Error during GUI initialization: " + ioException.getMessage());
        }
    }
}
