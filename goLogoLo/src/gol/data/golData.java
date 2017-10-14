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
import gol.jTPS;
import java.io.PrintStream;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import gol.jTPS_Transaction;

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

    static jTPS jTPS = new jTPS();
    //static PrintStream out = System.out;
    //static Scanner input = new Scanner(System.in);
    //static Num num = new Num();

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
        backgroundColor = Color.web(WHITE_HEX);
        currentFillColor = Color.web(WHITE_HEX);
        currentOutlineColor = Color.web(BLACK_HEX);
        currentBorderWidth = 6;                             // to change

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

    /*
    public Color getCurrentFillColor2() { // mine , of selectedshap

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        currentFillColor = workspace.getFillColorPicker().getValue();

        return currentFillColor;
    }
     */
    public Color getCurrentOutlineColor() {
        return currentOutlineColor;
    }

    /*
    public Color getCurrentOutlineColor2() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        currentOutlineColor = workspace.getOutlineColorPicker().getValue();
        return currentOutlineColor;
    }
     */
    public double getCurrentBorderWidth() {

        return currentBorderWidth;
    }
    // in slider

    public int getCurrentBorderWidth2() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        int thickness = (int) workspace.getOutlineThicknessSlider().getValue();
        return thickness;
    }

    /*
    public int getCurrentOutlineThickness2() { // mine
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        return (int) workspace.getOutlineThicknessSlider().getValue();
    }
     */
    public void setShapes(ObservableList<Node> initShapes) {
        shapes = initShapes;
    }

    public void setBackgroundColor2(Color initBackgroundColor) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        backgroundColor = initBackgroundColor;

        Pane canvas = workspace.getCanvas();

        BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
        Background background = new Background(fill);
        canvas.setBackground(background);

    }

    public void setBackgroundColor(Color initBackgroundColor) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            //System.out.println("SET BGD");
            jTPS_Transaction transaction = new jTPS_Transaction(this, "setBackgroundColor", getBackgroundColor(), initBackgroundColor, null);
            jTPS.addTransaction(transaction);
        }
        backgroundColor = initBackgroundColor;

        Pane canvas = workspace.getCanvas();

        BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
        Background background = new Background(fill);
        canvas.setBackground(background);

    }

    public void setCurrentFillColor(Shape shape, Color initColor) { // added shape to pass as parameter to UNDO/REDO function
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;
        /*
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
           
            jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentFillColor", selectedShape.getFill(), initColor, selectedShape);
            jTPS.addTransaction(transaction);

        }
         */

        currentFillColor = initColor;
        if (selectedShape != null) {

            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentFillColor", selectedShape.getFill(), initColor, selectedShape);
                jTPS.addTransaction(transaction);
            }
            // selectedShape = shape;
            selectedShape.setFill(currentFillColor);
        } else {
            System.out.println("SHAPE IS NULL from fill color");
        }

    }

    public void setCurrentOutlineColor(Shape shape, Color initColor) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;

        currentOutlineColor = initColor;
        if (selectedShape != null) {
            if (!workspace.getredobtn() && !workspace.getundobtn()) {

                jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentOutlineColor", shape.getStroke(), initColor, selectedShape);
                jTPS.addTransaction(transaction);
            }
            selectedShape.setStroke(initColor);
        } else {
            System.out.println("SHAPE IS NULL from outline color");
        }
    }

    public void setCurrentOutlineThickness(Shape shape, int initBorderWidth) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;

        currentBorderWidth = initBorderWidth;
        if (selectedShape != null) {
            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentOutlineThickness", (int) shape.getStrokeWidth(), initBorderWidth, shape);
                jTPS.addTransaction(transaction);
            }
            selectedShape.setStrokeWidth(initBorderWidth);
        } else {
            //  System.out.println("SHAPE IS NULL from outline THIcK ");
        }
    }

    // Not adding to undo redo
    public void removeSelectedShape() {

        if (selectedShape != null) {
            shapes.remove(selectedShape);
            selectedShape = null;
        }
    }

    public void moveSelectedShapeToBack(Shape shape) { // selectedShape as a param?
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;

        if (selectedShape != null) {
            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "moveSelectedShapeToBack", null, null, selectedShape);
                jTPS.addTransaction(transaction);
            }
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

    public void moveSelectedShapeToFront(Shape shape) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;

        if (selectedShape != null) {
            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "moveSelectedShapeToFront", null, null, selectedShape);
                jTPS.addTransaction(transaction);
            }
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
        newRectangle.start(x, y);
        newRectangle.setUserData("RECT");
        newShape = newRectangle;
        initNewShape();
    }

    public void startNewEllipse(int x, int y) {
        DraggableEllipse newEllipse = new DraggableEllipse();
        newEllipse.start(x, y);
        newEllipse.setUserData("ELLIP");
        newShape = newEllipse;
        initNewShape();
    }

    public void startNewImage(String filepath, double height, double width, Image image) {
        DraggableRectangle newRectangle = new DraggableRectangle();
        newRectangle.setHeight(height);
        newRectangle.setWidth(width);
        newRectangle.setFill(new ImagePattern(image));
        newRectangle.setUserData("IMAGE");
        newRectangle.setfilepath(filepath);

        newShape = newRectangle;
        initNewShape();
    }

    public void makeNewTextBox(String s) {

        DraggableText text = new DraggableText();
        text.setText(s);
        text.setUserData("TEXT");
        newShape = text;
        text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 200)); // Default

        initNewShape();
    }

    public void changeTextBox(Shape shape, String news) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;
        if (selectedShape != null) {
            DraggableText text = (DraggableText) selectedShape;

            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "changeTextBox", text.getText(), news, selectedShape);
                jTPS.addTransaction(transaction);
            }
            text.setText(news);
        }

    }

    public void initNewShape() {
        // DESELECT THE SELECTED SHAPE IF THERE IS ONE
        if (selectedShape != null) {
            unhighlightShape(selectedShape);
            selectedShape = null;
        }

        // USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        // ADD THE SHAPE TO THE CANVAS
        if (newShape != null) {
            shapes.add(newShape);

            if (newShape.getUserData() != null) {

                if (newShape.getUserData().equals("RECT") || newShape.getUserData().equals("ELLIP")
                        || newShape.getUserData().equals("TEXT")) { // Rect, Elip, Text
                    newShape.setFill(workspace.getFillColorPicker().getValue());
                    newShape.setStroke(workspace.getOutlineColorPicker().getValue());
                    //newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());
                    newShape.setStrokeWidth(getCurrentBorderWidth2()); //same thing with above line
                }

                if (!workspace.getredobtn() && !workspace.getundobtn()) {
                    jTPS_Transaction transaction = new jTPS_Transaction(this, "newShape", null, null, newShape);
                    jTPS.addTransaction(transaction);
                }
                state = golState.SIZING_SHAPE;

                if (newShape.getUserData().equals("IMAGE") || newShape.getUserData().equals("TEXT")) { // Image of Text
                    state = golState.SELECTING_SHAPE;
                }
            }
        }
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

    public void setSelectedShape2(Shape initSelectedShape) {
        if (selectedShape != null) {
            unhighlightShape(selectedShape);
        }
        selectedShape = initSelectedShape;
        highlightShape(selectedShape);
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

        if (shape != null) {
            if (shape.getUserData() != null) {
                workspace.loadSelectedShapeSettings(shape);
            }
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

    public void getBolded(Shape shape) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        if (shape != null) {
            DraggableText text = (DraggableText) shape;

            FontWeight fontweight = FontWeight.NORMAL;
            //.getName() contains font name, Bold, Italic and etc
            if (!text.getFont().getName().contains("Bold")) { // to Bold
                fontweight = FontWeight.EXTRA_BOLD;
            }

            FontPosture fontposture = FontPosture.REGULAR;
            if (text.getFont().getName().contains("Italic")) { // keep italic
                fontposture = FontPosture.ITALIC;
            }

            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "getBolded", null, null, shape);
                jTPS.addTransaction(transaction);
            }

            text.setFont(Font.font(text.getFont().getFamily(), fontweight, fontposture, text.getFont().getSize()));
           
        }
    }

    public void getItalicized(Shape shape) {

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        if (shape != null) {
            DraggableText text = (DraggableText) shape;

            FontWeight fontweight = FontWeight.NORMAL;
            if (text.getFont().getName().contains("Bold")) { // kepp Bold
                fontweight = FontWeight.EXTRA_BOLD;
            }

            FontPosture fontposture = FontPosture.REGULAR;
            if (!text.getFont().getName().contains("Italic")) { // add Italic
                fontposture = FontPosture.ITALIC;
            }

            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "getItalicized", null, null, shape);
                jTPS.addTransaction(transaction);
            }
            text.setFont(Font.font(text.getFont().getFamily(), fontweight, fontposture, text.getFont().getSize()));
        }
    }

    public void changefont(Shape shape, String newfont) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        if (shape != null) {
            DraggableText text = (DraggableText) shape;

            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "changefont", text.getFont().getFamily(), newfont, shape);
                jTPS.addTransaction(transaction);
            }

            FontWeight fontweight = FontWeight.NORMAL;
            if (text.getFont().getName().contains("Bold")) { // keep bold
                fontweight = FontWeight.EXTRA_BOLD;
            }

            FontPosture fontposture = FontPosture.REGULAR;
            if (text.getFont().getName().contains("Italic")) { // keep italic  
                fontposture = FontPosture.ITALIC;
            }
            text.setFont(Font.font(newfont, fontweight, fontposture, text.getFont().getSize()));
        }
    }

    public void changesize(Shape shape, double n) {

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        if (shape != null) {
            DraggableText text = (DraggableText) shape;
            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "changesize", text.getFont().getSize(), n, shape);
                jTPS.addTransaction(transaction);
            }

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
    }

    public Shape copyShape(Shape shape) {
        Shape finalclone = null;
        Color fillColor2 = null;
        Color stroke = null;
        double strokeWidth = 0;

        if (shape != null) {
            if (shape.getUserData().equals("RECT") || shape.getUserData().equals("ELLIP")
                    || shape.getUserData().equals("TEXT")) {
                Color n = (Color) shape.getFill();
                fillColor2 = new Color(n.getRed(), n.getGreen(), n.getBlue(), n.getOpacity());

                Color m = (Color) shape.getStroke();
                stroke = new Color(m.getRed(), m.getGreen(), m.getBlue(), m.getOpacity());

                strokeWidth = shape.getStrokeWidth();
            }

            if (shape.getUserData().equals("RECT")) {
                DraggableRectangle original = (DraggableRectangle) shape;
                DraggableRectangle clone = new DraggableRectangle();

                clone.setFill(fillColor2);
                clone.setStroke(stroke);
                clone.setStrokeWidth(strokeWidth);
                clone.setWidth(original.getWidth());
                clone.setHeight(original.getHeight());
                clone.setUserData("RECT");
                finalclone = clone;

            } else if (shape.getUserData().equals("ELLIP")) {
                DraggableEllipse original = (DraggableEllipse) shape;
                DraggableEllipse clone = new DraggableEllipse();

                clone.setFill(fillColor2);
                clone.setStroke(stroke);
                clone.setStrokeWidth(strokeWidth);
                clone.setRadiusX(original.getRadiusX());
                clone.setRadiusY(original.getRadiusY());
                clone.setUserData("ELLIP");
                finalclone = clone;

            } else if (shape.getUserData().equals("TEXT")) { // USER DATA BASE TO ADD!!
                DraggableText original = (DraggableText) shape;

                DraggableText clone = new DraggableText();
                Text text = new Text(original.getText());

                clone.setText(text.getText());
                //clone.setFont(original.getFont()); // maybe getfamily

                // BOLD
                FontWeight fontweight = FontWeight.NORMAL;

                //.getName() contains font name, Bold, Italic and etc
                if (original.getFont().getName().contains("Bold")) { // keep Bold
                    fontweight = FontWeight.EXTRA_BOLD;
                }

                FontPosture fontposture = FontPosture.REGULAR;
                if (original.getFont().getName().contains("Italic")) { // keep Italic
                    fontposture = FontPosture.ITALIC;
                }

                clone.setFont(Font.font(original.getFont().getFamily(), fontweight, fontposture, original.getFont().getSize()));

                clone.setFill(fillColor2);
                clone.setStroke(stroke);
                clone.setStrokeWidth(strokeWidth);
                clone.setUserData("TEXT");
                finalclone = clone;

            } else if (shape.getUserData().equals("IMAGE")) {

                DraggableRectangle original = (DraggableRectangle) shape;
                DraggableRectangle clone = new DraggableRectangle();

                clone.setFill(original.getFill());

                clone.setWidth(original.getWidth());
                clone.setHeight(original.getHeight());

                clone.setUserData("IMAGE");
                finalclone = clone;

            }

            golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                Shape currentcopy = workspace.getCanvasController().getCopy();
                jTPS_Transaction transaction = new jTPS_Transaction(this, "copyShape", currentcopy, finalclone, shape);
                jTPS.addTransaction(transaction);
            }
        }
//        System.out.println(finalclone.toString() + " SHAPE CLONED");
        return finalclone;
    }

    public Shape cutShape(Shape shape) {
        Shape finalclone = null;
        Color fillColor2 = null;
        Color stroke = null;
        double strokeWidth = 0;

        if (shape != null) {
            if (shape.getUserData().equals("RECT") || shape.getUserData().equals("ELLIP")
                    || shape.getUserData().equals("TEXT")) {
                Color n = (Color) shape.getFill();
                fillColor2 = new Color(n.getRed(), n.getGreen(), n.getBlue(), n.getOpacity());

                Color m = (Color) shape.getStroke();
                stroke = new Color(m.getRed(), m.getGreen(), m.getBlue(), m.getOpacity());

                strokeWidth = shape.getStrokeWidth();
            }

            if (shape.getUserData().equals("RECT")) {
                DraggableRectangle original = (DraggableRectangle) shape;
                DraggableRectangle clone = new DraggableRectangle();

                clone.setFill(fillColor2);
                clone.setStroke(stroke);
                clone.setStrokeWidth(strokeWidth);
                clone.setWidth(original.getWidth());
                clone.setHeight(original.getHeight());
                clone.setUserData("RECT");
                finalclone = clone;

            } else if (shape.getUserData().equals("ELLIP")) {
                DraggableEllipse original = (DraggableEllipse) shape;
                DraggableEllipse clone = new DraggableEllipse();

                clone.setFill(fillColor2);
                clone.setStroke(stroke);
                clone.setStrokeWidth(strokeWidth);
                clone.setRadiusX(original.getRadiusX());
                clone.setRadiusY(original.getRadiusY());
                clone.setUserData("ELLIP");
                finalclone = clone;

            } else if (shape.getUserData().equals("TEXT")) { // USER DATA BASE TO ADD!!
                DraggableText original = (DraggableText) shape;;

                DraggableText clone = new DraggableText();
                System.out.println(clone.getX() + " and " + clone.getY() + "x and Y from cut");
                Text text = new Text(original.getText());

                clone.setText(text.getText());
                //clone.setFont(original.getFont()); // maybe getfamily

                // BOLD
                FontWeight fontweight = FontWeight.NORMAL;

                //.getName() contains font name, Bold, Italic and etc
                if (original.getFont().getName().contains("Bold")) { // keep Bold
                    fontweight = FontWeight.EXTRA_BOLD;
                }

                FontPosture fontposture = FontPosture.REGULAR;
                if (original.getFont().getName().contains("Italic")) { // keep Italic
                    fontposture = FontPosture.ITALIC;
                }

                clone.setFont(Font.font(original.getFont().getFamily(), fontweight, fontposture, original.getFont().getSize()));

                clone.setFill(fillColor2);
                clone.setStroke(stroke);
                clone.setStrokeWidth(strokeWidth);
                clone.setUserData("TEXT");
                finalclone = clone;

            } else if (shape.getUserData().equals("IMAGE")) {

                DraggableRectangle original = (DraggableRectangle) shape;
                DraggableRectangle clone = new DraggableRectangle();

                clone.setFill(original.getFill());

                clone.setWidth(original.getWidth());
                clone.setHeight(original.getHeight());

                clone.setUserData("IMAGE");
                finalclone = clone;

            }
            shapes.remove(shape); // CUT

            golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                Shape currentcopy = workspace.getCanvasController().getCopy();
                jTPS_Transaction transaction = new jTPS_Transaction(this, "cutShape", currentcopy, finalclone, shape);
                jTPS.addTransaction(transaction);
            }
        }
        // System.out.println(finalclone.toString() + " SHAPE CLONED for cut");
        return finalclone;
    }

    public void pasteShape(Shape shape) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        if (!workspace.getredobtn() && !workspace.getundobtn()) {

            // System.out.println(currentcopy.toString() + " currentcopy");
            jTPS_Transaction transaction = new jTPS_Transaction(this, "pasteShape", null, null, shape);
            jTPS.addTransaction(transaction);
        }

        if (shape != null) {
            shapes.add(shape);
            //System.out.println(shapes.size() + " shapes HERE");

            Draggable d = (Draggable) shapes.get(shapes.size() - 1);
            Draggable s = (Draggable) shape;
            //d.setXY(0, 0);
            
            d.setLocationAndSize(s.getX(), s.getY(), s.getWidth(), s.getHeight());
            if (shape.getUserData().equals("TEXT")) {
                //d.setXY(250, 400);
                // System.out.println( d.getX() + " x "+ d.getY() + " y HHHH");
                //  System.out.println("TEXT ALREADY");
            }
            // location
            // System.out.println(shape.toString() + "SHAPE PASTED");
            state = SELECTING_SHAPE;
        }
    }

    public void addShape(Shape shapeToAdd) {
        if (shapeToAdd != null) {
            shapes.add(shapeToAdd);
        }
    }

    public void removeShape(Shape shapeToRemove) {

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        // add null
        if (shapeToRemove != null) {
            selectedShape = shapeToRemove;
            if (!workspace.getredobtn() && !workspace.getundobtn()) {

                jTPS_Transaction transaction = new jTPS_Transaction(this, "removeShape", null, null, selectedShape);
                jTPS.addTransaction(transaction);
            }

            shapes.remove(shapeToRemove);

        }
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

    public void undoTransaction() {
        System.out.println("undo11");
        System.out.println(jTPS.toString());
        jTPS.undoTransaction();
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.resetundobtn();
    }

    public void redoTransaction() {
        System.out.println("redo22");
        System.out.println(jTPS.toString());
        jTPS.redoTransaction();
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.resetredobtn();

    }

    public void setCopy(Shape copy) { // allows access from other methods
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        workspace.getCanvasController().setCopy(copy);

    }

    public Shape getCopy() { // allows access from other methods
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        return workspace.getCanvasController().getCopy();
    }

    public void reloadworkspace2(boolean b) {
        //golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        workspace.reloadWorkspace2(b);
    }
    
    public void reloadworkspace3(boolean b) {
        //golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        workspace.reloadWorkspace3(b);
    }
}
