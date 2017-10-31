package gol.gui;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import gol.data.golData;
import gol.data.golState;
import djf.AppTemplate;
import javafx.scene.image.Image;

/**
 * This class responds to interactions with other UI logo editing controls.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class LogoEditController {

    AppTemplate app;
    golData dataManager;

    public LogoEditController(AppTemplate initApp) {
        app = initApp;
        dataManager = (golData) app.getDataComponent();
    }

    /**
     * This method handles the response for selecting either the selection or
     * removal tool.
     */
    public void processSelectSelectionTool() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);

        // CHANGE THE STATE
        dataManager.setState(golState.SELECTING_SHAPE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * This method handles a user request to remove the selected shape.
     */
    public void processRemoveSelectedShape() {
        // REMOVE THE SELECTED SHAPE IF THERE IS ONE

        dataManager.removeShape(dataManager.getSelectedShape());
        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
        app.getGUI().updateToolbarControls(false);
    }

    /**
     * This method processes a user request to start drawing a rectangle.
     */
    public void processSelectRectangleToDraw() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(golState.STARTING_RECTANGLE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * This method provides a response to the user requesting to start drawing
     * an ellipse.
     */
    public void processSelectEllipseToDraw() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(golState.STARTING_ELLIPSE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * This method processes a user request to add a image to canvas
     *
     * @param image
     */
    public void processMakeImageasShape(Image image, String filepath) {
        // CHANGE THE CURSOR
        //Scene scene = app.getGUI().getPrimaryScene();
        //scene.setCursor(Cursor.CROSSHAIR);

        dataManager.startNewImage(filepath, image.getHeight(), image.getWidth(), image);

        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * This method processes a user request to move the selected shape down to
     * the back layer.
     */
    public void processMoveSelectedShapeToBack() {
        dataManager.moveSelectedShapeToBack(dataManager.getSelectedShape());
        app.getGUI().updateToolbarControls(false);
    }

    /**
     * This method processes a user request to move the selected shape up to the
     * front layer.
     */
    public void processMoveSelectedShapeToFront() {
        dataManager.moveSelectedShapeToFront(dataManager.getSelectedShape());
        app.getGUI().updateToolbarControls(false);
    }

    /**
     * This method processes a user request to select a fill color for a shape.
     */
    public void processSelectFillColor() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Color selectedColor = workspace.getFillColorPicker().getValue();
        if (selectedColor != null) {
            dataManager.setCurrentFillColor(dataManager.getSelectedShape(), selectedColor);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * This method processes a user request to select the outline color for a
     * shape.
     */
    public void processSelectOutlineColor() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Color selectedColor = workspace.getOutlineColorPicker().getValue();
        if (selectedColor != null) {
            dataManager.setCurrentOutlineColor(dataManager.getSelectedShape(), selectedColor);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * This method processes a user request to select the background color.
     */
    public void processSelectBackgroundColor() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Color selectedColor = workspace.getBackgroundColorPicker().getValue();
        if (selectedColor != null) {
            dataManager.setBackgroundColor(selectedColor);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * This method processes a user request to select the outline thickness for
     * shape drawing.
     */
    public void processSelectOutlineThickness() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        if (dataManager.getSelectedShape() != null) {
            if ((int) dataManager.getSelectedShape().getStrokeWidth()!= dataManager.getCurrentBorderWidth2() ) {
                int thickness = (int) workspace.getOutlineThicknessSlider().getValue();
                dataManager.setCurrentOutlineThickness(dataManager.getSelectedShape(), thickness);
            }
        }
        app.getGUI().updateToolbarControls(false);
    }

    /**
     * This method processes a user request to take a snapshot of the current
     * scene.
     */
    public void processSnapshot() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        File file = new File("Logo.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void processTextBox(String text) {
        dataManager.makeNewTextBox(text);
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    public void processCopyShapeTool() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);

        // CHANGE THE STATE
        // dataManager.setState(golState.COPY_SHAPE);
        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    public void processPasteShapeTool() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);

        // CHANGE THE STATE
        dataManager.setState(golState.PASTE_SHAPE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

}
