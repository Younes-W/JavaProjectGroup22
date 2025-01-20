package Group22.Main;

import Group22.API.Dashboard;
import Group22.API.InitializationThread;
import Group22.Errorhandling.Logging;
import Group22.GUI.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Entry point for the Drone Application. Initializes the application and transitions from loading screen to main UI.
 */
public class Main extends Application {

    /**
     * Main method that launches the JavaFX application.
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application, sets up initial scenes and background initialization.
     * @param primaryStage the primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        Logging.initialize("Logfile.log");
        try {
            primaryStage.setTitle("Drone Application");
            Image icon = new Image("drone.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);
            primaryStage.setResizable(false);

            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/GUI/LoadingScreen.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScreenScene = new Scene(loadingRoot);
            loadingScreenScene.getStylesheets()
                    .add(getClass().getResource("/GUI/style.css").toExternalForm());
            primaryStage.setScene(loadingScreenScene);
            primaryStage.show();

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/Main.fxml"));
            Parent mainRoot = mainLoader.load();
            Scene mainScene = new Scene(mainRoot);
            mainScene.getStylesheets()
                    .add(getClass().getResource("/GUI/style.css").toExternalForm());
            MainController mainController = mainLoader.getController();

            Task<Void> initTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    InitializationThread initThreadTask = new InitializationThread(0);
                    initThreadTask.run();
                    Dashboard dashboard = initThreadTask.getDashboard();
                    Platform.runLater(() -> {
                        mainController.setDashboard(dashboard);
                        mainController.loadDroneIds();
                        mainController.loadDroneTypeIds();
                        mainController.resetLabels();
                        primaryStage.setScene(mainScene);
                    });
                    return null;
                }
            };

            Thread initThread = new Thread(initTask);
            initThread.setDaemon(true);
            initThread.start();

        } catch (IOException e) {
            Logging.error("Error loading FXML file:");
            e.printStackTrace();
        } catch (Exception e) {
            Logging.error("Unexpected error:");
            e.printStackTrace();
        }
    }
}
