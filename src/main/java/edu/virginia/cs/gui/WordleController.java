package edu.virginia.cs.gui;

import edu.virginia.cs.wordle.WordleImplementation;
import edu.virginia.cs.wordle.Wordle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class WordleController {
    private Wordle wordle = new WordleImplementation();
    @FXML
    private GridPane grid = new GridPane();

    private void createGrid() {

    }
}