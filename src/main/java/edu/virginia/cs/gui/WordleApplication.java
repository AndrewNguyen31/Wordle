package edu.virginia.cs.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordleApplication.class.getResource("wordle-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.setTitle("Welcome to Wordle!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}