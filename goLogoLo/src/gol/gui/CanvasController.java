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
import static java.awt.SystemColor.text;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import java.awt.event.*;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class CanvasController {

    AppTemplate app;
    Shape shape = null;
    Shape copy = null;

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
            shape = dataManager.selectTopShape(x, y);

            Scene scene = app.getGUI().getPrimaryScene();

            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(golState.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);

                if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {
                    //DraggableText text2 = (DraggableText) shape;

                    golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

                    workspace.boldButton.setOnAction(e -> {
                        dataManager.getBolded(shape);
                    });

                    workspace.italicButton.setOnAction(e -> {
                        dataManager.getItalicized(shape);
                    });

                    workspace.comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                        @Override
                        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                            golData dataManager = (golData) app.getDataComponent();
                            Shape shape = dataManager.getSelectedShape();
                            if (shape != null) {
                                if (shape.getUserData().equals("TEXT")) {
                                    DraggableText text = (DraggableText) shape;
                                    if (!workspace.comboBox.getSelectionModel().selectedItemProperty().getValue().equals(text.getFont().getFamily())) {
                                        //System.out.println(workspace.comboBox.getSelectionModel().selectedItemProperty().getValue() + " selected in combo");
                                        dataManager.changefont(shape, (String) workspace.comboBox.getSelectionModel().selectedItemProperty().getValue());
                                    }
                                }
                            } else {
                                System.out.println(" No selected shape");
                            }
                        }
                    });

                    workspace.comboBox2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                        @Override
                        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                            golData dataManager = (golData) app.getDataComponent();
                            Shape shape = dataManager.getSelectedShape();
                            if (shape != null) {
                                if (shape.getUserData().equals("TEXT")) {
                                    DraggableText text = (DraggableText) shape;
                                     //dataManager.cloneRect(shape);
                                    if (!workspace.comboBox2.getSelectionModel().selectedItemProperty().getValue().equals((Double) text.getFont().getSize())) {
                                        dataManager.changesize(shape, (double) workspace.comboBox2.getSelectionModel().selectedItemProperty().getValue());
                                    }
                                }
                            } else {
                                System.out.println(" No selected shape");
                            }
                        }
                    });

                }

            } else { // shape null
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(golState.STARTING_RECTANGLE)) {
            dataManager.startNewRectangle(x, y);
        } else if (dataManager.isInState(golState.STARTING_ELLIPSE)) {
            dataManager.startNewEllipse(x, y);
        } else if (dataManager.isInState(golState.COPY_SHAPE)){
            copy = dataManager.cloneShape(shape);
            dataManager.setState(DRAGGING_NOTHING);                                 // DEFAULt
        } else if (dataManager.isInState(golState.PASTE_SHAPE)){
          
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

    /**
     * Respond to double clicked mouse
     *
     * @param x
     * @param y
     */
    public void processModifyText(int x, int y) { //, String s) {
        golData dataManager = (golData) app.getDataComponent();
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Shape shape = dataManager.selectTopShape(x, y);

        if (shape != null) {
            if (shape.getUserData() != null) {
                if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {
                    DraggableText text = (DraggableText) shape;
                    if (text.getText() != null) {
                        String newS = workspace.promptToText(text.getText()); // original text passed as parameter
                        if (newS != null) {
                            dataManager.changeTextBox(newS);
                        }
                    }
                }
            }
        }
    }

}
