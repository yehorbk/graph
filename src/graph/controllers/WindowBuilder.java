package graph.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import models.Menu;

public class WindowBuilder {

    private Stage stage;
    private final int width = 1080;
    private final int height = 720;
    private final double canvasArea = 0.9;
    private final double menuArea = 0.1;
    private Canvas canvas;
    private Menu menu;
    
    public WindowBuilder(Stage stage, String title) {
        this.stage = stage;
        this.stage.setTitle(title);
    }
    
    public void init() {
        HBox root = new HBox();
        Scene scene = new Scene(root, this.width, this.height);
        this.stage.setScene(scene);
        canvas = new Canvas();
        menu = new Menu();
        drawScene(root);
    }
    
    private void drawScene(HBox root) {
        HBox.setHgrow(canvas, Priority.ALWAYS);
        HBox.setHgrow(menu, Priority.ALWAYS);
        canvas.setWidth(this.width * this.canvasArea);
        canvas.setHeight(this.height);
        menu.setMaxSize(this.width * this.menuArea, this.height);
        root.getChildren().addAll(canvas, menu);
    }
    
}
