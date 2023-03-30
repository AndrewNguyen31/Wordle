module WordleGUI.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.virginia.cs.gui to javafx.fxml;
    exports edu.virginia.cs.gui;
}