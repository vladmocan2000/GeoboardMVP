package View;

import java.awt.*;

interface InterfaceView {

    void setMousePositionLabel(String s);
    void drawPolyline(Graphics g, Color color, int[] rows, int[] columns);
    void drawCircle(Graphics g, Boolean fillState, Color color, int row, int column, int diameter);
    void drawPolygon(Graphics g, Boolean fillState, Color color, int[] rows, int[] columns);
    void updateDrawingSpace();
    void resetCircleDrawingMode();
    void resetEquilateralTriangleDrawingMode();
    void resetPolygonDrawingMode();
    void resetRedState();
    void resetBlueState();
    void resetGreenState();
    void showAreaAndPerimeter(Double area, Double perimeter);
}