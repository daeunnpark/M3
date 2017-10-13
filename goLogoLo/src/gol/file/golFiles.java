package gol.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import gol.data.golData;
import gol.data.DraggableEllipse;
import gol.data.DraggableRectangle;
import gol.data.Draggable;
import static gol.data.Draggable.RECTANGLE;
import gol.data.DraggableText;
import static java.awt.PageAttributes.ColorType.COLOR;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class golFiles implements AppFileComponent {

    // FOR JSON LOADING
    static final String JSON_BG_COLOR = "background_color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_SHAPES = "shapes";
    static final String JSON_SHAPE = "shape";
    static final String JSON_TYPE = "type";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    static final String JSON_FILL_COLOR = "fill_color";
    static final String JSON_OUTLINE_COLOR = "outline_color";
    static final String JSON_OUTLINE_THICKNESS = "outline_thickness";
    static final String JSON_TEXT = "text";
    static final String JSON_FONT = "font";
    static final String JSON_FONTFAMILY = "fontfamily";
    static final String JSON_FONTSIZE = "fontsize";
    static final String JSON_FILEPATH = "filepath";

    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";
    String JSON_CHOICE = "choice";

    /**
     * This method is for saving user work, which in the case of this
     * application means the data that together draws the logo.
     *
     * @param data The data management component for this application.
     *
     * @param filePath Path (including file name/extension) to where to save the
     * data to.
     *
     * @throws IOException Thrown should there be an error writing out data to
     * the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
        golData dataManager = (golData) data;

        // FIRST THE BACKGROUND COLOR
        Color bgColor = dataManager.getBackgroundColor();
        JsonObject bgColorJson = makeJsonColorObject(bgColor);

        // NOW BUILD THE JSON OBJCTS TO SAVE
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ObservableList<Node> shapes = dataManager.getShapes();
        for (Node node : shapes) {
            Shape shape = (Shape) node;
            Draggable draggableShape = ((Draggable) shape);
            //String type = draggableShape.getShapeType();
            String type = node.getUserData().toString();

            String text = "";
            String font = "";

            String fontfamily = "";
            String filepath = "";
            JsonObject fillColorJson = null;
            JsonObject outlineColorJson = null;
            double outlineThickness = 0.0;

            double fontsize = 0.0;
            if (type.equals("TEXT")) {
                // DraggableText d = (DraggableText)shape.;
                text = ((DraggableText) shape).getText().toString();
                font = ((DraggableText) shape).getFont().getName();
                //font.replace(" ", "*");
                //font = tempfont.replace(" ", "*");
                //System.out.println(text + " text");
                System.out.println(font + " newfont saved");

                fontfamily = ((DraggableText) shape).getFont().getFamily();
                // fontfamily = tempfontfamily.replace(' ', '*');
                System.out.println(fontfamily + " newfontfamily");
                fontsize = ((DraggableText) shape).getFont().getSize();
            }

            double x = draggableShape.getX();
            double y = draggableShape.getY();
            double width = draggableShape.getWidth();
            double height = draggableShape.getHeight();
            if (type.equals("IMAGE")) {
                DraggableRectangle d = (DraggableRectangle) shape;
                fillColorJson = makeJsonColorObject(Color.web("#000000")); // random
                outlineColorJson = makeJsonColorObject(Color.web("#000000"));
                outlineThickness = 0;
                filepath = d.getfilepath();
            } else {
                fillColorJson = makeJsonColorObject((Color) shape.getFill());
                outlineColorJson = makeJsonColorObject((Color) shape.getStroke());
                outlineThickness = shape.getStrokeWidth();
            }

            JsonObject shapeJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, type)
                    .add(JSON_TEXT, text)
                    .add(JSON_FONT, font)
                    .add(JSON_FONTFAMILY, fontfamily)
                    .add(JSON_FONTSIZE, fontsize)
                    .add(JSON_X, x)
                    .add(JSON_Y, y)
                    .add(JSON_WIDTH, width)
                    .add(JSON_HEIGHT, height)
                    .add(JSON_FILEPATH, filepath)
                    .add(JSON_FILL_COLOR, fillColorJson)
                    .add(JSON_OUTLINE_COLOR, outlineColorJson)
                    .add(JSON_OUTLINE_THICKNESS, outlineThickness).build();

            arrayBuilder.add(shapeJson);
        }
        JsonArray shapesArray = arrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_BG_COLOR, bgColorJson)
                .add(JSON_SHAPES, shapesArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }

    private JsonObject makeJsonColorObject(Color color) {
        JsonObject colorJson = Json.createObjectBuilder()
                .add(JSON_RED, color.getRed())
                .add(JSON_GREEN, color.getGreen())
                .add(JSON_BLUE, color.getBlue())
                .add(JSON_ALPHA, color.getOpacity()).build();
        return colorJson;
    }

    /**
     * This method loads data from a JSON formatted file into the data
     * management component and then forces the updating of the workspace such
     * that the user may edit the data.
     *
     * @param data Data management component where we'll load the file into.
     *
     * @param filePath Path (including file name/extension) to where to load the
     * data from.
     *
     * @throws IOException Thrown should there be an error reading in data from
     * the file.
     */
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

    private double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigDecimalValue().doubleValue();
    }

    private String getDataAsStr(JsonObject json, String dataName) {
        String s = json.get(dataName).toString();
        return s;

    }

    private Shape loadShape(JsonObject jsonShape) {
        // FIRST BUILD THE PROPER SHAPE TYPE
        String type = jsonShape.getString(JSON_TYPE);

        System.out.println(type + "type");
        Shape shape = null;
        if (type.equals("RECT") || type.equals("IMAGE")) {
            shape = new DraggableRectangle();

        } else if (type.equals("ELLIP")) {
            shape = new DraggableEllipse();
        } else if (type.equals("TEXT")) {
            shape = new DraggableText();
            System.out.println(type + " type");
        } else {
            System.out.println("loading shape null -- never happens");
        }
        shape.setUserData(type);

        // THEN LOAD ITS FILL AND OUTLINE PROPERTIES
        if (!shape.getUserData().equals("IMAGE")) {
            Color fillColor = loadColor(jsonShape, JSON_FILL_COLOR);
            Color outlineColor = loadColor(jsonShape, JSON_OUTLINE_COLOR);
            double outlineThickness = getDataAsDouble(jsonShape, JSON_OUTLINE_THICKNESS);
            shape.setFill(fillColor);
            shape.setStroke(outlineColor);
            shape.setStrokeWidth(outlineThickness);
        }

        // AND THEN ITS DRAGGABLE PROPERTIES
        double x = getDataAsDouble(jsonShape, JSON_X);
        double y = getDataAsDouble(jsonShape, JSON_Y);
        double width = getDataAsDouble(jsonShape, JSON_WIDTH);
        double height = getDataAsDouble(jsonShape, JSON_HEIGHT);
        Draggable draggableShape = (Draggable) shape;
        draggableShape.setLocationAndSize(x, y, width, height);

        if (shape.getUserData().equals("IMAGE")) {
            DraggableRectangle d = (DraggableRectangle) shape;

            String filepath = jsonShape.getString(JSON_FILEPATH);
            Image image = new Image(filepath);

            d.setFill(new ImagePattern(image));
            d.setUserData("IMAGE");

        }
        // STR
        if (shape.getUserData().equals("TEXT")) {
            String text = jsonShape.getString(JSON_TEXT);
            System.out.println(text + " text");
            String font = getDataAsStr(jsonShape, JSON_FONT);
            //String tempfont = jsonShape.getString(JSON_FONT);
            //System.out.println(tempfont + "tempfont");

            //String font = tempfont.replace("*", " ");
            //System.out.println(font + " font loaded");
            String fontfamily = jsonShape.getString(JSON_FONTFAMILY);
            // String fontfamily = tempfontfamily.replace('*', ' ');
            double fontsize = getDataAsDouble(jsonShape, JSON_FONTSIZE);

            if (!text.equals("")) { // fontsize
                DraggableText draggableText = (DraggableText) shape;
                Text text2 = new Text(text);
                draggableText.setText(text2.getText());

                FontWeight fontweight = FontWeight.NORMAL;

                //.getName() contains font name, Bold, Italic and etc
                if (font.contains("Bold")) { // keep Bold
                    fontweight = FontWeight.EXTRA_BOLD;
                }

                FontPosture fontposture = FontPosture.REGULAR;
                if (font.contains("Italic")) { // keep Italic
                    fontposture = FontPosture.ITALIC;
                }

                draggableText.setFont(Font.font(fontfamily, fontweight, fontposture, fontsize));

                draggableText.setUserData("TEXT"); // one more time

            }
        }

        // ALL DONE, RETURN IT
        return shape;
    }

    private Color loadColor(JsonObject json, String colorToGet) {
        JsonObject jsonColor = json.getJsonObject(colorToGet);
        double red = getDataAsDouble(jsonColor, JSON_RED);
        double green = getDataAsDouble(jsonColor, JSON_GREEN);
        double blue = getDataAsDouble(jsonColor, JSON_BLUE);
        double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
        Color loadedColor = new Color(red, green, blue, alpha);
        return loadedColor;
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    /**
     * This method is provided to satisfy the compiler, but it is not used by
     * this application.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        // WE ARE NOT USING THIS, THOUGH PERHAPS WE COULD FOR EXPORTING
        // IMAGES TO VARIOUS FORMATS, SOMETHING OUT OF THE SCOPE
        // OF THIS ASSIGNMENT
    }

    /**
     * This method is provided to satisfy the compiler, but it is not used by
     * this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        // AGAIN, WE ARE NOT USING THIS IN THIS ASSIGNMENT
    }
}
