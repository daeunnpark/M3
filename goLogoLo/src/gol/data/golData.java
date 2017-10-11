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
    static PrintStream out = System.out;
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

    public Color getCurrentFillColor2() { // mine , of selectedshap

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();

        currentFillColor = workspace.getFillColorPicker().getValue();

        return currentFillColor;
    }

    public Color getCurrentOutlineColor() {
        return currentOutlineColor;
    }

    public Color getCurrentOutlineColor2() {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        currentOutlineColor = workspace.getOutlineColorPicker().getValue();
        return currentOutlineColor;
    }

    public double getCurrentBorderWidth() {
        return currentBorderWidth;
    }

    public int getCurrentOutlineThickness2() { // mine
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        return (int) workspace.getOutlineThicknessSlider().getValue();
    }

    public void setShapes(ObservableList<Node> initShapes) {
        shapes = initShapes;
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
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            //System.out.println(selectedShape.getFill().toString() + "  ----- before  ");
            //System.out.println(initColor.toString() + " ---------- after" );
            jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentFillColor", selectedShape.getFill(), initColor, selectedShape);
            jTPS.addTransaction(transaction);

        }
        /*
        if (shape == null) { // from workspace, not undo -> null
            selectedShape = getSelectedShape();
        }
        else {
            selectedShape = shape;
            
        }
        currentFillColor = initColor;
        selectedShape.setFill(currentFillColor);
         */

        currentFillColor = initColor;
        if (selectedShape != null) {
            // selectedShape = shape;
            selectedShape.setFill(currentFillColor);
        } else {
            System.out.println("SHAPE IS NULL from fill color");
        }

    }

    public void setCurrentOutlineColor(Shape shape, Color initColor) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;

        if (!workspace.getredobtn() && !workspace.getundobtn()) {

            jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentOutlineColor", shape.getStroke(), initColor, selectedShape);
            jTPS.addTransaction(transaction);
        }

        currentOutlineColor = initColor;
        if (selectedShape != null) {
            selectedShape.setStroke(initColor);
        } else {
            System.out.println("SHAPE IS NULL from outline color");
        }
    }

    public void setCurrentOutlineThickness(Shape shape, int initBorderWidth) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            jTPS_Transaction transaction = new jTPS_Transaction(this, "setCurrentOutlineThickness", shape.getStrokeWidth(), initBorderWidth, shape);
            jTPS.addTransaction(transaction);
        }

        currentBorderWidth = initBorderWidth;
        if (selectedShape != null) {
            selectedShape.setStrokeWidth(initBorderWidth);
        } else {
            System.out.println("SHAPE IS NULL from outline THIcK ");
        }
    }

    public void removeSelectedShape(Shape shape) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        // add null
        selectedShape = shape;
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            jTPS_Transaction transaction = new jTPS_Transaction(this, "removeSelectedShape", null, null, selectedShape);
            jTPS.addTransaction(transaction);
        }

        if (selectedShape != null) {
            shapes.remove(selectedShape);
            selectedShape = null;
        }
    }

    public void moveSelectedShapeToBack(Shape shape) { // selectedShape as a param?
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            jTPS_Transaction transaction = new jTPS_Transaction(this, "moveSelectedShapeToBack", null, null, selectedShape);
            jTPS.addTransaction(transaction);
        }

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

    public void moveSelectedShapeToFront(Shape shape) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        selectedShape = shape;
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            jTPS_Transaction transaction = new jTPS_Transaction(this, "moveSelectedShapeToFront", null, null, selectedShape);
            jTPS.addTransaction(transaction);
        }
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

    public void startNewImage(double height, double width, Image image) {
        DraggableRectangle newRectangle = new DraggableRectangle();
        newRectangle.setHeight(height);
        newRectangle.setWidth(width);
        newRectangle.setFill(new ImagePattern(image));
        newRectangle.setUserData("IMAGE");

        newShape = newRectangle;
        initNewShape();
    }

    public void makeNewTextBox(String s) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        /*
        if (!workspace.getredobtn() && !workspace.getundobtn()) {
            jTPS_Transaction transaction = new jTPS_Transaction(this, "makeNewTextBox", s, null, null);
            jTPS.addTransaction(transaction);
        }
         */
        DraggableText text = new DraggableText();
        text.setText(s);
        text.setUserData("TEXT");
        newShape = text;
        text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 200)); // Default

        initNewShape();
    }

    public void changeTextBox(Shape shape, String news) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        // DraggableText text = null;
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
        //newShape.setFill(workspace.getFillColorPicker().getValue());                      // FOR IMG AND TEXT
        //newShape.setStroke(workspace.getOutlineColorPicker().getValue());
        //newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());

        // ADD THE SHAPE TO THE CANVAS
        shapes.add(newShape);
        if (newShape.getUserData() != null) {

            if (newShape.getUserData().equals("RECT") || newShape.getUserData().equals("ELLIP")
                    || newShape.getUserData().equals("TEXT")) { // Rect, Elip, Text
                newShape.setFill(workspace.getFillColorPicker().getValue());
                newShape.setStroke(workspace.getOutlineColorPicker().getValue());
                newShape.setStrokeWidth(workspace.getOutlineThicknessSlider().getValue());
                // newShape.setStrokeWidth(getCurrentOutlineThickness2()); same thing with above line
            }

            if (!workspace.getredobtn() && !workspace.getundobtn()) {
                jTPS_Transaction transaction = new jTPS_Transaction(this, "newShape", null, null, newShape);
                jTPS.addTransaction(transaction);
            }
            state = golState.SIZING_SHAPE;
        }

        if (newShape.getUserData().equals("IMAGE") || newShape.getUserData().equals("TEXT")) { // Image of Text
            state = golState.SELECTING_SHAPE;
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

    public void getItalicized(Shape shape) {

        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
        

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

    public void changefont(Shape shape, String newfont) {
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
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

    public void changesize(Shape shape, double n) {
        DraggableText text = (DraggableText) shape;
        golWorkspace workspace = (golWorkspace) app.getWorkspaceComponent();
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

    public Shape cloneShape(Shape shape) {
        Shape finalclone = null;
        Color fillColor2 = null;
        Color stroke = null;
        double strokeWidth = 0;

        if (shape != null) {
            if (shape.getUserData().equals("RECT") || (shape.getUserData().equals("ELLIP"))) {
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
                clone.setRadiusX(original.getRadiusY());
                clone.setRadiusY(original.getRadiusY());
                clone.setUserData("ELLIP");
                finalclone = clone;

            } else if (shape.getUserData().equals("TEXT")) { // USER DATA BASE TO ADD!!
                DraggableText original = (DraggableText) shape;
                finalclone = cloneText(original);

            } else if (shape.getUserData().equals("IMAGE")) {

                DraggableRectangle original = (DraggableRectangle) shape;
                DraggableRectangle clone = new DraggableRectangle();

                clone.setFill(original.getFill());

                clone.setWidth(original.getWidth());
                clone.setHeight(original.getHeight());

                clone.setUserData("IMAGE");
                finalclone = clone;

            }
        }
        System.out.println(finalclone.toString() + " SHAPE CLONED");
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
        clone.setUserData("TEXT");

        return clone;

    }

    public void pasteShape(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
            // System.out.println(shape.toString() + "SHAPE PASTED");
            state = SELECTING_SHAPE;
        }
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
}
