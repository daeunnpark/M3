package gol.gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import gol.data.golData;
import gol.data.Draggable;
import gol.data.golState;
import static gol.data.golState.DRAGGING_NOTHING;
import static gol.data.golState.DRAGGING_SHAPE;
import static gol.data.golState.SELECTING_SHAPE;
import static gol.data.golState.SIZING_SHAPE;
import djf.AppTemplate;
import gol.data.DraggableText;
import static gol.data.golState.MODIFYING_TEXT;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class CanvasController {

    AppTemplate app;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMousePress(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();

        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();

            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(golState.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);

                if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {

                    golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

                    workspace.boldButton.setOnAction(e -> {

                        System.out.println(workspace.boldButton.isSelected() + "selected");
                        dataManager.getBolded(shape, workspace.boldButton.isSelected());

                        System.out.println("BUTTON BOLD2");

                    });

                    workspace.italicButton.setOnAction(e -> {

                        dataManager.getItalicized(shape, workspace.italicButton.isSelected());

                    });
                    workspace.comboBox.setOnAction(e -> {
                        if (workspace.comboBox.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("HHHH");
                            dataManager.changefont(shape, workspace.comboBox.getSelectionModel().getSelectedItem().toString());
                        }
                    });
                    
                    
                     workspace.comboBox2.setOnAction(e -> {
                        if (workspace.comboBox2.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("HHHH222");
                            
                            dataManager.changesize(shape, Integer.parseInt(workspace.comboBox2.getSelectionModel().getSelectedItem().toString()));
                        }
                    });
                }

            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(golState.STARTING_RECTANGLE)) {
            dataManager.startNewRectangle(x, y);
        } else if (dataManager.isInState(golState.STARTING_ELLIPSE)) {
            dataManager.startNewEllipse(x, y);
        }

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseDragged(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
            Draggable newDraggableShape = (Draggable) dataManager.getNewShape();
            newDraggableShape.size(x, y);
        } else if (dataManager.isInState(DRAGGING_SHAPE)) {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call
     * canvas, but is actually a Pane.
     */
    public void processCanvasMouseRelease(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();

        if (dataManager.isInState(SIZING_SHAPE)) {
            dataManager.selectSizedShape();
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(golState.DRAGGING_SHAPE)) {
            dataManager.setState(SELECTING_SHAPE);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(golState.DRAGGING_NOTHING)) {
            dataManager.setState(SELECTING_SHAPE);
        }

    }

    public void processModifyText(int x, int y) { //, String s) {
        golData dataManager = (golData) app.getDataComponent();
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Shape shape = dataManager.selectTopShape(x, y);

        //System.out.println("processmodifying in canvas");
        if (shape != null) {
            if (shape.getUserData() != null) {
                if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {
                    String newS = workspace.promptToText();

                    //System.out.println(newS + "HERE ----------------");
                    if (newS != null) {
                        dataManager.changeTextBox(newS);
                    }

                    // System.out.println("processmodifying in canvas not null");
                }
            }
        }
    }

    public void processBoldStyle(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Shape shape = dataManager.selectTopShape(x, y);

        //System.out.println("processmodifying in canvas");
        if (shape != null) {
            if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {
                //dataManager.getBolded(shape);
            }
        }
    }

}
