package Group22.Main;
import Group22.API.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// GUI
import Group22.GUI.MainController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        Dashboard d1 = new Dashboard();
        Map<Integer,Drone> d = d1.getDrones();
        for( Integer key : d.keySet() ) {
            System.out.println(key);
            System.out.println(d.get(key));
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Main.fxml"));
            Parent root = loader.load();

            // Scene Setup
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

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
