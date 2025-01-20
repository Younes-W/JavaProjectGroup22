package Group22.Main;
import Group22.API.*;
import Group22.Errorhandling.Logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// GUI
import Group22.GUI.LoadingScreenController;
import Group22.GUI.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;


import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Logging.initialize("Logfile.log");
        try {

            // Stage Title
            primaryStage.setTitle("Drone Application");

            // Stage Task Bar
            //primaryStage.initStyle(StageStyle.UNDECORATED);

            // Stage Icon
            Image icon = new Image("Tom.png");
            primaryStage.getIcons().add(icon);

            // Stage Size
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);

            // Turn off resizable
            primaryStage.setResizable(false);

            // LoadingScreen Scene Setup
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/GUI/LoadingScreen.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScreenScene = new Scene(loadingRoot);
            loadingScreenScene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());
            primaryStage.setScene(loadingScreenScene);
            primaryStage.show();

            // Main Scene Setup
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/Main.fxml"));
            Parent mainRoot = mainLoader.load();
            Scene mainScene = new Scene(mainRoot);
            mainScene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());

            MainController mainController = mainLoader.getController();

            InitializationThread initThreadTask = new InitializationThread(0);
            Thread initThread = new Thread(() -> {
                initThreadTask.run();
                Platform.runLater(() -> {
                    mainController.setDashboard(initThreadTask.getDashboard());
                    mainController.loadDroneIds();
                    mainController.loadDroneTypeIds();
                    mainController.resetLabels();
                    primaryStage.setScene(mainScene);
                });
            });
            initThread.setDaemon(true);
            initThread.start();


        } catch (IOException e) {
            System.err.println("Error loading FXML file:");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error setting up the stage:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error:");
            e.printStackTrace();
        }
    }


}
