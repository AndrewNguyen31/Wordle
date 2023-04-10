package edu.virginia.cs.gui;
import edu.virginia.cs.wordle.IllegalWordException;
import edu.virginia.cs.wordle.LetterResult;
import edu.virginia.cs.wordle.WordleImplementation;
import edu.virginia.cs.wordle.Wordle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Optional;
public class WordleController {
    private Wordle wordle = new WordleImplementation();
    int row = 0;
    int col = 0;
    LetterResult[] letterColors;
    @FXML
    private GridPane grid = new GridPane();
    @FXML
    private Label text;
    @FXML
    private Label realText;

    public void initialize() {
        createGrid();
        setEventHandler();
    }

    private void createGrid() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                TextField text = new TextField();
                text.setStyle("-fx-background-color: black; -fx-border-color: grey;-fx-text-fill:white;");
                text.setPrefWidth(50);
                text.setPrefHeight(400);
                text.setFont(Font.font("verdana", FontWeight.BOLD, 20));
                text.setTextFormatter(new TextFormatter<>((change) -> {
                    change.setText(change.getText().toUpperCase());
                    return change;
                }));
                text.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.length() > 1) {
                        text.setText(String.valueOf(newValue.charAt(0)));
                    }
                });
                text.setEditable(false);
                if (row == 0 && col == 0) {
                    text.setEditable(true);
                    requestFocus(text);
                }
                grid.add(text, col, row);
                Insets insets = new Insets(0, 2.5, 2.5, 2.5);
                grid.setMargin(text, insets);
            }
        }
    }

    private void setEventHandler() {
        grid.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (wordle.isGameOver()) return;
                else if (isCursor(event)) {
                    event.consume();
                    return;
                }
                String word = getWord(row);
                if (checkValidWord(word)) {
                    if (colorRow(word)) return;
                    text.setText("");
                    makeRowUneditable(row);
                    moveForward();
                } else {
                    if (event.getCode() == KeyCode.BACK_SPACE) moveBackward();
                    else if (col < 4) moveForward();
                }
                TextField newField = getTextField(col, row);
                newField.setEditable(true);
                requestFocus(newField);
            }
        });
    }

    private boolean isCursor(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        switch (code) {
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
            case PAGE_UP:
            case PAGE_DOWN:
            case HOME:
            case END:
            case TAB:
                return true;
        }
        return false;
    }

    private String getWord(int row) {
        String word = "";
        for (int col = 0; col < 5; col++) {
            word += getTextField(col, row).getText();
        }
        return word;
    }

    private boolean checkValidWord(String word) {
        try {
            letterColors = wordle.submitGuess(word);
            return true;
        } catch (IllegalWordException e) {
            if (word.length() == 5) {
                text.setText("Invalid word! Please try again.");
            }
            return false;
        }
    }

    private void requestFocus(TextField textField) {
        Platform.runLater(() -> {
            if (!textField.isFocused()) {
                textField.requestFocus();
            }
        });
    }

    private TextField getTextField(int col, int row) {
        int index = col + row * 5;
        return (TextField) grid.getChildren().get(index);
    }

    private void moveForward() {
        col = (col + 1) % 5;
        if (col == 0) row++;
    }

    private void moveBackward() {
        if (col > 0) col--;
    }

    private Boolean colorRow(String guess) {
        for (int col = 0; col < 5; col++) {
            TextField text = getTextField(col, row);
            text.setText(String.valueOf(guess.charAt(col)));
            if (letterColors[col].equals(LetterResult.GRAY)) {
                text.setStyle("-fx-background-color: dimgray; -fx-border-color: grey;-fx-text-fill:white;");
            }
            if (letterColors[col].equals(LetterResult.GREEN)) {
                text.setStyle("-fx-background-color: green; -fx-border-color: grey;-fx-text-fill:white;");
            }
            if (letterColors[col].equals(LetterResult.YELLOW)) {
                text.setStyle("-fx-background-color: #F1C40F; -fx-border-color: grey;-fx-text-fill:white;");
            }
        }
        if (!wordle.isGameOver()) return false;
        if (wordle.isWin()) text.setText("Correct! You won!");
        else if (wordle.isLoss()) text.setText("Incorrect. Out of guesses. Correct word: " + wordle.getAnswer());
        playAgainAlert();
        return true;
    }

    private void makeRowUneditable(int r) {
        for (int col = 0; col < 5; col++) {
            TextField curField = getTextField(col, r);
            curField.setEditable(false);
        }
    }

    private void playAgainAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Play again?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> YesNo = alert.showAndWait();
        if (YesNo.get() == ButtonType.YES) {
            wordle = new WordleImplementation();
            row = 0;
            col = 0;
            resetGrid();
            setEventHandler();
            text.setText("");
        } else {
            Platform.exit();
        }
    }

    private void resetGrid() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                TextField text = getTextField(col, row);
                text.setText("");
                text.setEditable(false);
                text.setStyle("-fx-background-color: black; -fx-border-color: grey;-fx-text-fill:white;");
            }
        }
        TextField text = getTextField(col, row);
        text.setEditable(true);
        requestFocus(text);
    }
}