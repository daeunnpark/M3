

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daeun
 */
public class DRAFT2 {
    // ROW 3
    /*
    package gol;

import java.util.Locale;
import gol.data.golData;
import gol.file.golFiles;
import gol.gui.golWorkspace;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.APP_LOGO;
import static djf.settings.AppPropertyType.APP_TITLE;
import static djf.settings.AppPropertyType.PREF_HEIGHT;
import static djf.settings.AppPropertyType.PREF_WIDTH;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.START_MAXIMIZED;
import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 * This class serves as the application class for our goLogoLoApp program. Note
 * that much of its behavior is inherited from AppTemplate, as defined in the
 * Desktop Java Framework. This app starts by loading all the app-specific
 * messages like icon files and tooltips and other settings, then the full User
 * Interface is loaded using those settings. Note that this is a JavaFX
 * application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 
public class goLogoLoApp extends AppTemplate {

    Stage window;
    Scene welcomedialog, workspace;

    
     * This hook method must initialize all three components in the proper order
     * ensuring proper dependencies are respected, meaning all proper objects
     * are already constructed when they are needed for use, since some may need
     * others for initialization.
     
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL THREE COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, AND THE DATA COMPONENT NEEDS THE
        // FILE COMPONENT SO WE MUST BE CAREFUL OF THE ORDER
        fileComponent = new golFiles();
        dataComponent = new golData(this);
        workspaceComponent = new golWorkspace(this);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;// pointer

        AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
        messageDialog.init(primaryStage);
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.init(primaryStage);
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        try {
            // LOAD APP PROPERTIES, BOTH THE BASIC UI STUFF FOR THE FRAMEWORK
            // AND THE CUSTOM UI STUFF FOR THE WORKSPACE
            boolean success = loadProperties(APP_PROPERTIES_FILE_NAME);

            if (success) {
                // GET THE TITLE FROM THE XML FILE
                String appTitle = props.getProperty(APP_TITLE);

                // BUILD THE BASIC APP GUI OBJECT FIRST
                gui = new AppGUI(primaryStage, appTitle, this);
                buildAppComponentsHook();

                workspace = gui.getPrimaryScene();

                window.setResizable(true);
                window.setWidth(workspace.getWidth());
                window.setHeight(workspace.getHeight());

                StackPane s = new StackPane();
                Button b = new Button("Create New Metro Map");

                b.setOnAction(e -> window.setScene(workspace));
                s.getChildren().add(b);
                welcomedialog = new Scene(s);

                window.setScene(welcomedialog);

                // NOW OPEN UP THE WINDOW
                primaryStage.show();
            }
        } catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
        }

    }

    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method
     * inherited from AppTemplate, defined in the Desktop Java Framework.
     
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
}

    
    
    
    
    
        row3Box = new VBox();
        backgroundColorLabel = new Label(props.getProperty(BACKGROUNDCOLOR));
        backgroundColorPicker = new ColorPicker();
        buttonTooltip = new Tooltip(props.getProperty(BACKGROUND_TOOLTIP));
        backgroundColorPicker.setTooltip(buttonTooltip);
        row3Box.getChildren().add(backgroundColorLabel);
        row3Box.getChildren().add(backgroundColorPicker);
*/
}
