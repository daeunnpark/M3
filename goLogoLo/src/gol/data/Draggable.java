package gol.data;

/**
 * This interface represents a family of draggable shapes.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface Draggable {

    public static final String RECTANGLE = "RECTANGLE";
    public static final String ELLIPSE = "ELLIPSE";
    public static final String IMAGE = "IMAGE";
    public static final String TEXT = "TEXT";
    public double initialX = 0;
    public double initalY = 0;

    public golState getStartingState();

    public void start(int x, int y);

    public void drag(int x, int y);

    public void setXY(double x, double y);

    public void size(int x, int y);

    public double getX();

    public double getY();

    public double getWidth();

    public double getHeight();

    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);

    public String getShapeType();

    public void setInitialXY(double x, double y);

    public double getInitialX();

    public double getInitialY();

}
