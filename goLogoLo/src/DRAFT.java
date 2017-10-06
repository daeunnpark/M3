
import djf.AppTemplate;
import gol.data.Draggable;
import gol.data.DraggableText;
import gol.data.golData;
import gol.data.golState;
import static gol.data.golState.DRAGGING_NOTHING;
import static gol.data.golState.DRAGGING_SHAPE;
import static gol.data.golState.SELECTING_SHAPE;
import static gol.data.golState.SIZING_SHAPE;
import gol.gui.golWorkspace;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *STROKE FOR IMAGE?
 * @author Daeun
 */
public class DRAFT { 
    /*
    
    package gol.data;

import javafx.scene.shape.Rectangle;

/**
 * This is a draggable rectangle for our goLogoLo application.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
    
    /*
public class DraggableRectangle extends Rectangle implements Draggable {
    double startX;
    double startY;
    
    public DraggableRectangle() {
	setX(0.0);
	setY(0.0);
	setWidth(0.0);
	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0; 
	startY = 0.0;
    }
    
    @Override
    public golState getStartingState() {
	return golState.SELECTING_SHAPE;
    }
    
    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
	setX(x);
	setY(y);
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - (getX() + (getWidth()/2));
	double diffY = y - (getY() + (getHeight()/2));
	double newX = getX() + diffX;
	double newY = getY() + diffY;
        
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }
    
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }
    
    @Override
    public void size(int x, int y) {
	double width = x - getX();
	widthProperty().set(width);
	double height = y - getY();
	heightProperty().set(height);	
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX);
	yProperty().set(initY);
	widthProperty().set(initWidth);
	heightProperty().set(initHeight);
    }
    
    @Override
    public String getShapeType() {
	return RECTANGLE;
    }
}

    
    
    
    
                workspace.comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                       // DraggableText text = (DraggableText) shape;

                        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            System.out.println(" ----- From " + oldValue.toString() + " to " + newValue.toString());
                            i
                            }

                        }
                    });


                    
                    workspace.comboBox.setOnAction(e -> {
                        if (workspace.comboBox.getSelectionModel().getSelectedItem() != null) {
                            dataManager.changefont(shape, workspace.comboBox.getSelectionModel().getSelectedItem().toString());
                        }
                    });

                    workspace.comboBox2.setOnAction(e -> {
                        if (workspace.comboBox2.getSelectionModel().getSelectedItem() != null) {
                            dataManager.changesize(shape, Integer.parseInt(workspace.comboBox2.getSelectionModel().getSelectedItem().toString()));
                        }
                    });
                     
    
    
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
    
    
    
    
    
                    workspace.comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

                        @Override
                        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                          System.out.println(" ----- From " + oldValue.toString() + " to " + newValue.toString());
                            if(!text.getFont().getFamily().equals(newValue) ){
                                System.out.println("CHANGEs");
                                System.out.println(shape.toString());
                                dataManager.changefont(shape, workspace.comboBox.getSelectionModel().getSelectedItem().toString());
                          }
                            
                            
                        }
                    });

         @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
      public void getBolded(Shape shape) {
        DraggableText text = (DraggableText) shape;

        FontWeight fontweight = FontWeight.NORMAL;
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        //.getName() contains font name, Bold, Italic and etc
        
        if (!text.getFont().getName().contains("Bold")) { // to Bold
            fontweight = FontWeight.EXTRA_BOLD;
        }

        FontPosture fontposture = FontPosture.REGULAR;
        if (text.getFont().getName().contains("Italic")) { // keep italic
            fontposture = FontPosture.ITALIC;
        }

        text.setFont(Font.font(text.getFont().getFamily(), fontweight, fontposture, text.getFont().getSize()));
    }

    
    
    
    
    
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
import static gol.data.golState.Loadingprop_TEXT;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 
public class CanvasController {

    AppTemplate app;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     
    public void processCanvasMousePress(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();
       // Shape shape = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();
            golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent(); // ??NEEDS
            
    
        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape(x, y);
            //Scene scene = app.getGUI().getPrimaryScene();

            // AND START DRAGGING IT
            System.out.println("DRAG");
            if (shape != null) {
                 System.out.println("Ggg");

                scene.setCursor(Cursor.MOVE);
                dataManager.setState(golState.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);

                if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {

                    // workspace = (golWorkspace) app.getWorkspaceComponent();
                    
                  

                    System.out.println("YES");

                    workspace.boldButton.setOnAction(e -> {
                        dataManager.getBolded(shape);
                    });

                    workspace.italicButton.setOnAction(e -> {
                        dataManager.getItalicized(shape);
                    });

                    workspace.comboBox.setOnAction(e -> {
                        if (workspace.comboBox.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("comboBox on action this");
                            dataManager.changefont(shape, workspace.comboBox.getSelectionModel().getSelectedItem().toString());
                        }
                    });

                    workspace.comboBox2.setOnAction(e -> {
                        if (workspace.comboBox2.getSelectionModel().getSelectedItem() != null) {
                            dataManager.changesize(shape, Double.parseDouble(workspace.comboBox2.getSelectionModel().getSelectedItem().toString()));
                        }
                    });

                }

            } else // shape null
            {
                System.out.println("NUlLLLL");
                scene.setCursor(Cursor.DEFAULT);
            }
            dataManager.setState(DRAGGING_NOTHING);
            app.getWorkspaceComponent().reloadWorkspace(dataManager);
        }
    

    else if (dataManager.isInState (golState.STARTING_RECTANGLE) 
        ) {
            dataManager.startNewRectangle(x, y);
    }

    else if (dataManager.isInState (golState.STARTING_ELLIPSE) 
        ) {
            dataManager.startNewEllipse(x, y);
    }

    //golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

    workspace.reloadWorkspace (dataManager);
}

/**
 * Respond to mouse dragging on the rendering surface, which we call canvas, but
 * is actually a Pane.
 
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
     
    public void processCanvasMouseRelease(int x, int y) {
        golData dataManager = (golData) app.getDataComponent();
         //golData dataManager = (golData) app.getDataComponent();
         
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Shape shape = dataManager.selectTopShape(x, y);
        
      
        if (dataManager.isInState(SIZING_SHAPE)) {
        //if (dataManager.isInState(SIZING_SHAPE)) {
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
    
    
    
    
        if(shape!=null){
            if (shape.getUserData() != null && shape.getUserData().equals("TEXT")) {

                workspace.loadSelectedTextSettings(shape);
                dataManager.setState(SELECTING_SHAPE);
                // state = SELECTING_SHAPE;
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
     
 
    
        "10",
                        "12",
                        "14",
                        "16",
                        "18",
                        "20",
                        "22",
                        "24",
                        "26",
                        "28",
                        "30",
                        "32",
                        "34",
                        "36",
                        "38",
    
    
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
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(golState.STARTING_RECTANGLE)) {
            dataManager.startNewRectangle(x, y);
        } else if (dataManager.isInState(golState.STARTING_ELLIPSE)) {
            dataManager.startNewEllipse(x, y);
        } else if (dataManager.isInState(golState.MODIFYING_TEXT)) {
            //  DraggableText d = (DraggableText) shape;

        }
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }
    
    
    
    
    
    
    public void promptToText() {

        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("TEXT BOX");
        dialog.setHeaderText("Add a Text Box");
        //dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            logoEditController.processTextBox(result.get());
        }
    }
    
    
    
    
    
    /*
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
    
    
    
    
    
    
    
    public void SaveJSONFileLang(String choice) throws FileNotFoundException { // throws IOException {
        String language = choice;
        //J language = choice;
        // GET THE DATA
        //golData dataManager = (golData)data;

        JsonObject JSONLANG = Json.createObjectBuilder()
                .add("JSON_CHOICE", language)
                .build();

        String filepath = "E:\\LANGUAGE.json";
        
        try {  
              
            // writing data to a file in JSON  
            File file=new File(filepath);  
            file.createNewFile();  
            FileWriter fileWriter = new FileWriter(file);  
            System.out.println("Writing JSON object to file");  
            //System.out.print(countryObj);  
  
            fileWriter.write(JSONLANG.toString());  
            
            fileWriter.flush();  
            fileWriter.close();  
  
            
            // INIT WRITER
            
             OutputStream os = new FileOutputStream(filepath);
        FileWriter jsonFileWriter = File.createWriter(os);
        jsonFileWriter.writeObject(JSONLANG);
        //String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filepath);
        //pw.write(prettyPrinted);
        pw.close();
            
        
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> lang = new HashMap<>(1);
        //properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(lang);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(JSONLANG);
        jsonWriter.close();
        

    
    }

    public String ReadLang(String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
       // golData dataManager = (golData) data;
        //dataManager.resetData();

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);
        
        String s = json.getString(JSON_CHOICE);
        if (s!=null){
        if(s.equals("English")){
            System.out.println("ENG - json");
 
        }
        else if(s.equals("French")){
            System.out.println("FRN - json");
            
        }
        }

      return s;
    }
    
    
    
    
    
    
    
      public void initNewShape() {
	// DESELECT THE SELECTED SHAPE IF THERE IS ONE
	if (selectedShape != null) {
	    unhighlightShape(selectedShape);
	    selectedShape = null;
	}

	// USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
	golWorkspace workspace = (golWorkspace)app.getWorkspaceComponent();
	newShape.setFill(workspace.getFillColorPicker().getValue());
	newShape.setStroke(workspace.getOutlineColorPicker().getValue());
	newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());
	
	// ADD THE SHAPE TO THE CANVAS
	shapes.add(newShape);
	
	// GO INTO SHAPE SIZING MODE
	state = golState.SIZING_SHAPE;
    }
    
    
    
    
      @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        golData dataManager = (golData) data;
        dataManager.resetData();

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE BACKGROUND COLOR
        Color bgColor = loadColor(json, JSON_BG_COLOR);
        dataManager.setBackgroundColor(bgColor);

        // AND NOW LOAD ALL THE SHAPES
        JsonArray jsonShapeArray = json.getJsonArray(JSON_SHAPES);
        for (int i = 0; i < jsonShapeArray.size(); i++) {
            JsonObject jsonShape = jsonShapeArray.getJsonObject(i);
            Shape shape = loadShape(jsonShape);
            dataManager.addShape(shape);
        }
        
        
        
        
    }
    
    
    
    
    
    
    
    
                    // writing data to a file in JSON
                    File file = new File(filepath);
                    file.createNewFile();
                    FileWriter fileWriter = new FileWriter(file);
                    System.out.println("Writing JSON object to file");
                    //System.out.print(countryObj);

                    fileWriter.write(JSONLANG.toString());

                    fileWriter.flush();
                    fileWriter.close();

                    app.getFileComponent().saveData(app.getDataComponent(), selectedFile.getPath());

                    List<String> choices = new ArrayList<>();
                    choices.add("English");
                    choices.add("French");

                    ChoiceDialog<String> dialog = new ChoiceDialog<>("English", choices);
                    dialog.setTitle("Language Setting");
                    dialog.setHeaderText("Please select your language");

                    Optional<String> result = dialog.showAndWait();
                    PropertiesManager props = PropertiesManager.getPropertiesManager();

                    if (result.isPresent()) {

                        if (result.get().equals("French")) {
                            try {
                                boolean success = app.loadProperties("app_properties_FR.xml");
                                if (success) {
                                    // GET THE TITLE FROM THE XML FILE
                                    String appTitle = props.getProperty(APP_TITLE);
                                    initTopToolbar2(app);
                                }
                            } catch (Exception e) {
                                System.out.println("Error from wspace");
                            }
                        }
                    }
                }
    
    
    
    
    
    public void SaveJSONFileLang(String choice) throws FileNotFoundException { // throws IOException {

        
         String filepath = "E:\\LANGUAGE.json";
    
        //J language = choice;
        // GET THE DATA
        //golData dataManager = (golData)data;

        JsonObject JSONLANG = Json.createObjectBuilder()
                .add("JSON_CHOICE", choice)
                .build();

       

        try {

            // writing data to a file in JSON  
            File file = new File(filepath);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            System.out.println("Writing JSON object to file");
            //System.out.print(countryObj);  

            fileWriter.write(JSONLANG.toString());

            fileWriter.flush();
            fileWriter.close();

            // INIT WRITER
            
             OutputStream os = new FileOutputStream(filepath);
        FileWriter jsonFileWriter = File.createWriter(os);
        jsonFileWriter.writeObject(JSONLANG);
        //String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filepath);
        //pw.write(prettyPrinted);
        pw.close();
             
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> lang = new HashMap<>(1);
        //properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(lang);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(JSONLANG);
        jsonWriter.close();
         

    }
    

    public String ReadLang(String filePath) throws IOException {

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        String s = json.getString(JSON_CHOICE);
        if (s != null) {
            if (s.equals("English")) {
                System.out.println("ENG - json");

            } else if (s.equals("French")) {
                System.out.println("FRN - json");

            }
        }

        return s;
    }
    
    
    
     workspace.comboBox.getSelectionModel().selectedItemProperty().addListener(e -> {
                        System.out.println("combo changed");
                        System.out.println(e.toString());
                        
                        System.out.println(workspace.comboBox.getSelectionModel().selectedItemProperty().get() + " SELECTED1");
                        
                        String s = workspace.comboBox.getSelectionModel().selectedItemProperty().getValue().toString();
                        //System.out.println(s + "--- ss");
                        String f = workspace.comboBox.getSelectionModel().selectedItemProperty().toString();
                       // System.out.println(f + " ---------------- f");
                        //System.out.println(f + " SELECTED2");

                        
                        //String f = workspace.comboBox.getSelectionModel().selectedItemProperty().toString();
                        if (!text2.getFont().getName().equals(s)) {
                            //System.out.println("combo changed");
                            //System.out.println(shape.toString() + "22");
                            //dataManager.changefont(shape, e.toString());
                            // dataManager.changefont(shape, workspace.comboBox.getSelectionModel().getSelectedItem().toString());
                        }
                        else {
                        System.out.println("SAME");}

                    });
        
        */
    
}
