package gol.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import static gol.golLanguageProperty.ELLIPSE_ICON;
import static gol.golLanguageProperty.ELLIPSE_TOOLTIP;
import static gol.golLanguageProperty.MOVE_TO_BACK_ICON;
import static gol.golLanguageProperty.MOVE_TO_BACK_TOOLTIP;
import static gol.golLanguageProperty.MOVE_TO_FRONT_ICON;
import static gol.golLanguageProperty.MOVE_TO_FRONT_TOOLTIP;
import static gol.golLanguageProperty.RECTANGLE_ICON;
import static gol.golLanguageProperty.RECTANGLE_TOOLTIP;
import static gol.golLanguageProperty.REMOVE_ICON;
import static gol.golLanguageProperty.REMOVE_TOOLTIP;
import static gol.golLanguageProperty.SELECTION_TOOL_ICON;
import static gol.golLanguageProperty.SELECTION_TOOL_TOOLTIP;
import static gol.golLanguageProperty.SNAPSHOT_ICON;
import static gol.golLanguageProperty.SNAPSHOT_TOOLTIP;
import gol.data.golData;
import static gol.data.golData.BLACK_HEX;
import static gol.data.golData.WHITE_HEX;
import gol.data.golState;
import djf.ui.AppYesNoCancelDialogSingleton;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppGUI;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.APP_LOGO;
import static djf.settings.AppPropertyType.APP_TITLE;
import static djf.settings.AppPropertyType.BACKGROUNDCOLOR;
import static djf.settings.AppPropertyType.OUTLINETHICKNESS;
import static djf.settings.AppPropertyType.EXIT_ICON;
import static djf.settings.AppPropertyType.EXIT_TOOLTIP;
import static djf.settings.AppPropertyType.FILLCOLOR;
import static djf.settings.AppPropertyType.LOAD_ICON;
import static djf.settings.AppPropertyType.LOAD_TOOLTIP;
import static djf.settings.AppPropertyType.NEW_ICON;
import static djf.settings.AppPropertyType.NEW_TOOLTIP;
import static djf.settings.AppPropertyType.OUTLINECOLOR;
import static djf.settings.AppPropertyType.PREF_HEIGHT;
import static djf.settings.AppPropertyType.PREF_WIDTH;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.SAVE_ICON;
import static djf.settings.AppPropertyType.SAVE_TOOLTIP;
import static djf.settings.AppPropertyType.START_MAXIMIZED;
import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static gol.css.golStyle.*;
import gol.golLanguageProperty;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import properties_manager.PropertiesManager;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class golWorkspace extends AppWorkspaceComponent {

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;

    // HAS ALL THE CONTROLS FOR EDITING
    VBox editToolbar;

    // FIRST ROW
    HBox row1Box;
    Button selectionToolButton;
    Button removeButton;
    Button rectButton;
    Button ellipseButton;

    // SECOND ROW
    HBox row2Box;
    Button moveToBackButton;
    Button moveToFrontButton;

    // THIRD ROW
    VBox row3Box;
    Label backgroundColorLabel;
    ColorPicker backgroundColorPicker;

    // FORTH ROW
    VBox row4Box;
    Label fillColorLabel;
    ColorPicker fillColorPicker;

    // FIFTH ROW
    VBox row5Box;
    Label outlineColorLabel;
    ColorPicker outlineColorPicker;

    // SIXTH ROW
    VBox row6Box;
    Label outlineThicknessLabel;
    Slider outlineThicknessSlider;

    // SEVENTH ROW
    HBox row7Box;
    Button snapshotButton;

    // THIS IS WHERE WE'LL RENDER OUR DRAWING, NOTE THAT WE
    // CALL THIS A CANVAS, BUT IT'S REALLY JUST A Pane
    Pane canvas;

    // HERE ARE THE CONTROLLERS
    CanvasController canvasController;
    LogoEditController logoEditController;

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

    // FOR DISPLAYING DEBUG STUFF
    Text debugText;

    // FILE TOOLBAR BUTTONS
    protected Button newButton;
    protected Button loadButton;
    protected Button saveButton;
    protected Button exitButton;

    // THIS DIALOG IS USED FOR GIVING FEEDBACK TO THE USER
    //protected AppYesNoCancelDialogSingleton yesNoCancelDialog;
    // THIS TITLE WILL GO IN THE TITLE BAR
    protected String appTitle, answer0, answer;

    private File file; //=new File("E:\\LANGUAGE.json"); 
    private String JSON_CHOICE;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public golWorkspace(AppTemplate initApp) {

        // KEEP THIS FOR LATER
        app = initApp;

        // KEEP THE GUI FOR LATER
        gui = app.getGUI();

        languageSelection();
        // LAYOUT THE APP
        initLayout();

        // HOOK UP THE CONTROLLERS
        initControllers();

        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
    }

    /**
     * Note that this is for displaying text during development.
     */
    public void setDebugText(String text) {
        debugText.setText(text);
    }

    // ACCESSOR METHODS FOR COMPONENTS THAT EVENT HANDLERS
    // MAY NEED TO UPDATE OR ACCESS DATA FROM
    public ColorPicker getFillColorPicker() {
        return fillColorPicker;
    }

    public ColorPicker getOutlineColorPicker() {
        return outlineColorPicker;
    }

    public ColorPicker getBackgroundColorPicker() {
        return backgroundColorPicker;
    }

    public Slider getOutlineThicknessSlider() {
        return outlineThicknessSlider;
    }

    public Pane getCanvas() {
        return canvas;
    }

    // HELPER SETUP METHOD
    private void initLayout() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
        editToolbar = new VBox();

        // ROW 1
        row1Box = new HBox();
        selectionToolButton = gui.initChildButton(row1Box, SELECTION_TOOL_ICON.toString(), SELECTION_TOOL_TOOLTIP.toString(), true);
        removeButton = gui.initChildButton(row1Box, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), true);
        rectButton = gui.initChildButton(row1Box, RECTANGLE_ICON.toString(), RECTANGLE_TOOLTIP.toString(), false);
        ellipseButton = gui.initChildButton(row1Box, ELLIPSE_ICON.toString(), ELLIPSE_TOOLTIP.toString(), false);

        // ROW 2
        row2Box = new HBox();
        moveToBackButton = gui.initChildButton(row2Box, MOVE_TO_BACK_ICON.toString(), MOVE_TO_BACK_TOOLTIP.toString(), true);
        moveToFrontButton = gui.initChildButton(row2Box, MOVE_TO_FRONT_ICON.toString(), MOVE_TO_FRONT_TOOLTIP.toString(), true);

        // ROW 3
        row3Box = new VBox();
        backgroundColorLabel = new Label(props.getProperty(BACKGROUNDCOLOR));
        backgroundColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
        row3Box.getChildren().add(backgroundColorLabel);
        row3Box.getChildren().add(backgroundColorPicker);

        // ROW 4
        row4Box = new VBox();
        fillColorLabel = new Label(props.getProperty(FILLCOLOR));
        fillColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
        row4Box.getChildren().add(fillColorLabel);
        row4Box.getChildren().add(fillColorPicker);

        // ROW 5
        row5Box = new VBox();
        outlineColorLabel = new Label(props.getProperty(OUTLINECOLOR));
        outlineColorPicker = new ColorPicker(Color.valueOf(BLACK_HEX));
        row5Box.getChildren().add(outlineColorLabel);
        row5Box.getChildren().add(outlineColorPicker);

        // ROW 6
        row6Box = new VBox();
        outlineThicknessLabel = new Label(props.getProperty(OUTLINETHICKNESS));
        outlineThicknessSlider = new Slider(0, 10, 1);
        row6Box.getChildren().add(outlineThicknessLabel);
        row6Box.getChildren().add(outlineThicknessSlider);

        // ROW 7
        row7Box = new HBox();
        snapshotButton = gui.initChildButton(row7Box, SNAPSHOT_ICON.toString(), SNAPSHOT_TOOLTIP.toString(), false);

        // NOW ORGANIZE THE EDIT TOOLBAR
        editToolbar.getChildren().add(row1Box);
        editToolbar.getChildren().add(row2Box);
        editToolbar.getChildren().add(row3Box);
        editToolbar.getChildren().add(row4Box);
        editToolbar.getChildren().add(row5Box);
        editToolbar.getChildren().add(row6Box);
        editToolbar.getChildren().add(row7Box);

        // WE'LL RENDER OUR STUFF HERE IN THE CANVAS
        canvas = new Pane();
        debugText = new Text();
        canvas.getChildren().add(debugText);
        debugText.setX(100);
        debugText.setY(100);

        // AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
        golData data = (golData) app.getDataComponent();
        data.setShapes(canvas.getChildren());

        // AND NOW SETUP THE WORKSPACE
        workspace = new BorderPane();
        ((BorderPane) workspace).setLeft(editToolbar);
        ((BorderPane) workspace).setCenter(canvas);
    }

    // HELPER SETUP METHOD
    private void initControllers() {
        // MAKE THE EDIT CONTROLLER
        logoEditController = new LogoEditController(app);

        // NOW CONNECT THE BUTTONS TO THEIR HANDLERS
        selectionToolButton.setOnAction(e -> {
            logoEditController.processSelectSelectionTool();
        });
        removeButton.setOnAction(e -> {
            logoEditController.processRemoveSelectedShape();
        });
        rectButton.setOnAction(e -> {
            logoEditController.processSelectRectangleToDraw();
        });
        ellipseButton.setOnAction(e -> {
            logoEditController.processSelectEllipseToDraw();
        });

        moveToBackButton.setOnAction(e -> {
            logoEditController.processMoveSelectedShapeToBack();
        });
        moveToFrontButton.setOnAction(e -> {
            logoEditController.processMoveSelectedShapeToFront();
        });

        backgroundColorPicker.setOnAction(e -> {
            logoEditController.processSelectBackgroundColor();
        });
        fillColorPicker.setOnAction(e -> {
            logoEditController.processSelectFillColor();
        });
        outlineColorPicker.setOnAction(e -> {
            logoEditController.processSelectOutlineColor();
        });
        outlineThicknessSlider.valueProperty().addListener(e -> {
            logoEditController.processSelectOutlineThickness();
        });
        snapshotButton.setOnAction(e -> {
            logoEditController.processSnapshot();
        });

        // MAKE THE CANVAS CONTROLLER	
        canvasController = new CanvasController(app);
        canvas.setOnMousePressed(e -> {
            canvasController.processCanvasMousePress((int) e.getX(), (int) e.getY());
        });
        canvas.setOnMouseReleased(e -> {
            canvasController.processCanvasMouseRelease((int) e.getX(), (int) e.getY());
        });
        canvas.setOnMouseDragged(e -> {
            canvasController.processCanvasMouseDragged((int) e.getX(), (int) e.getY());
        });
    }

    // HELPER METHOD
    public void loadSelectedShapeSettings(Shape shape) {
        if (shape != null) {
            Color fillColor = (Color) shape.getFill();
            Color strokeColor = (Color) shape.getStroke();
            double lineThickness = shape.getStrokeWidth();
            fillColorPicker.setValue(fillColor);
            outlineColorPicker.setValue(strokeColor);
            outlineThicknessSlider.setValue(lineThickness);
        }
    }

    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {
        // NOTE THAT EACH CLASS SHOULD CORRESPOND TO
        // A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
        // CSS FILE
        canvas.getStyleClass().add(CLASS_RENDER_CANVAS);

        // COLOR PICKER STYLE
        fillColorPicker.getStyleClass().add(CLASS_BUTTON);
        outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
        backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);

        editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
        row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        backgroundColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);

        row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fillColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        outlineColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        outlineThicknessLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        row7Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
    }

    /**
     * This function reloads all the controls for editing logos the workspace.
     */
    //@Override
    public void reloadWorkspace(AppDataComponent data) {
        golData dataManager = (golData) data;
        if (dataManager.isInState(golState.STARTING_RECTANGLE)) {
            selectionToolButton.setDisable(false);
            removeButton.setDisable(true);
            rectButton.setDisable(true);
            ellipseButton.setDisable(false);
        } else if (dataManager.isInState(golState.STARTING_ELLIPSE)) {
            selectionToolButton.setDisable(false);
            removeButton.setDisable(true);
            rectButton.setDisable(false);
            ellipseButton.setDisable(true);
        } else if (dataManager.isInState(golState.SELECTING_SHAPE)
                || dataManager.isInState(golState.DRAGGING_SHAPE)
                || dataManager.isInState(golState.DRAGGING_NOTHING)) {
            boolean shapeIsNotSelected = dataManager.getSelectedShape() == null;
            selectionToolButton.setDisable(true);
            removeButton.setDisable(shapeIsNotSelected);
            rectButton.setDisable(false);
            ellipseButton.setDisable(false);
            moveToFrontButton.setDisable(shapeIsNotSelected);
            moveToBackButton.setDisable(shapeIsNotSelected);
        }

        removeButton.setDisable(dataManager.getSelectedShape() == null);
        backgroundColorPicker.setValue(dataManager.getBackgroundColor());

    }

    /**
     * If LangSelection.txt file exists, read it and updates the UI in that
     * Language Otherwise, create LangSelection.txt and save selected Language
     */
    public void languageSelection() {

        String line = null;
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        try {
            //String verify, putData;
            File file = new File("LangSelection.txt");
            file.createNewFile();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            if ((line = br.readLine()) != null) {       // SUPER IMPORTANT TO USE LINE AND NOT br.readLine()

                // updates
                if (line.equals("French")) {
                    boolean success = app.loadProperties("app_properties_FR.xml");

                    if (success) {

                        String appTitle = props.getProperty(APP_TITLE);
                        initTopToolbar2(app);
                    }

                } else {
                    // ENG by default
                }

            } else { // ASKS sleection

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                answer0 = askLanguageSelection();
                answer = answer0 + "\n";

                bw.write(answer);
                bw.flush();
                bw.close();

                if (answer0.equals("French")) {
                    boolean success = app.loadProperties("app_properties_FR.xml");

                    if (success) {

                        String appTitle = props.getProperty(APP_TITLE);
                        initTopToolbar2(app);
                    }
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks user to select a language
     *
     * @return language
     */
    public String askLanguageSelection() {
        List<String> choices = new ArrayList<>();
        choices.add("English");
        choices.add("French");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("English", choices);
        dialog.setTitle("Language Setting");
        dialog.setHeaderText("Please select your language");

        Optional<String> result = dialog.showAndWait();
        return result.get();
    }

    /**
     * Initializes TopFileTolbar in new Language
     *
     * @param app
     */
    private void initTopToolbar2(AppTemplate app) {
        //fileToolbar = new FlowPane();
        //fileToolbar = app.getGUI().getFileToolbar();

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        Button newButton = (Button) app.getGUI().getFileToolbar().getChildren().get(0);
        Tooltip buttonTooltip = new Tooltip(props.getProperty(NEW_TOOLTIP));
        newButton.setTooltip(buttonTooltip);

        Button loadButton = (Button) app.getGUI().getFileToolbar().getChildren().get(1);
        buttonTooltip = new Tooltip(props.getProperty(LOAD_TOOLTIP));
        loadButton.setTooltip(buttonTooltip);

        Button saveButton = (Button) app.getGUI().getFileToolbar().getChildren().get(2);
        buttonTooltip = new Tooltip(props.getProperty(SAVE_TOOLTIP));
        saveButton.setTooltip(buttonTooltip);

        Button exitButton = (Button) app.getGUI().getFileToolbar().getChildren().get(3);
        buttonTooltip = new Tooltip(props.getProperty(EXIT_TOOLTIP));
        exitButton.setTooltip(buttonTooltip);

    }

    // public void createLANGFile();
    @Override
    public void resetWorkspace() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
