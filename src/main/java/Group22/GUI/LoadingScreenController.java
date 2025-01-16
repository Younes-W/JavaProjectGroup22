package Group22.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class LoadingScreenController {
    @FXML
    private ProgressBar progressBar;

    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }

    public void initialize() {
        simulateProgress();
    }

    public void simulateProgress() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progressBar.progressProperty(), 1))
        );

        // Starte die Simulation
        timeline.setCycleCount(1); // Einmal durchlaufen
        timeline.play();
    }

    }
