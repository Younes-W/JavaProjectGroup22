package de.frauas.fb2.javaproject.ws25.group22.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

/**
 * Controller for the loading screen, managing the progress bar animation
 * during application initialization.
 *
 * @author Maxim Wenkemann, Torben Fechner
 */
public class LoadingScreenController {
    @FXML
    private ProgressBar progressBar;

    /**
     * Updates the progress bar with a specified progress value.
     *
     * @param progress a value between 0 and 1 indicating the progress.
     */
    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }

    /**
     * Initialization method automatically called after FXML fields are injected.
     * It starts the simulation of progress.
     */
    public void initialize() {
        simulateProgress();
    }

    /**
     * Simulates progress on the progress bar using a Timeline animation.
     * The animation runs once, transitioning the progress from 0 to 1 over 2 seconds.
     */
    public void simulateProgress() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progressBar.progressProperty(), 1))
        );

        timeline.setCycleCount(1);
        timeline.play();
    }
}
