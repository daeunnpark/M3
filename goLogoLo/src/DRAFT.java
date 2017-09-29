/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daeun
 */
public class DRAFT { 
        /*
    
    
    
    
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
