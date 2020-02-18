package models;

import javafx.scene.layout.VBox;

public class Menu extends VBox {

    public Menu() {
        this.applyStyles();
    }
    
    private void applyStyles() {
        this.setStyle("-fx-background-color: rgba(217, 217, 217, 0.5);");
    }
    
}
