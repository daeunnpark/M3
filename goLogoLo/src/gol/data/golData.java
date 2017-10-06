package gol.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import static gol.data.golState.SELECTING_SHAPE;
import static gol.data.golState.SIZING_SHAPE;
import gol.gui.golWorkspace;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import static gol.data.Draggable.RECTANGLE;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.json.JsonObject;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class golData implements AppDataComponent {
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES

    // THESE ARE THE SHAPES TO DRAW
    ObservableList<Node> shapes;

    // THE BACKGROUND COLOR
    Color backgroundColor;

    // AND NOW THE EDITING DATA
    // THIS IS THE SHAPE CURRENTLY BEING SIZED BUT NOT YET ADDED
    Shape newShape;

    // THIS IS THE SHAPE CURRENTLY SELECTED
    Shape selectedShape;

    // FOR FILL AND OUTLINE
    Color currentFillColor;
    Color currentOutlineColor;
    double currentBorderWidth;

    // CURRENT STATE OF THE APP
    golState state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;

    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;

    //FONT
    String Fontweight, Fontposture;

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public golData(AppTemplate initApp) {
        // KEEP THE APP FOR LATER
        app = initApp;

        // NO SHAPE STARTS OUT AS SELECTED
        newShape = null;
        selectedShape = null;

        // INIT THE COLORS
        currentFillColor = Color.web(WHITE_HEX);
        currentOutlineColor = Color.web(BLACK_HEX);
        currentBorderWidth = 1;

        // THIS IS FOR THE SELECTED SHAPE
        DropShadow dropShadowEffect = new DropShadow();
        dropShadowEffect.setOffsetX(0.0f);
        dropShadowEffect.setOffsetY(0.0f);
        dropShadowEffect.setSpread(1.0);
        dropShadowEffect.setColor(Color.YELLOW);
        dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
        dropShadowEffect.setRadius(15);
        highlightedEffect = dropShadowEffect;
    }

    public ObservableList<Node> getShapes() {
        return shapes;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getCurrentFillColor() {
        return currentFillColor;
    }

    public Color getCurrentOutlineColor() {
        return currentOutlineColor;
    }

    public double getCurrentBorderWidth() {
        return currentBorderWidth;
    }

    public void setShapes(ObservableList<Node> initShapes) {
        shapes = initShapes;
    }

    public void setBackgroundColor(Color initBackgroundColor) {
        backgroundColor = initBackgroundColor;
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
        Background background = new Background(fill);
        canvas.setBackground(background);
    }

    public void setCurrentFillColor(Color initColor) {
        currentFillColor = initColor;
        if (selectedShape != null) {
            selectedShape.setFill(currentFillColor);
        }
    }

    public void setCurrentOutlineColor(Color initColor) {
        currentOutlineColor = initColor;
        if (selectedShape != null) {
            selectedShape.setStroke(initColor);
        }
    }

    public void setCurrentOutlineThickness(int initBorderWidth) {
        currentBorderWidth = initBorderWidth;
        if (selectedShape != null) {
            selectedShape.setStrokeWidth(initBorderWidth);
        }
    }

    public void removeSelectedShape() {
        if (selectedShape != null) {
            shapes.remove(selectedShape);
            selectedShape = null;
        }
    }

    public void moveSelectedShapeToBack() {
        if (selectedShape != null) {
            shapes.remove(selectedShape);
            if (shapes.isEmpty()) {
                shapes.add(selectedShape);
            } else {
                ArrayList<Node> temp = new ArrayList<>();
                temp.add(selectedShape);
                for (Node node : shapes) {
                    temp.add(node);
                }
                shapes.clear();
                for (Node node : temp) {
                    shapes.add(node);
                }
            }
        }
    }

    public void moveSelectedShapeToFront() {
        if (selectedShape != null) {
            shapes.remove(selectedShape);
            shapes.add(selectedShape);
        }
    }

    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void resetData() {
        setState(SELECTING_SHAPE);
        newShape = null;
        selectedShape = null;

        // INIT THE COLORS
        currentFillColor = Color.web(WHITE_HEX);
        currentOutlineColor = Color.web(BLACK_HEX);

        shapes.clear();
        ((golWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().clear();
    }

    public void selectSizedShape() {
        if (selectedShape != null) {
            unhighlightShape(selectedShape);
        }
        selectedShape = newShape;
        highlightShape(selectedShape);
        newShape = null;
        if (state == SIZING_SHAPE) {
            state = ((Draggable) selectedShape).getStartingState();
        }
    }

    public void unhighlightShape(Shape shape) {
        selectedShape.setEffect(null);
    }

    public void highlightShape(Shape shape) {
        shape.setEffect(highlightedEffect);
    }

    public void startNewRectangle(int x, int y) {
        DraggableRectangle newRectangle = new DraggableRectangle();
        //newRectangle.setUserData("Rect");
        newRectangle.start(x, y);
        newRectangle.setUserData("RECT");
        newShape = newRectangle;
        initNewShape();
    }

    public void startNewEllipse(int x, int y) {
        DraggableEllipse newEllipse = new DraggableEllipse();
        // newEllipse.setUserData("Elli");
        newEllipse.start(x, y);
        newEllipse.setUserData("ELLIP");
        newShape = newEllipse;
        initNewShape();
    }

    public void startNewImage(double height, double width, Image image) {
        DraggableRectangle newRectangle = new DraggableRectangle();
        //DraggableEllipse newEllipse = new DraggableEllipse();
        newRectangle.setHeight(height);
        newRectangle.setWidth(width);
        newRectangle.setFill(new ImagePattern(image));
        newRectangle.setUserData("IMAGE");

        newShape = newRectangle;
        initNewShape();
    }
    // start 
    public void makeNewTextBox(String s) {

        DraggableText text = new DraggableText();
        text.setText(s);
        text.setUserData("TEXT");
        newShape = text;
        text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 200)); // Default
        //state = golState.SELECTING_SHAPE;

        initNewShape();
    }

    public void changeTextBox(String news) {
        if (selectedShape != null) {
            DraggableText text = (DraggableText) selectedShape;
            text.setText(news);
        }
        state = golState.SELECTING_SHAPE;

    }

    public void initNewShape() {
        // DESELECT THE SELECTED SHAPE IF THERE IS ONE
        if (selectedShape != null) {
            unhighlightShape(selectedShape);
            selectedShape = null;
        }

        // USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        //newShape.setFill(workspace.getFillColorPicker().getValue());                      // FOR IMG AND TEXT
        //newShape.setStroke(workspace.getOutlineColorPicker().getValue());
        //newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());

        // ADD THE SHAPE TO THE CANVAS
        shapes.add(newShape);

        if (newShape.getUserData() == null) {// Rect or Elip
            newShape.setFill(workspace.getFillColorPicker().getValue());
            newShape.setStroke(workspace.getOutlineColorPicker().getValue());
            newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());

            state = golState.SIZING_SHAPE;

        } else { // Image of Text
            state = golState.SELECTING_SHAPE;
        }

    }

    public void initNewImage() {
        // DESELECT THE SELECTED SHAPE IF THERE IS ONE
        if (selectedShape != null) {
            unhighlightShape(selectedShape);
            selectedShape = null;
        }

        // USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        //newShape.setFill(workspace.getFillColorPicker().getValue());
        newShape.setStroke(workspace.getOutlineColorPicker().getValue());
        newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());

        // ADD THE SHAPE TO THE CANVAS
        shapes.add(newShape);

        // GO INTO SHAPE SIZING MODE
        state = golState.SELECTING_SHAPE;

    }

    public Shape getNewShape() {
        return newShape;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape initSelectedShape) {
        selectedShape = initSelectedShape;
    }

    public Shape selectTopShape(int x, int y) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        Shape shape = getTopShape(x, y);
        if (shape == selectedShape) {
            return shape;
        }

        if (selectedShape != null) {
            unhighlightShape(selectedShape);
        }

        if (shape != null) {
            highlightShape(shape);

        }
        selectedShape = shape; //// SET AFTER THIS 

        if (shape.getUserData() == null) { // Rect / Elip
            workspace.loadSelectedShapeSettings(shape);

        } else if (shape.getUserData().equals("TEXT")) {
            workspace.loadSelectedTextSettings(shape);
        }
        
        
        if (shape != null) {
            ((Draggable) shape).start(x, y);
        }

        return shape;
    }

    public Shape getTopShape(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = (Shape) shapes.get(i);
            if (shape.contains(x, y)) {
                return shape;
            }
        }
        return null;
    }

    public void getBolded(Shape shape) { //, boolean b) {
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

    public void getItalicized(Shape shape) {
        DraggableText text = (DraggableText) shape;

        FontWeight fontweight = FontWeight.NORMAL;
        if (text.getFont().getName().contains("Bold")) { // kepp Bold
            fontweight = FontWeight.EXTRA_BOLD;
        }

        FontPosture fontposture = FontPosture.REGULAR;
        if (!text.getFont().getName().contains("Italic")) { // add Italic
            fontposture = FontPosture.ITALIC;
        }
        text.setFont(Font.font(text.getFont().getFamily(), fontweight, fontposture, text.getFont().getSize()));
    }

    public void changefont(Shape shape, String newfont) {
        DraggableText text = (DraggableText) shape;

        FontWeight fontweight = FontWeight.NORMAL;
        if (text.getFont().getName().contains("Bold")) { //keep bold
            fontweight = FontWeight.EXTRA_BOLD;
        }

        FontPosture fontposture = FontPosture.REGULAR;
        if (text.getFont().getName().contains("Italic")) { // keep italic  
            fontposture = FontPosture.ITALIC;
        }
        text.setFont(Font.font(newfont, fontweight, fontposture, text.getFont().getSize()));

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        // workspace.loadSelectedTextSettings(shape);
    }

    public void changesize(Shape shape, double n) {
        DraggableText text = (DraggableText) shape;

        FontWeight fontweight = FontWeight.NORMAL;
        if (text.getFont().getName().contains("Bold")) {
            fontweight = FontWeight.EXTRA_BOLD;

        }
        FontPosture fontposture = FontPosture.REGULAR;
        if (text.getFont().getName().contains("Italic")) {
            fontposture = FontPosture.ITALIC;
        }
        text.setFont(Font.font(text.getFont().getFamily(), fontweight, fontposture, n));
    }

    public Shape cloneShape(Shape shape) {
        Shape finalclone = null;
        Color n = (Color) shape.getFill();
        Color fillColor2 = new Color(n.getRed(), n.getGreen(), n.getBlue(), n.getOpacity());

        Color m = (Color) shape.getStroke();
        Color stroke = new Color(m.getRed(), m.getGreen(), m.getBlue(), m.getOpacity());

        double strokeWidth = shape.getStrokeWidth();

        if (shape.getUserData().equals("RECT")) {
            DraggableRectangle original = (DraggableRectangle) shape;
            DraggableRectangle clone = new DraggableRectangle();
            double width = original.getWidth();
            double height = original.getHeight();

            clone.setFill(fillColor2);
            clone.setStroke(stroke);
            clone.setStrokeWidth(strokeWidth);
            clone.setWidth(width);
            clone.setHeight(height);
            finalclone = clone;

        } else if (shape.getUserData().equals("ELLIP")) {
            DraggableEllipse original = (DraggableEllipse) shape;
            DraggableEllipse clone = new DraggableEllipse();
            double width = original.getRadiusX();
            double height = original.getRadiusY();

            clone.setFill(fillColor2);
            clone.setStroke(stroke);
            clone.setStrokeWidth(strokeWidth);
            clone.setRadiusX(width);
            clone.setRadiusY(height);
            finalclone = clone;

        } else if (shape.getUserData().equals("TEXT")) {
            DraggableText original = (DraggableText) shape;
            //DraggableText clone = new DraggableText();

            finalclone = cloneText(original);
        }
        /*
        else if (shape.getUserData().equals("IMAGE")){
            DraggableEllipse original = (DraggableEllipse) shape;
            DraggableEllipse clone = new DraggableEllipse ();
        }
         */
        
        return finalclone;

    }

    /**
     * Make a deep cloned copy of original
     *
     * @param original
     * @return cloned DraggableText
     */
    public DraggableText cloneText(DraggableText original) {

        DraggableText clone = new DraggableText();

        clone.setText(original.getText());
        //clone.setFont(original.getFont()); // maybe getfamily

        // BOLD
        FontWeight fontweight = FontWeight.NORMAL;
        //golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        //.getName() contains font name, Bold, Italic and etc
        if (original.getFont().getName().contains("Bold")) { //
            fontweight = FontWeight.EXTRA_BOLD;
        }

        FontPosture fontposture = FontPosture.REGULAR;
        if (original.getFont().getName().contains("Italic")) { //
            fontposture = FontPosture.ITALIC;
        }

        clone.setFont(Font.font(original.getFont().getFamily(), fontweight, fontposture, original.getFont().getSize()));

        return clone;

    }

    public void addShape(Shape shapeToAdd) {
        shapes.add(shapeToAdd);
    }

    public void removeShape(Shape shapeToRemove) {
        shapes.remove(shapeToRemove);
    }

    public golState getState() {
        return state;
    }

    public void setState(golState initState) {
        state = initState;
    }

    public boolean isInState(golState testState) {
        return state == testState;
    }

    
}
