package Group22.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoadingScreenController {
    @FXML
    private ProgressBar progressBar;

    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }
}
