package gol.gui;

import java.io.IOException;
import javafx.scene.control.Button;
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
import static gol.golLanguageProperty.REMOVE_ELEMENT_TOOLTIP;
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
import static djf.settings.AppPropertyType.APP_INFO;
import static djf.settings.AppPropertyType.BACKGROUNDCOLOR;
import static djf.settings.AppPropertyType.COPY_ICON;
import static djf.settings.AppPropertyType.COPY_TOOLTIP;
import static djf.settings.AppPropertyType.CUT_ICON;
import static djf.settings.AppPropertyType.CUT_TOOLTIP;
import static djf.settings.AppPropertyType.EN;
import static djf.settings.AppPropertyType.EXIT_ICON;

import static djf.settings.AppPropertyType.OUTLINETHICKNESS;
import static djf.settings.AppPropertyType.EXIT_TOOLTIP;
import static djf.settings.AppPropertyType.FILLCOLOR;
import static djf.settings.AppPropertyType.FR;

import static djf.settings.AppPropertyType.INFO_ICON;
import static djf.settings.AppPropertyType.INFO_TOOLTIP;
import static djf.settings.AppPropertyType.LANG_ICON;
import static djf.settings.AppPropertyType.LANG_TEXT;
import static djf.settings.AppPropertyType.LANG_TITLE;

import static djf.settings.AppPropertyType.LANG_TOOLTIP;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.LOAD_TOOLTIP;
import static djf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static djf.settings.AppPropertyType.NEW_TOOLTIP;
import static djf.settings.AppPropertyType.OUTLINECOLOR;
import static djf.settings.AppPropertyType.PASTE_ICON;
import static djf.settings.AppPropertyType.PASTE_TOOLTIP;
import static djf.settings.AppPropertyType.REDO_ICON;
import static djf.settings.AppPropertyType.REDO_TOOLTIP;
import static djf.settings.AppPropertyType.SAVE_ICON;
import static djf.settings.AppPropertyType.SAVE_TOOLTIP;
import static djf.settings.AppPropertyType.UNDO_ICON;
import static djf.settings.AppPropertyType.UNDO_TOOLTIP;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import static gol.css.golStyle.*;
import gol.data.DraggableText;
import static gol.golLanguageProperty.ADDPICTURE_ICON;
import static gol.golLanguageProperty.ADDPICTURE_TOOLTIP;
import static gol.golLanguageProperty.ADDTEXT_ICON;
import static gol.golLanguageProperty.ADDTEXT_TOOLTIP;
import static gol.golLanguageProperty.BACKGROUND_TOOLTIP;
import static gol.golLanguageProperty.BOLD_ICON;
import static gol.golLanguageProperty.BOLD_TOOLTIP;
import static gol.golLanguageProperty.FILL_TOOLTIP;
import static gol.golLanguageProperty.ITALIC_ICON;
import static gol.golLanguageProperty.ITALIC_TOOLTIP;
import static gol.golLanguageProperty.OUTLINE_TOOLTIP;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

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

    // FILE TOOLBAR BUTTONS
    protected Button newButton;
    protected Button loadButton;
    protected Button saveButton;
    protected Button saveAsButton;
    private Button exportButton;

    ToggleButton undoButton;
    ToggleButton redoButton;

    private Button aboutButton;

    // ROW1
    private VBox row1Box;
    private HBox row1_1Box;
    private HBox row1_2Box;
    private HBox row1_3Box;

    private Label metroLinesLabel;
    private ComboBox lines;
    private ColorPicker lineColorpicker;
    private Button addLineButton;
    private Button removeLineButton;
    private Button addStationButton;
    private Button removeStationButton;
    private Button listStationButton;
    private Slider lineThickness;

    // ROW 2
    private VBox row2Box;
    private HBox row2_1Box;
    private HBox row2_2Box;
    private HBox row2_3Box;
    private Label metroStationsLabel;
    private ComboBox stations;
    private ColorPicker stationColorpicker;
    private Button addStationButton2;
    private Button removeStationButton2;
    private Button snapButton;
    private Button moveLabelButton;
    private Button rotateButton;
    private Slider stationRadius;

    // Row 3
    private HBox row3Box;
    private VBox row3_1Box;
    private VBox row3_2Box;
    private ComboBox direction1;
    private ComboBox direction2;
    private Button changeDirButton;

    // Row 4
    private VBox row4Box;
    private HBox row4_1Box;
    private HBox row4_2Box;

    private Label decorLabel;
    private ColorPicker decorColorpicker;
    private Button setImageBgdButton;
    private Button addImageButton;
    private Button addLabelButton;
    private Button RemoveElmtButton;

    // Row 5
    private VBox row5Box;
    private HBox row5_1Box;
    private HBox row5_2Box;

    private Label fontLabel;
    private ColorPicker fontColorpicker;
    private ToggleButton boldButton;
    private ToggleButton italicButton;
    private ComboBox fontsizecomboBox, fontcomboBox;

    // Row 6
    private VBox row6Box;
    private HBox row6_1Box;
    private HBox row6_2Box;

    private Label navigationLabel;
    private CheckBox showGrid;
    private Button zoomin;
    private Button zoomout;
    private Button increase;
    private Button decrease;

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

    boolean continueToOpen;

    //fileToolbar is from Framework
    FlowPane fileToolbar2; // cut copy paste
    FlowPane fileToolbar3; // undo redo    
    FlowPane fileToolbar4;// lang info

    // THIS DIALOG IS USED FOR GIVING FEEDBACK TO THE USER
    //protected AppYesNoCancelDialogSingleton yesNoCancelDialog;
    // THIS TITLE WILL GO IN THE TITLE BAR
    protected String appTitle, answer0, answer;

    // private File file; //=new File("E:\\LANGUAGE.json"); 
    //private String JSON_CHOICE;
    //ComboBox comboBox, comboBox2;
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

        // LAYOUT THE APP
        initLayout();

        // addFilebuttons();
        languageSelection();

        //initLayout();
        // HOOK UP THE CONTROLLERS
        initControllers();

        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();

    }

    public void undoredo() {

    }

    /**
     * Note that this is for displaying text during development.
     */
    public void setDebugText(String text) {
        debugText.setText(text);
    }

    // ACCESSOR METHODS FOR COMPONENTS THAT EVENT HANDLERS
    // MAY NEED TO UPDATE OR ACCESS DATA FROM
    public ColorPicker getLineColorPicker() {
        return lineColorpicker;
    }

    public ColorPicker getStationColorPicker() {
        return stationColorpicker;
    }

    public ColorPicker getDecorColorPicker() {
        return decorColorpicker;
    }

    public ColorPicker getFontColorPicker() {
        return fontColorpicker;
    }

    public Slider getLineThicknessSlider() {
        return lineThickness;
    }

    public Slider getStationRadiusSlider() {
        return stationRadius;
    }

    public Pane getCanvas() {
        return canvas;
    }

    // HELPER SETUP METHOD
    private void initLayout() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // fileToolbar
        saveButton = (Button) app.getGUI().getFileToolbar().getChildren().get(2);
        saveButton.setDisable(true);
        saveAsButton = gui.initChildButton(gui.getFileToolbar(), SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), false);
        exportButton = gui.initChildButton(gui.getFileToolbar(), EXIT_ICON.toString(), EXIT_TOOLTIP.toString(), false);

        // fileToolbar2
        /*
        fileToolbar2 = new FlowPane();
        fileToolbar2.setPrefWidth(450);

        cutButton = new Button();
        cutButton = gui.initChildButton(fileToolbar2, CUT_ICON.toString(), CUT_TOOLTIP.toString(), true);

        copyButton = new Button();
        copyButton = gui.initChildButton(fileToolbar2, COPY_ICON.toString(), COPY_TOOLTIP.toString(), true);

        pasteButton = new Button();
        pasteButton = gui.initChildButton(fileToolbar2, PASTE_ICON.toString(), PASTE_TOOLTIP.toString(), true);
        gui.getTopToolbarPane().getChildren().add(fileToolbar2);
         */
        //fileToolbar3
        fileToolbar3 = new FlowPane();
        fileToolbar3.setPrefWidth(400);

        // UNDO
        undoButton = new ToggleButton();
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(UNDO_ICON.toString());
        Image buttonImage = new Image(imagePath);

        undoButton.setDisable(true);
        undoButton.setSelected(false);
        undoButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(UNDO_TOOLTIP.toString()));
        undoButton.setTooltip(buttonTooltip);

        //REDO
        redoButton = new ToggleButton();
        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(REDO_ICON.toString());
        buttonImage = new Image(imagePath);

        redoButton.setDisable(true);
        redoButton.setSelected(false);
        redoButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(REDO_TOOLTIP.toString()));
        redoButton.setTooltip(buttonTooltip);

        fileToolbar3.getChildren().add(undoButton);
        fileToolbar3.getChildren().add(redoButton);
        gui.getTopToolbarPane().getChildren().add(fileToolbar3);

        //fileToolbar4
        fileToolbar4 = new FlowPane();
        fileToolbar4.setMaxWidth(120);

        aboutButton = new Button();
        aboutButton = gui.initChildButton(fileToolbar4, INFO_ICON.toString(), INFO_TOOLTIP.toString(), false);
        gui.getTopToolbarPane().getChildren().add(fileToolbar4);

        // THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
        editToolbar = new VBox();

        // ROW 1
        row1Box = new VBox();
        row1_1Box = new HBox();
        row1_2Box = new HBox();
        row1_3Box = new HBox();

        metroLinesLabel = new Label("Metro Lines");
        lines = new ComboBox();
        lineColorpicker = new ColorPicker();
        row1_1Box.getChildren().addAll(metroLinesLabel, lines, lineColorpicker);

        // initChildButton adds to the row1Box
        addLineButton = gui.initChildButton(row1_2Box, SELECTION_TOOL_ICON.toString(), SELECTION_TOOL_TOOLTIP.toString(), true);
        removeLineButton = gui.initChildButton(row1_2Box, REMOVE_ICON.toString(), REMOVE_ELEMENT_TOOLTIP.toString(), true);
        addStationButton = gui.initChildButton(row1_2Box, RECTANGLE_ICON.toString(), RECTANGLE_TOOLTIP.toString(), false);
        removeStationButton = gui.initChildButton(row1_2Box, ELLIPSE_ICON.toString(), ELLIPSE_TOOLTIP.toString(), false);
        listStationButton = gui.initChildButton(row1_2Box, ELLIPSE_ICON.toString(), ELLIPSE_TOOLTIP.toString(), false);

        lineThickness = new Slider(0, 10, 6);                          // default min max
        row1_3Box.getChildren().add(lineThickness);

        row1Box.getChildren().addAll(row1_1Box, row1_2Box, row1_3Box);

        // ROW2
        row2Box = new VBox();
        row2_1Box = new HBox();
        row2_2Box = new HBox();
        row2_3Box = new HBox();

        metroStationsLabel = new Label("Metro Stations");
        stations = new ComboBox();
        stationColorpicker = new ColorPicker();
        row2_1Box.getChildren().addAll(metroStationsLabel, stations, stationColorpicker);

        addStationButton2 = gui.initChildButton(row2_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        removeStationButton2 = gui.initChildButton(row2_2Box, ADDTEXT_ICON.toString(), ADDTEXT_TOOLTIP.toString(), false);
        snapButton = gui.initChildButton(row2_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        moveLabelButton = gui.initChildButton(row2_2Box, ADDTEXT_ICON.toString(), ADDTEXT_TOOLTIP.toString(), false);
        rotateButton = gui.initChildButton(row2_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);

        stationRadius = new Slider(0, 10, 6);
        row2_3Box.getChildren().add(stationRadius);

        row2Box.getChildren().addAll(row2_1Box, row2_2Box, row2_3Box);

        // ROW3
        row3Box = new HBox();
        row3_1Box = new VBox();
        row3_2Box = new VBox();

        direction1 = new ComboBox();
        direction2 = new ComboBox();
        row3_1Box.getChildren().addAll(direction1, direction2);
        changeDirButton = gui.initChildButton(row3_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);

        row3Box.getChildren().addAll(row3_1Box, row3_2Box);

        // ROW4
        row4Box = new VBox();
        row4_1Box = new HBox();
        row4_2Box = new HBox();

        decorLabel = new Label("Decor");
        decorColorpicker = new ColorPicker();
        row4_1Box.getChildren().addAll(decorLabel, decorColorpicker);

        setImageBgdButton = gui.initChildButton(row4_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        addImageButton = gui.initChildButton(row4_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        addLabelButton = gui.initChildButton(row4_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        RemoveElmtButton = gui.initChildButton(row4_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);

        row4Box.getChildren().addAll(row4_1Box, row4_2Box);

        // ROW5
        row5Box = new VBox();
        row5_1Box = new HBox();
        row5_2Box = new HBox();

        fontLabel = new Label("Font");
        fontColorpicker = new ColorPicker();
        row5_1Box.getChildren().addAll(fontLabel, fontColorpicker);

        boldButton = new ToggleButton();
        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(BOLD_ICON.toString());
        buttonImage = new Image(imagePath);

        boldButton.setSelected(false);
        boldButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(BOLD_TOOLTIP.toString()));
        boldButton.setTooltip(buttonTooltip);

        italicButton = new ToggleButton();
        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(ITALIC_ICON.toString());
        buttonImage = new Image(imagePath);

        italicButton.setSelected(false);
        italicButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(ITALIC_TOOLTIP.toString()));
        italicButton.setTooltip(buttonTooltip);

        fontsizecomboBox = new ComboBox<>();

        fontsizecomboBox.getItems().addAll(
                new Double(100),
                new Double(105),
                new Double(110),
                new Double(115),
                new Double(120),
                new Double(125),
                new Double(130),
                new Double(135),
                new Double(140),
                new Double(145),
                new Double(150),
                new Double(155),
                new Double(160),
                new Double(165),
                new Double(170),
                new Double(175),
                new Double(180),
                new Double(185),
                new Double(190),
                new Double(200)
        );

        fontcomboBox = new ComboBox<>();
        fontcomboBox.getItems().addAll(
                "Arial",
                "Menlo",
                "Tw Cen MT",
                "Shree Devanagari 714",
                "Arial Narrow"
        );

        row5_2Box.getChildren().addAll(boldButton, italicButton, fontsizecomboBox, fontcomboBox);
        row5Box.getChildren().addAll(row5_1Box, row5_2Box);

        // ROW6
        row6Box = new VBox();
        row6_1Box = new HBox();
        row6_2Box = new HBox();

        navigationLabel = new Label("Navigation");
        showGrid = new CheckBox("Show Grid");
        row6_1Box.getChildren().addAll(navigationLabel, showGrid);

        zoomin = gui.initChildButton(row6_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        zoomout = gui.initChildButton(row6_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        increase = gui.initChildButton(row6_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        decrease = gui.initChildButton(row6_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);

        row6Box.getChildren().addAll(row6_1Box, row6_2Box);

        // NOW ORGANIZE THE EDIT TOOLBAR
        editToolbar.getChildren().add(row1Box);
        editToolbar.getChildren().add(row2Box);
        editToolbar.getChildren().add(row3Box);
        editToolbar.getChildren().add(row4Box); // index 5
        editToolbar.getChildren().add(row5Box);
        editToolbar.getChildren().add(row6Box);

        // WE'LL RENDER OUR STUFF HERE IN THE CANVAS
        canvas = new Pane();
        debugText = new Text();
        canvas.getChildren().add(debugText);
        debugText.setX(100);
        debugText.setY(100);

        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_INFO.toString());
        // java.awt.Image image2 = new ImageIcon("cat2.png").getImage();
        // com.apple.eawt.Application.getApplication().setDockIconImage(image2);

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
/*
        logoEditController = new LogoEditController(app);

        imageButton.setOnAction(e -> {
            handleLoadRequest2();
        });

        textButton.setOnAction(e -> {
            logoEditController.processTextBox(promptToText(""));
        });

        langButton.setOnAction(e -> {
            String selectedLang = askLanguageSelection();

            if (selectedLang.equals("French") || selectedLang.equals("Français")) {
                boolean success = app.loadProperties("app_properties_FR.xml");
                if (success) {
                    initTopToolbar2(app);
                }
            } else {
                boolean success = app.loadProperties("app_properties_EN.xml");
                if (success) {
                    initTopToolbar2(app);
                }
            }
        });

        golData dataManager = (golData) app.getDataComponent();

        //logoEditController.processSelectBackgroundColor();
        infoButton.setOnAction(e -> {
            info();
        });

        undoButton.setOnAction(e -> {
            dataManager.undoTransaction();
        });

        redoButton.setOnAction(e -> {
            dataManager.redoTransaction();
        });

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

        canvas.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    canvasController.processModifyText((int) mouseEvent.getX(), (int) mouseEvent.getY());
                }
            }
        });

        canvas.setOnMouseReleased(e -> {
            canvasController.processCanvasMouseRelease((int) e.getX(), (int) e.getY());
        }
        );

        canvas.setOnMouseDragged(e -> {
            canvasController.processCanvasMouseDragged((int) e.getX(), (int) e.getY());
        }
        );

        outlineThicknessSlider.setOnMouseReleased(e -> {
            logoEditController.processSelectOutlineThickness();
        }
        );
        ;

    }

    // HELPER METHOD
    public void loadSelectedShapeSettings(Shape shape) {
        if (!shape.getUserData().equals("IMAGE")) {
            Color fillColor = (Color) shape.getFill();
            Color strokeColor = (Color) shape.getStroke();
            double lineThickness = shape.getStrokeWidth();
            fillColorPicker.setValue(fillColor);
            outlineColorPicker.setValue(strokeColor);
            outlineThicknessSlider.setValue(lineThickness);

            if (shape.getUserData().equals("TEXT")) { // Load extra properties for TEXT
                DraggableText text = (DraggableText) shape;

                if (text.getText() != null) {
                    comboBox.getSelectionModel().select(text.getFont().getFamily());
                    comboBox2.getSelectionModel().select((Double) text.getFont().getSize());

                }

            }
            //System.out.println("LOADED for " + shape.toString());

        }
         */
    }

    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {
        System.out.println("initiated");
        // NOTE THAT EACH CLASS SHOULD CORRESPOND TO
        // A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
        // CSS FILE
        canvas.getStyleClass().add(CLASS_RENDER_CANVAS);

        // COLOR PICKER STYLE
        /*
        fillColorPicker.getStyleClass().add(CLASS_BUTTON);
        outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
        backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
         */
        // fileToolbar2.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar3.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar4.getStyleClass().add(CLASS_BORDERED_PANE);

        undoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        redoButton.getStyleClass().add(CLASS_FILE_BUTTON);

        aboutButton.getStyleClass().add(CLASS_FILE_BUTTON);

        editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
        row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);

        row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        metroLinesLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        metroStationsLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        decorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fontLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        navigationLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);

    }

    /**
     * This function reloads all the controls for editing logos the workspace.
     */
    //@Override
    public void reloadWorkspace(AppDataComponent data) {
        /*
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
         */
    }

    public void reloadWorkspace2(boolean b) { // false at index -1
        if (b) {
            saveButton.setDisable(false);
            undoButton.setDisable(false);
            redoButton.setDisable(false);
            AppFileController f = app.getGUI().getFileController();
            f.setisSaved(false);
            //System.out.println("something to save");

        } else {
            saveButton.setDisable(true);

            undoButton.setDisable(true);
            AppFileController f = app.getGUI().getFileController();
            f.setisSaved(true);
            //System.out.println("nothing to save");

        }

    }

    public void reloadWorkspace3(boolean b) { // if last action disable redo

        if (b) {
            redoButton.setDisable(false);
        } else {
            redoButton.setDisable(true);
        }

    }

    /**
     * Asks user to select a language
     *
     * @return language
     */
    public String askLanguageSelection() {

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        List<String> choices = new ArrayList<>();
        choices.add(props.getProperty(FR));
        choices.add(props.getProperty(EN));

        ChoiceDialog<String> dialog = new ChoiceDialog<>(props.getProperty(EN), choices);
        dialog.setTitle(props.getProperty(LANG_TITLE));
        dialog.setHeaderText(props.getProperty(LANG_TEXT));

        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return "English";
        }

        return result.get();
    }

    /**
     * Initializes TopFileToolbar and EditToolbar in new Language
     *
     * @param app
     */
    private void initTopToolbar2(AppTemplate app) {

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // filetoolbar
        newButton = (Button) app.getGUI().getFileToolbar().getChildren().get(0);
        Tooltip buttonTooltip = new Tooltip(props.getProperty(NEW_TOOLTIP));
        newButton.setTooltip(buttonTooltip);

        loadButton = (Button) app.getGUI().getFileToolbar().getChildren().get(1);
        buttonTooltip = new Tooltip(props.getProperty(LOAD_TOOLTIP));
        loadButton.setTooltip(buttonTooltip);

        saveButton = (Button) app.getGUI().getFileToolbar().getChildren().get(2);
        buttonTooltip = new Tooltip(props.getProperty(SAVE_TOOLTIP));
        saveButton.setTooltip(buttonTooltip);
        /*
        exitButton = (Button) app.getGUI().getFileToolbar().getChildren().get(3);
        buttonTooltip = new Tooltip(props.getProperty(EXIT_TOOLTIP));
        exitButton.setTooltip(buttonTooltip);
         */
        //filetoolbar2
        /*
        buttonTooltip = new Tooltip(props.getProperty(CUT_TOOLTIP));
        cutButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(COPY_TOOLTIP));
        copyButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(PASTE_TOOLTIP));
        pasteButton.setTooltip(buttonTooltip);
         */
        //filetoolbar3
        buttonTooltip = new Tooltip(props.getProperty(UNDO_TOOLTIP));
        undoButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(REDO_TOOLTIP));
        redoButton.setTooltip(buttonTooltip);

        //filetoolbar4
        buttonTooltip = new Tooltip(props.getProperty(LANG_TOOLTIP));

        buttonTooltip = new Tooltip(props.getProperty(INFO_TOOLTIP));
        aboutButton.setTooltip(buttonTooltip);

        // edittoolbar
        // row1
        /*
        buttonTooltip = new Tooltip(props.getProperty(SELECTION_TOOL_TOOLTIP));
        selectionToolButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(REMOVE_ELEMENT_TOOLTIP));
        removeButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(RECTANGLE_TOOLTIP));
        rectButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(ELLIPSE_TOOLTIP));
        ellipseButton.setTooltip(buttonTooltip);

        //row1_2
        buttonTooltip = new Tooltip(props.getProperty(ADDPICTURE_TOOLTIP));
        imageButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(ADDTEXT_TOOLTIP));
        textButton.setTooltip(buttonTooltip);

        //row1_3
        buttonTooltip = new Tooltip(props.getProperty(ITALIC_TOOLTIP));
        italicButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(BOLD_TOOLTIP));
        boldButton.setTooltip(buttonTooltip);

        //row2
        buttonTooltip = new Tooltip(props.getProperty(MOVE_TO_BACK_TOOLTIP));
        moveToBackButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(MOVE_TO_FRONT_TOOLTIP));
        moveToFrontButton.setTooltip(buttonTooltip);

        //row7
        buttonTooltip = new Tooltip(props.getProperty(SNAPSHOT_TOOLTIP));
        snapshotButton.setTooltip(buttonTooltip);

        // Add new label in new lang
        backgroundColorLabel = new Label(props.getProperty(BACKGROUNDCOLOR));
        row3Box.getChildren().set(0, backgroundColorLabel);

        // change tooltip for colorpicker
        buttonTooltip = new Tooltip(props.getProperty(BACKGROUND_TOOLTIP));
        backgroundColorPicker.setTooltip(buttonTooltip);

        // ROW 4
        fillColorLabel = new Label(props.getProperty(FILLCOLOR));
        row4Box.getChildren().set(0, fillColorLabel);

        buttonTooltip = new Tooltip(props.getProperty(FILL_TOOLTIP));
        fillColorPicker.setTooltip(buttonTooltip);

        // ROW 5
        outlineColorLabel = new Label(props.getProperty(OUTLINECOLOR));
        row5Box.getChildren().set(0, outlineColorLabel);

        buttonTooltip = new Tooltip(props.getProperty(OUTLINE_TOOLTIP));
        outlineColorPicker.setTooltip(buttonTooltip);

        // ROW 6
        outlineThicknessLabel = new Label(props.getProperty(OUTLINETHICKNESS));
        row6Box.getChildren().set(0, outlineThicknessLabel);

        // Update styles since some Labels are new()
        initStyle();
         */
    }

    /**
     * Allows user to upload a Image
     *
     */
    public void handleLoadRequest2() {
        try {

            PropertiesManager props = PropertiesManager.getPropertiesManager();

            // AND NOW ASK THE USER FOR THE FILE TO OPEN
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

            // ONLY OPEN A NEW FILE IF THE USER SAYS OK
            if (selectedFile != null) {
                // RESET THE WORKSPACE

                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                File file = new File(selectedFile.getAbsolutePath());
                Image image = new Image(file.toURI().toString());
                String filepath = file.toURI().toString();

                //ImageView imageView = new ImageView(image);
                logoEditController.processMakeImageasShape(image, filepath);

            }
        } catch (Exception ioe) {
            // SOMETHING WENT WRONG
            ioe.printStackTrace();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }

    /**
     *
     * Dialogue for text input Asks users to type text to add
     *
     * @return input text
     */
    public String promptToText(String s) {

        TextInputDialog dialog = new TextInputDialog(s);
        dialog.setTitle("TEXT BOX");
        dialog.setHeaderText("Add a Text Box");
        //dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    /**
     * If LangSelection.txt file exists, read it and updates the UI in that
     * Language Otherwise, create LangSelection.txt and save selected Language
     */
    public void languageSelection() {

        String line = null;

        try {
            File file = new File("LangSelection.txt");
            file.createNewFile();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            if ((line = br.readLine()) != null) {       // SUPER IMPORTANT TO USE LINE AND NOT br.readLine()
                // updates
                if (line.equals("French")) {
                    boolean success = app.loadProperties("app_properties_FR.xml");
                    if (success) {
                        initTopToolbar2(app);
                    }
                } else {
                    // ENG by default
                }
            } else { // ASKS selection

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                answer0 = askLanguageSelection();
                answer = answer0 + "\n";
                System.out.println(answer0);

                bw.write(answer);
                bw.flush();
                bw.close();

                if (answer0.equals("French")) {
                    boolean success = app.loadProperties("app_properties_FR.xml");

                    if (success) {
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
     * Dialogue for Info about app
     */
    public void info() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_INFO.toString());
        Image image = new Image(imagePath);

        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(appTitle);
        a.setHeaderText("   APP INFORMATION");
        a.setContentText("  goLogoLo is Created by Daeun Park in 2017 with <3");

        ImageView imageView = new ImageView(image);
        a.setGraphic(imageView);
        a.showAndWait();

    }

    @Override
    public void resetWorkspace() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean getundobtn() {
        return undoButton.isSelected();
    }

    public boolean getredobtn() {
        return redoButton.isSelected();
    }

    public void resetundobtn() {
        undoButton.setSelected(false);
        // System.out.println("undo reseted");

    }

    public void resetredobtn() {
        redoButton.setSelected(false);
        //System.out.println("redo reseted");
    }

    // Allows access
    public CanvasController getCanvasController() {
        return canvasController;
    }

}
