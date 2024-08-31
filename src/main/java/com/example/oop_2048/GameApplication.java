package com.example.oop_2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class GameApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(controller::keyPressed);
        stage.setTitle("2048");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
