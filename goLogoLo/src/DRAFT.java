
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

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
        
        */
    
}
