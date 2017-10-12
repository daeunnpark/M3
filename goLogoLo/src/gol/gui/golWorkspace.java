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
import static djf.settings.AppPropertyType.BACKGROUNDCOLOR;
import static djf.settings.AppPropertyType.COPY_ICON;
import static djf.settings.AppPropertyType.COPY_TOOLTIP;
import static djf.settings.AppPropertyType.CUT_ICON;
import static djf.settings.AppPropertyType.CUT_TOOLTIP;
import static djf.settings.AppPropertyType.EN;

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

    // FIRST ROW
    HBox row1Box;
    Button selectionToolButton;
    Button removeButton;
    Button rectButton;
    Button ellipseButton;

    // 1_2 ROW
    HBox row1_2Box;
    Button imageButton;
    Button textButton;

    // 1_3 ROW
    HBox row1_3Box;
    ToggleButton italicButton;
    ToggleButton boldButton;

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
    private Button langButton;
    private Button infoButton;
    Button cutButton;
    Button copyButton;
    Button pasteButton;
    ToggleButton undoButton;
    ToggleButton redoButton;

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
    ComboBox comboBox, comboBox2;


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
        System.out.println("HH");
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

        undoredo();
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
        
        // fileToolbar2
        fileToolbar2 = new FlowPane(); 
        fileToolbar2.setPrefWidth(450);
        
        cutButton = new Button();
        cutButton = gui.initChildButton(fileToolbar2, CUT_ICON.toString(), CUT_TOOLTIP.toString(), false);

        copyButton = new Button();
        copyButton = gui.initChildButton(fileToolbar2, COPY_ICON.toString(), COPY_TOOLTIP.toString(), false);

        pasteButton = new Button();
        pasteButton = gui.initChildButton(fileToolbar2, PASTE_ICON.toString(), PASTE_TOOLTIP.toString(), true);
        gui.getTopToolbarPane().getChildren().add(fileToolbar2);

    
        //fileToolbar3
        fileToolbar3 = new FlowPane();
        fileToolbar3.setPrefWidth(400);
       
        // UNDO
        undoButton = new ToggleButton();
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(UNDO_ICON.toString());
        Image buttonImage = new Image(imagePath);

        undoButton.setSelected(false);
        undoButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(UNDO_TOOLTIP.toString()));
        undoButton.setTooltip(buttonTooltip);
 
        //REDO
        redoButton = new ToggleButton();
        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(REDO_ICON.toString());
        buttonImage = new Image(imagePath);

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
        
        langButton = new Button();
        langButton = gui.initChildButton(fileToolbar4, LANG_ICON.toString(), LANG_TOOLTIP.toString(), false);

        infoButton = new Button();
        infoButton = gui.initChildButton(fileToolbar4, INFO_ICON.toString(), INFO_TOOLTIP.toString(), false);
        gui.getTopToolbarPane().getChildren().add(fileToolbar4);
  
        
        
        // THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
        editToolbar = new VBox();

        // ROW 1
        row1Box = new HBox();
        selectionToolButton = gui.initChildButton(row1Box, SELECTION_TOOL_ICON.toString(), SELECTION_TOOL_TOOLTIP.toString(), true);
        removeButton = gui.initChildButton(row1Box, REMOVE_ICON.toString(), REMOVE_ELEMENT_TOOLTIP.toString(), true);
        rectButton = gui.initChildButton(row1Box, RECTANGLE_ICON.toString(), RECTANGLE_TOOLTIP.toString(), false);
        ellipseButton = gui.initChildButton(row1Box, ELLIPSE_ICON.toString(), ELLIPSE_TOOLTIP.toString(), false);

        //ROW 1_2
        row1_2Box = new HBox();
        imageButton = gui.initChildButton(row1_2Box, ADDPICTURE_ICON.toString(), ADDPICTURE_TOOLTIP.toString(), false);
        textButton = gui.initChildButton(row1_2Box, ADDTEXT_ICON.toString(), ADDTEXT_TOOLTIP.toString(), false);

        //row1_3
        row1_3Box = new HBox();

        italicButton = new ToggleButton();
        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(ITALIC_ICON.toString());
        buttonImage = new Image(imagePath);

        italicButton.setSelected(false);
        italicButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(ITALIC_TOOLTIP.toString()));
        italicButton.setTooltip(buttonTooltip);
        
        
        boldButton = new ToggleButton();
        imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(BOLD_ICON.toString());
        buttonImage = new Image(imagePath);

        boldButton.setSelected(false);
        boldButton.setGraphic(new ImageView(buttonImage));
        buttonTooltip = new Tooltip(props.getProperty(BOLD_TOOLTIP.toString()));
        boldButton.setTooltip(buttonTooltip);
        
        row1_3Box.getChildren().add(italicButton);
        row1_3Box.getChildren().add(boldButton);

        
        // FONT TYPE
        comboBox = new ComboBox<>();

        comboBox.getItems().addAll(
                "Arial",
                "Menlo",
                "Tw Cen MT",
                "Shree Devanagari 714",
                "Arial Narrow"
        );

        
        // FONT SIZE
        comboBox2 = new ComboBox<>();

        comboBox2.getItems().addAll(
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

        row1_3Box.getChildren().addAll(comboBox, comboBox2);

        
        // ROW 2
        row2Box = new HBox();
        moveToBackButton = gui.initChildButton(row2Box, MOVE_TO_BACK_ICON.toString(), MOVE_TO_BACK_TOOLTIP.toString(), true);
        moveToFrontButton = gui.initChildButton(row2Box, MOVE_TO_FRONT_ICON.toString(), MOVE_TO_FRONT_TOOLTIP.toString(), true);

        // ROW 3
        row3Box = new VBox();
        backgroundColorLabel = new Label(props.getProperty(BACKGROUNDCOLOR));
        backgroundColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
        buttonTooltip = new Tooltip(props.getProperty(BACKGROUND_TOOLTIP));
        backgroundColorPicker.setTooltip(buttonTooltip);
        row3Box.getChildren().add(backgroundColorLabel);
        row3Box.getChildren().add(backgroundColorPicker);

        // ROW 4
        row4Box = new VBox();
        fillColorLabel = new Label(props.getProperty(FILLCOLOR));
        fillColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
        buttonTooltip = new Tooltip(props.getProperty(FILL_TOOLTIP));
        fillColorPicker.setTooltip(buttonTooltip);
        row4Box.getChildren().add(fillColorLabel);
        row4Box.getChildren().add(fillColorPicker);
       
        // ROW 5
        row5Box = new VBox();
        outlineColorLabel = new Label(props.getProperty(OUTLINECOLOR));
        outlineColorPicker = new ColorPicker(Color.valueOf(BLACK_HEX));
        buttonTooltip = new Tooltip(props.getProperty(OUTLINE_TOOLTIP));
        outlineColorPicker.setTooltip(buttonTooltip);
        row5Box.getChildren().add(outlineColorLabel);
        row5Box.getChildren().add(outlineColorPicker);

        // ROW 6
        row6Box = new VBox();
        outlineThicknessLabel = new Label(props.getProperty(OUTLINETHICKNESS));
        outlineThicknessSlider = new Slider(0, 10, 6);                          // default min max value
        
        row6Box.getChildren().add(outlineThicknessLabel);
        row6Box.getChildren().add(outlineThicknessSlider);

        // ROW 7
        row7Box = new HBox();
        snapshotButton = gui.initChildButton(row7Box, SNAPSHOT_ICON.toString(), SNAPSHOT_TOOLTIP.toString(), false);

        // NOW ORGANIZE THE EDIT TOOLBAR
        editToolbar.getChildren().add(row1Box);
        editToolbar.getChildren().add(row1_2Box);
        editToolbar.getChildren().add(row1_3Box);
        editToolbar.getChildren().add(row2Box);
        editToolbar.getChildren().add(row3Box);
        editToolbar.getChildren().add(row4Box); // index 5
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

        imageButton.setOnAction(e -> {
            handleLoadRequest2();
        });

        textButton.setOnAction(e -> {
            logoEditController.processTextBox(promptToText("daeun"));               // TO CHANGE
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
        /*
        outlineThicknessSlider.valueProperty().addListener(e -> {
            logoEditController.processSelectOutlineThickness();
        });
        */
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
                    // golData dataManager = (golData) app.getDataComponent();
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
            //System.out.println("RELEASED");
           logoEditController.processSelectOutlineThickness();
        }
        );

    }

    // HELPER METHOD
    public void loadSelectedShapeSettings(Shape shape) {
    
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
            System.out.println("LOADED for " + shape.toString());

 
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

        fileToolbar2.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar3.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar4.getStyleClass().add(CLASS_BORDERED_PANE);
        
        cutButton.getStyleClass().add(CLASS_FILE_BUTTON);
	copyButton.getStyleClass().add(CLASS_FILE_BUTTON);
	pasteButton.getStyleClass().add(CLASS_FILE_BUTTON);
        
	undoButton.getStyleClass().add(CLASS_FILE_BUTTON);
	redoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        
	langButton.getStyleClass().add(CLASS_FILE_BUTTON);
	infoButton.getStyleClass().add(CLASS_FILE_BUTTON);

        editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
        row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row1_2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row1_3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        
        
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

        exitButton = (Button) app.getGUI().getFileToolbar().getChildren().get(3);
        buttonTooltip = new Tooltip(props.getProperty(EXIT_TOOLTIP));
        exitButton.setTooltip(buttonTooltip);

        //filetoolbar2
        buttonTooltip = new Tooltip(props.getProperty(CUT_TOOLTIP));
        cutButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(COPY_TOOLTIP));
        copyButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(PASTE_TOOLTIP));
        pasteButton.setTooltip(buttonTooltip);

        //filetoolbar3
        buttonTooltip = new Tooltip(props.getProperty(UNDO_TOOLTIP));
        undoButton.setTooltip(buttonTooltip);
        
        buttonTooltip = new Tooltip(props.getProperty(REDO_TOOLTIP));
        redoButton.setTooltip(buttonTooltip);
        
        //filetoolbar4
        buttonTooltip = new Tooltip(props.getProperty(LANG_TOOLTIP));
        langButton.setTooltip(buttonTooltip);

        buttonTooltip = new Tooltip(props.getProperty(INFO_TOOLTIP));
        infoButton.setTooltip(buttonTooltip);

        // edittoolbar
        // row1
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

                ImageView imageView = new ImageView(image);
                logoEditController.processMakeImageasShape(image);

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

        Image image = new Image("file:/Users/Daeun/NetBeansProjects/hw2/goLogoLo/images/cat.png");

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
        System.out.println("undo reseted");

    }

    public void resetredobtn() {
        redoButton.setSelected(false);
        System.out.println("redo reseted");
    }
    
    // Allows access
    public CanvasController getCanvasController(){
        //canvasController = new CanvasController(app);
    return canvasController;
    }
    
}
