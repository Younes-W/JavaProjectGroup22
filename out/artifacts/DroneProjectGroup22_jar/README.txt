To run the JAR file

1. use the following command in your terminal or command prompt:

java --module-path /path/to/javafx-sdk-23.0.1/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.web \
     --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
     --add-opens javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
     -jar DroneProjectGroup22.jar

2. Replace /path/to/javafx-sdk-23.0.1/lib with the actual path to the lib directory of your JavaFX SDK installation.

Note: Ensure that the JavaFX SDK version matches the one specified (23.0.1) to avoid compatibility issues.