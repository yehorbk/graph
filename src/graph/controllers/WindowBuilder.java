package graph.controllers;

import graph.models.GraphCanvas;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import graph.models.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class WindowBuilder {

    private final int width = 1080;
    private final int height = 720;
    private final double canvasArea = 0.9;
    private final double menuArea = 0.1;

    private Stage stage;
    private Scene scene;
    private HBox root;
    private GraphCanvas graphCanvas;
    private Menu menu;
    
    private GraphController graphController;

    public WindowBuilder(Stage stage, String title) {
        this.stage = stage;
        this.stage.setTitle(title);
    }

    public void init(int identifier, int count) {
        this.root = new HBox();
        this.scene = new Scene(this.root, this.width, this.height);
        this.stage.setScene(this.scene);
        this.graphCanvas = new GraphCanvas(this.width * this.canvasArea, this.height);
        this.graphController = new GraphController(identifier, count, graphCanvas);
        this.menu = new Menu();
        setMenuEvents();
        this.drawScene();
    }

    public void setMenuEvents() {
        this.menu.bindSelectLabChoiceBoxEvent(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                graphController.loadLab(newValue.intValue());
            }
        });
        this.menu.bindShowSimpleGraphButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.drawSimpleBindings();
            }
        });
        this.menu.bindShowDirectedGraphButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.drawDirectedBindings();
            }
        });
        this.menu.bindShowGraphSpecs(new EventHandler() {
            @Override
            public void handle(Event event) {
                showGraphSpecsDialog();
            }
        });
        this.menu.bindShowCondensationGraphButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.drawCondensationBindings();
            }
        });
        this.menu.bindStartBFSButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.startBFSAlgorithm();
            }
        });
        this.menu.bindShowBFSTreeButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.drawBFSTree();
            }
        });
        this.menu.bindStartPrimeButtonEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.startPrimeAlgorithm();
            }
        });
        this.menu.bindShowSpanningTreeEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.drawSpanningTree();
            }
        });
        this.menu.bindStartDijkstraEvent(new EventHandler() {
            @Override
            public void handle(Event event) {
                graphController.startDijkstraAlgorithm();
            }
        });
    }

    private void drawScene() {
        HBox.setHgrow(this.graphCanvas, Priority.ALWAYS);
        HBox.setHgrow(this.menu, Priority.ALWAYS);
        this.menu.setMaxSize(this.width * this.menuArea, this.height);
        this.root.getChildren().addAll(this.graphCanvas, this.menu);
    }

    private void showGraphSpecsDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Graph Specs");
        alert.setHeaderText("Характеристики графа");
        TextArea textArea = new TextArea(this.graphController.generateGraphSpecs());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane alertContent = new GridPane();
        alertContent.setMaxWidth(Double.MAX_VALUE);
        alertContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(alertContent);
        alert.showAndWait();
    }

}
