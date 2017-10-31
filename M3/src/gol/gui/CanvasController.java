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
    boolean issamecopy = false;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
   
   /** 
    public void processCanvasMousePress(int x, int y) {
        //System.out.println("LOCATIOND DRAGGED  " + x + "   " + y);
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

                // RESET all btns
                //workspace.selectionToolButton.setDisable(false);
                //workspace.removeButton.setDisable(false);
                //workspace.rectButton.setDisable(false);
                //workspace.ellipseButton.setDisable(false);
                workspace.imageButton.setDisable(false);
                workspace.textButton.setDisable(false);

                workspace.italicButton.setDisable(false);
                workspace.boldButton.setDisable(false);

                workspace.comboBox.setDisable(false);
                workspace.comboBox2.setDisable(false);

                workspace.backgroundColorPicker.setDisable(false);
                workspace.fillColorPicker.setDisable(false);
                workspace.outlineColorPicker.setDisable(false);
                workspace.outlineThicknessSlider.setDisable(false);

                if (shape.getUserData() != null) { // TO ADD

                    if (shape.getUserData().equals("RECT") || (shape.getUserData().equals("IMAGE"))) {
                        workspace.italicButton.setDisable(true);
                        workspace.boldButton.setDisable(true);
                        workspace.comboBox.setDisable(true);
                        workspace.comboBox2.setDisable(true);

                        DraggableRectangle r = (DraggableRectangle) shape;
                        r.setStartDifX(x);
                        r.setStartDifY(y);

                        r.setInitialXY(r.getX(), r.getY());

                        // System.out.println(r.getWidth() + " rect width " + r.getHeight() + " rect hegith");
                        if ((shape.getUserData().equals("IMAGE"))) {
                            workspace.fillColorPicker.setDisable(true);
                            workspace.outlineColorPicker.setDisable(true);
                            workspace.outlineThicknessSlider.setDisable(true);
                        }

                    } else if (shape.getUserData().equals("ELLIP")) {
                        workspace.italicButton.setDisable(true);
                        workspace.boldButton.setDisable(true);
                        workspace.comboBox.setDisable(true);
                        workspace.comboBox2.setDisable(true);

                        DraggableEllipse r = (DraggableEllipse) shape;

                        r.setStartDifX(x);
                        r.setStartDifY(y);
                        r.setstartCenterX(r.getCenterX());
                        r.setstartCenterY(r.getCenterY());

                        r.setInitialXY(r.getX(), r.getY());

                        //System.out.println(r.getWidth() + " ellip width " + r.getHeight() + " ellip hegith");
                    } else if (shape.getUserData().equals("TEXT")) {
                        
                        DraggableText text = (DraggableText) shape;
                        text.setStartDifX(x);
                        text.setStartDifY(y);

                        text.setInitialXY(text.getX(), text.getY());

                        // System.out.println(text.getWidth() + " text width " + text.getHeight() + " text height");
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

                    workspace.copyButton.setOnAction(e -> {
                        copy = dataManager.copyShape(shape);
                        workspace.pasteButton.setDisable(false);
                    });

                    workspace.cutButton.setOnAction(e -> {
                        copy = dataManager.cutShape(shape); // removed
                        workspace.pasteButton.setDisable(false);
                    });

                    workspace.pasteButton.setOnAction(e -> {

                        if (copy != null) {

                            for (int i = 0; i < dataManager.getShapes().size(); i++) {
                                Shape s = (Shape) dataManager.getShapes().get(i);

                                if (s.getUserData().equals(copy.getUserData())) {
                                    if (s.getFill().equals(copy.getFill())) {
                                        if (s.getStroke().equals(copy.getStroke())) {
                                            if (s.getStrokeWidth() == copy.getStrokeWidth()) {
                                                if (copy.getUserData().equals("TEXT")) { //if (s.getUserData().equals("TEXT")) {
                                                    DraggableText text = (DraggableText) copy;
                                                    DraggableText text2 = (DraggableText) s;
                                                    if (text.getFont().getName().equals(text2.getFont().getName())) {
                                                        if (text.getFont().getSize() == text2.getFont().getSize()) {
                                                            issamecopy = true;
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    issamecopy = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                System.out.println("NO");
                                issamecopy = false;
                            }
                            if (issamecopy) {
                                copy = dataManager.copyShape(copy);
                                dataManager.pasteShape(copy);
                                System.out.println("IS SAME COPY");
                            } else {
                                dataManager.pasteShape(copy);
                            }

                        } else {
                            System.out.println("COPY IS NULL");
                        }

                    });
                }

            } else { // shape null

                workspace.italicButton.setDisable(true);
                workspace.boldButton.setDisable(true);
                workspace.comboBox.setDisable(true);
                workspace.comboBox2.setDisable(true);

                workspace.fillColorPicker.setDisable(true);
                workspace.outlineColorPicker.setDisable(true);
                workspace.outlineThicknessSlider.setDisable(true);

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
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     
    public void processCanvasMouseDragged(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();

        if (dataManager.isInState(SIZING_SHAPE)) {

            Draggable newDraggableShape = (Draggable) dataManager.getNewShape();
            if (x > 0 && x < 1020
                    && y > 0 && y < 710) {
                newDraggableShape.size(x, y);
            }
        } else if (dataManager.isInState(DRAGGING_SHAPE)) {

            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
            System.out.println();

            // TO ADD
            if ((selectedDraggableShape.getX() > 0)
                    && (selectedDraggableShape.getX() + selectedDraggableShape.getWidth() < 1020)
                    && (selectedDraggableShape.getY() > 0)
                    && (selectedDraggableShape.getY() + selectedDraggableShape.getHeight() < 710)) {
                selectedDraggableShape.drag(x, y);

            } else {
                if (selectedDraggableShape.getShapeType().equals("ELLIPSE")) {
                    DraggableEllipse E = (DraggableEllipse) dataManager.getSelectedShape();

                    double Xaxis = E.getCenterX();
                    double Yaxis = E.getCenterY();

                    if ((E.getX() <= 0)) { // if negative
                        Xaxis = Xaxis + 10;
                    }
                    if (E.getY() <= 0) {
                        Yaxis = Yaxis + 10;
                    }
                    if (E.getY() + E.getHeight() >= 710) {
                        Yaxis = Yaxis - 10;
                    }
                    if (E.getX() + E.getWidth() >= 1020) {
                        Xaxis = Xaxis - 10;
                    }

                    E.setXY2(Xaxis, Yaxis); // set center                 

                } else { // Rect, Text

                    if ((selectedDraggableShape.getX() > 0)
                            && (selectedDraggableShape.getX() + selectedDraggableShape.getWidth() < 1020)
                            && (selectedDraggableShape.getY() > 0)
                            && (selectedDraggableShape.getY() + selectedDraggableShape.getHeight() < 710)) {

                        selectedDraggableShape.drag(x, y);

                    } else {

                        //System.out.println("noppppeeee-----");
                        double Xaxis = selectedDraggableShape.getX();
                        double Yaxis = selectedDraggableShape.getY();

                        if ((selectedDraggableShape.getX() <= 0)) { // if negative
                            Xaxis = selectedDraggableShape.getX() + 10;
                        }
                        if (selectedDraggableShape.getY() <= 0) {
                            Yaxis = selectedDraggableShape.getY() + 10;
                        }
                        if (selectedDraggableShape.getY() + selectedDraggableShape.getHeight() >= 710) {
                            Yaxis = selectedDraggableShape.getY() - 10;
                        }
                        if (selectedDraggableShape.getX() + selectedDraggableShape.getWidth() >= 1020) {
                            Xaxis = selectedDraggableShape.getX() - 10;
                        }
                        selectedDraggableShape.setXY(Xaxis, Yaxis);
                    }
                }
                app.getGUI().updateToolbarControls(false);
            }
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call
     * canvas, but is actually a Pane.
     
    public void processCanvasMouseRelease(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();

        Shape selectedShape = dataManager.getSelectedShape();
        if (dataManager.isInState(SIZING_SHAPE)) {
            dataManager.selectSizedShape();
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(golState.DRAGGING_SHAPE)) {

            Draggable d = (Draggable) selectedShape;

            if (d.getInitialX() != x || d.getInitialY() != y) {
                dataManager.dragShape(shape, d.getInitialX(), d.getInitialY());
                d.setInitialXY(d.getX(), d.getY());
            }

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
                            dataManager.changeTextBox(shape, newS);
                        }
                    }
                }
            }
        }
    }

    public void setCopy(Shape copy) { // allows access from other methods
        this.copy = copy;
    }

    public Shape getCopy() { // allows access from other methods
        return copy;
    }
*/
}
