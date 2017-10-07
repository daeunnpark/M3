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
import gol.data.DraggableEllipse;
import gol.data.DraggableRectangle;
import gol.data.DraggableText;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
            golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(golState.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);
                
                if (shape.getUserData() != null) { // TO ADD
                    
                    if (shape.getUserData().equals("RECT") || (shape.getUserData().equals("IMAGE"))) {
                        DraggableRectangle r = (DraggableRectangle) shape;
                        r.setStartDifX(x);
                        r.setStartDifY(y);
                    } else if (shape.getUserData().equals("ELLIP")) {
                        DraggableEllipse r = (DraggableEllipse) shape;
                        
                        r.setStartDifX(x);
                        r.setStartDifY(y);
                        r.setstartCenterX(r.getCenterX());
                        r.setstartCenterY(r.getCenterY());
                        
                    } else if (shape.getUserData().equals("TEXT")) {
                        DraggableText text = (DraggableText) shape;
                        text.setStartDifX(x);
                        text.setStartDifY(y);
                        
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
                        
                        //workspace.fillColorLabel.setDisable(true);
                        
                    }
                    
                    workspace.copyButton.setOnAction(e -> {
                        //System.out.println("copy btn");
                        copy = dataManager.cloneShape(shape);
                        // System.out.println(copy.toString() + "copied " );
                        
                    });
                    
                    workspace.cutButton.setOnAction(e -> {
                        // System.out.println("cut btn");
                        copy = dataManager.cloneShape(shape);
                        //System.out.println( dataManager.getSelectedShape().toString() + " selected shape removed ");
                        dataManager.removeSelectedShape();
                    });
                    
                    workspace.pasteButton.setOnAction(e -> {
                        // System.out.println("paste btn");
                        if (copy != null) {
                            dataManager.pasteShape(copy);
                        }
                        //else {
                        //System.out.println(" copy null");
                        //}
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
            } else if (dataManager.isInState(golState.COPY_SHAPE)) {
                // copy = dataManager.cloneShape(shape);
                dataManager.setState(DRAGGING_NOTHING);                                 // DEFAULt
            }            
            golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
            workspace.reloadWorkspace(dataManager);
        }
        /**
         * Respond to mouse dragging on the rendering surface, which we call
         * canvas, but is actually a Pane.
         */
    public void processCanvasMouseDragged(int x, int y) { //, double offsetX, double offsetY) {
        golData dataManager = (golData) app.getDataComponent();
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
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
    public void processModifyText(int x, int y) {
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
