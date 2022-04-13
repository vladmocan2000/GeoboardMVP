package Presenter;

import View.*;
import Model.*;
import Model.Point;
import Model.Polygon;

import java.awt.*;

public class Presenter {

    private View view;
    private Model model;

    private Integer rowLastPressed, columnLastPressed, numberOfCircles = 0, numberOfPolygons = 0; 
    private Boolean circleDrawingMode = false, equilateralTriangleDrawingMode = false, polygonDrawingMode = false, creatingPolygon = false;

    public Presenter(View view, Model model) {

        this.view = view;
        this.model = model;
    }

    public void onWindowClosing() {

        System.exit(0);
    }

    public void onMouseMove(Integer row, Integer column) {

        this.view.setMousePositionLabel("(" + row + ", " + column + ")");

        if(creatingPolygon) {

            Polygon polygon = (Polygon)this.model.getLastGeometricFigure();
            polygon.getLastPoint().setRow(row);
            polygon.getLastPoint().setColumn(column);

            String padded = String.format("%-30s", "(" + row + ", " + column + ")");
            int x1 = polygon.getLastPoint().getColumn();
            int y1 = polygon.getLastPoint().getRow();
            int x2 = polygon.getPoints()[polygon.getPoints().length - 2].getColumn();
            int y2 = polygon.getPoints()[polygon.getPoints().length - 2].getRow();
            double l = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
            this.view.setMousePositionLabel(padded + "Length of the current line: " + l); 

            this.view.updateDrawingSpace();
        }
    }

    public void onMouseDragged(Integer row, Integer column) {

        this.view.setMousePositionLabel("(" + row + ", " + column + ")");

        if(circleDrawingMode || equilateralTriangleDrawingMode || polygonDrawingMode) {
            
            GeometricFigure geometricFigure = this.model.getLastGeometricFigure();

            if(geometricFigure instanceof Circle && circleDrawingMode) {

                Integer circleRadius = Integer.max(1, Integer.max(row - rowLastPressed, column - columnLastPressed));
                Circle circle = (Circle)geometricFigure;
                circle.setRadius(circleRadius);

                String padded = String.format("%-30s", "(" + row + ", " + column + ")");
                this.view.setMousePositionLabel(padded + "Radius of the circle: " + circle.getRadius()); 
            }

            if(geometricFigure instanceof Polygon && equilateralTriangleDrawingMode) {

                Polygon equilateralTriangle = (Polygon)geometricFigure;
                if(row > rowLastPressed && column > columnLastPressed) {
                    
                    int l = Integer.max(column - columnLastPressed, row - rowLastPressed);
                    int h = (int)((Math.sqrt(3) * l) / 2 + 0.5);
                    equilateralTriangle.getPoints()[0] = new Point(rowLastPressed, columnLastPressed + (int)(((double)l)/2 + 0.5));
                    equilateralTriangle.getPoints()[1] = new Point(rowLastPressed + h, columnLastPressed);
                    equilateralTriangle.getPoints()[2] = new Point(rowLastPressed + h, columnLastPressed + l);

                    String padded = String.format("%-30s", "(" + row + ", " + column + ")");
                    this.view.setMousePositionLabel(padded + "Side of the equilateral triangle: " + Math.sqrt((columnLastPressed + l/2 - columnLastPressed) *
                                                                                                              (columnLastPressed + l/2 - columnLastPressed) + 
                                                                                                              (rowLastPressed - rowLastPressed + h) * 
                                                                                                              (rowLastPressed - rowLastPressed + h))); 
                }
                else {

                    equilateralTriangle.getPoints()[0] = new Point(rowLastPressed, columnLastPressed);
                    equilateralTriangle.getPoints()[1] = new Point(rowLastPressed, columnLastPressed);
                    equilateralTriangle.getPoints()[2] = new Point(rowLastPressed, columnLastPressed);

                    String padded = String.format("%-30s", "(" + row + ", " + column + ")");
                    this.view.setMousePositionLabel(padded + "Side of the equilateral triangle: " + 0); 
                }
            }

            if(geometricFigure instanceof Polygon && polygonDrawingMode) {

                Polygon polygon = (Polygon)geometricFigure;
                polygon.getLastPoint().setRow(row);
                polygon.getLastPoint().setColumn(column);

                String padded = String.format("%-30s", "(" + row + ", " + column + ")");
                int x1 = polygon.getLastPoint().getColumn();
                int y1 = polygon.getLastPoint().getRow();
                int x2 = polygon.getPoints()[polygon.getPoints().length - 2].getColumn();
                int y2 = polygon.getPoints()[polygon.getPoints().length - 2].getRow();
                double l = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                this.view.setMousePositionLabel(padded + "Length of the current line: " + l); 
            }

            this.view.updateDrawingSpace();
        }
    }

    public void onMousePressed(Integer row, Integer column) {

        this.rowLastPressed = row;
        this.columnLastPressed = column;

        if(circleDrawingMode) {

            Circle circle = new Circle("circle " + numberOfCircles++, new Point(row, column), 1, this.model.getImplicitColor());
            this.model.addGeometricFigure(circle);

            String padded = String.format("%-30s", "(" + row + ", " + column + ")");
            this.view.setMousePositionLabel(padded + "Radius of the circle: " + circle.getRadius()); 

            this.view.updateDrawingSpace();
        }

        if(equilateralTriangleDrawingMode) {

            Polygon triangle = new Polygon("polygon " + numberOfPolygons++, this.model.getImplicitColor());
            triangle.addPoint(new Point(row, column));
            triangle.addPoint(new Point(row, column));
            triangle.addPoint(new Point(row, column));
            this.model.addGeometricFigure(triangle);

            String padded = String.format("%-30s", "(" + row + ", " + column + ")");
            this.view.setMousePositionLabel(padded + "Side of the equilateral triangle: " + 0); 

            this.view.updateDrawingSpace();
        }

        if(polygonDrawingMode) {

            if(creatingPolygon) {
                
                int errorThreshold = 10;
                Polygon polygon = (Polygon)this.model.getLastGeometricFigure();
                if(Math.abs(polygon.getPoints()[0].getRow() - row) < errorThreshold && Math.abs(polygon.getPoints()[0].getColumn() - column) < errorThreshold) {

                    creatingPolygon = false;
                    polygon.removeLastPoint();
                    if(polygon.getPoints().length == 2) {

                        this.model.removeLastGeometricFigure();
                    }
                }
                else {
                    
                    polygon.addPoint(new Point(row, column));
                }
            }
            else {

                creatingPolygon = true;
                Polygon polygon = new Polygon("polygon " + numberOfPolygons++, this.model.getImplicitColor());
                polygon.addPoint(new Point(row, column));
                polygon.addPoint(new Point(row, column));
                this.model.addGeometricFigure(polygon);
            }

            String padded = String.format("%-30s", "(" + row + ", " + column + ")");
            this.view.setMousePositionLabel(padded + "Length of the current line: " + 0); 

            this.view.updateDrawingSpace();
        }
    }

    public void onMouseExited() {

        this.view.setMousePositionLabel("");
    }

    public void onCircleDrawingModeStateChange(Boolean state) {

        circleDrawingMode = state;

        if(circleDrawingMode) {

            equilateralTriangleDrawingMode = false;
            polygonDrawingMode = false;
            this.view.resetEquilateralTriangleDrawingMode();
            this.view.resetPolygonDrawingMode();
        }
    }

    public void onEquilateralTriangleDrawingModeStateChange(Boolean state) {

        equilateralTriangleDrawingMode = state;

        if(equilateralTriangleDrawingMode) {

            circleDrawingMode = false;
            polygonDrawingMode = false;
            this.view.resetCircleDrawingMode();
            this.view.resetPolygonDrawingMode();
        }
    }

    public void onPolygonDrawingModeStateChange(Boolean state) {

        polygonDrawingMode = state;
        
        if(polygonDrawingMode) {

            circleDrawingMode = false;
            equilateralTriangleDrawingMode = false;
            this.view.resetCircleDrawingMode();
            this.view.resetEquilateralTriangleDrawingMode();
        }
    }

    public double calculateArea(GeometricFigure geometricFigure) {

        if(geometricFigure instanceof Circle) {

            Circle circle = (Circle)geometricFigure;
            double area = Math.PI * circle.getRadius() * circle.getRadius();

            return area;
        }
        else {
                    
            Polygon polygon = (Polygon)geometricFigure;
            double area = polygon.getLastPoint().getColumn() * polygon.getPoints()[0].getRow() - polygon.getLastPoint().getRow() * polygon.getPoints()[0].getColumn();

            for(int i=0; i<polygon.getPoints().length - 1; i++) {

                area += polygon.getPoints()[i].getColumn() * polygon.getPoints()[i+1].getRow() - polygon.getPoints()[i].getRow() * polygon.getPoints()[i+1].getColumn(); 
            }

            area = Math.abs(area) / 2;
            
            return area;
        }
    }

    public double calculatePerimeter(GeometricFigure geometricFigure) {

        if(geometricFigure instanceof Circle) {

            Circle circle = (Circle)geometricFigure;
            double perimeter = 2 * Math.PI * circle.getRadius();

            return perimeter;
        }
        else {
                    
            Polygon polygon = (Polygon)geometricFigure;
            double perimeter = Math.sqrt((polygon.getLastPoint().getColumn() - polygon.getPoints()[0].getColumn()) * 
                                         (polygon.getLastPoint().getColumn() - polygon.getPoints()[0].getColumn())
                                       + (polygon.getLastPoint().getRow() - polygon.getPoints()[0].getRow()) *
                                         (polygon.getLastPoint().getRow() - polygon.getPoints()[0].getRow()));

            for(int i=0; i<polygon.getPoints().length - 1; i++) {

                perimeter += Math.sqrt((polygon.getPoints()[i].getColumn() - polygon.getPoints()[i+1].getColumn()) * 
                                         (polygon.getPoints()[i].getColumn() - polygon.getPoints()[i+1].getColumn())
                                       + (polygon.getPoints()[i].getRow() - polygon.getPoints()[i+1].getRow()) *
                                         (polygon.getPoints()[i].getRow() - polygon.getPoints()[i+1].getRow()));
            }

            return perimeter;
        }
    }

    public void onAreaAndPerimeterButtonPressed(String geometricFigureName) {

        if(geometricFigureName == null) {

            return ;
        }

        for(GeometricFigure geometricFigure : this.model.getGeometricFigures()) {
    
            if(geometricFigure.getName().equals(geometricFigureName)) {
    
                geometricFigure.setFillState(false);

                this.view.showAreaAndPerimeter(calculateArea(geometricFigure), calculatePerimeter(geometricFigure));

                break ;
            }
        }

        this.view.updateDrawingSpace();
    }

    public void setFillStateAfterName(String geometricFigureName) {

        for(GeometricFigure geometricFigure : this.model.getGeometricFigures()) {

            if(geometricFigure.getName().equals(geometricFigureName)) {

                geometricFigure.setFillState(true);
            }
            else {

                geometricFigure.setFillState(false);
            }
        }

        for(int i=0; i<this.model.getGeometricFigures().length; i++) {

            if(this.model.getGeometricFigures()[i].getName().equals(geometricFigureName)) {

                this.model.putFirstKElemet(i);
                break ;
            }
        }

        this.view.updateDrawingSpace();
    }

    public String[] getListOfGeometricFigureNames() {

        String[] list = new String[this.model.getGeometricFigures().length];

        int i=0;
        for(GeometricFigure geometricFigure : this.model.getGeometricFigures()) {

            list[i++] = geometricFigure.getName();
        }

        return list;
    }

    public void onRedChange(Boolean state) {

        if(state) {

            this.model.setImplicitColor(Color.RED);
            this.view.resetBlueState();
            this.view.resetGreenState();
        }
        else {

            this.model.setImplicitColor(Color.BLACK);
        }
    }

    public void onGreenChange(Boolean state) {

        if(state) {

            this.model.setImplicitColor(Color.GREEN);
            this.view.resetBlueState();
            this.view.resetRedState();
        }
        else {

            this.model.setImplicitColor(Color.BLACK);
        }
    }

    public void onBlueChange(Boolean state) {

        if(state) {

            this.model.setImplicitColor(Color.BLUE);
            this.view.resetRedState();
            this.view.resetGreenState();
        }
        else {

            this.model.setImplicitColor(Color.BLACK);
        }
    }

    public void onCircumscribedCircleButtonPressed(String name) {

        if(name == null) {

            return ;
        }

        for(GeometricFigure geometricFigure : this.model.getGeometricFigures()) {

            if(geometricFigure.getName().equals(name) && geometricFigure instanceof Polygon) {
                    
                Polygon polygon = (Polygon)geometricFigure;
                polygon.setFillState(false);

                for(int i=0; i<polygon.getPoints().length; i++) {

                    for(int j=i+1; j<polygon.getPoints().length; j++) {

                        for(int k=j+1; k<polygon.getPoints().length; k++) {

                            double xn = ((double)polygon.getPoints()[i].getColumn() + polygon.getPoints()[k].getColumn())/2; 
                            double yn = ((double)polygon.getPoints()[i].getRow() + polygon.getPoints()[k].getRow())/2; 
                                
                            double xm = ((double)polygon.getPoints()[j].getColumn() + polygon.getPoints()[k].getColumn())/2; 
                            double ym = ((double)polygon.getPoints()[j].getRow() + polygon.getPoints()[k].getRow())/2; 

                            double mm = ((double)polygon.getPoints()[k].getColumn() - polygon.getPoints()[j].getColumn()) / 
                                            ((double)polygon.getPoints()[j].getRow() - polygon.getPoints()[k].getRow());
                            double mn = ((double)polygon.getPoints()[k].getColumn() - polygon.getPoints()[i].getColumn()) / 
                                            ((double)polygon.getPoints()[i].getRow() - polygon.getPoints()[k].getRow());

                            double x, y;
                            if(Double.isInfinite(mm)) {

                                x = xm;
                                y = yn + mn * (xm - xn);
                            }
                            else if(Double.isInfinite(mn)) {

                                x = xn;
                                y = ym + mm * (xn - xm);
                            }
                            else {
                                 
                                x = (yn - ym + mm * xm - mn * xn) / (mm - mn);
                                y = ym + mm * (x - xm);
                            }

                            double error = 1, radius = Math.sqrt((x-polygon.getPoints()[i].getColumn()) * (x-polygon.getPoints()[i].getColumn()) + (y-polygon.getPoints()[i].getRow()) * (y-polygon.getPoints()[i].getRow()));
                            int ok = 0;
                            for(Point point : polygon.getPoints()) {

                                if(Math.sqrt((x-point.getColumn()) * (x-point.getColumn()) + (y-point.getRow()) * (y-point.getRow())) > radius + error) {

                                    ok = 1;
                                    break;
                                }
                            }

                            if(ok == 0) {

                                Circle circle = new Circle("circle " + numberOfCircles++, new Point((int)(y+0.5), (int)(x+0.5)), 1, this.model.getImplicitColor());
                                circle.setRadius((int)(radius+0.5));
                                this.model.addGeometricFigure(circle);
                            }
                        }
                    }
                }

                this.view.updateDrawingSpace();
                return ;
            }
        }
    }

    public void onInscribedCircleButtonPressed(String name) {

        if(name == null) {

            return ;
        }

        for(GeometricFigure geometricFigure : this.model.getGeometricFigures()) {

            if(geometricFigure.getName().equals(name) && geometricFigure instanceof Polygon) {
                    
                Polygon polygon = (Polygon)geometricFigure;
                polygon.setFillState(false);

                if(polygon.getPoints().length != 3) {

                    this.view.updateDrawingSpace();
                    return ;
                }

                double a = Math.sqrt((polygon.getPoints()[1].getRow() - polygon.getPoints()[2].getRow()) * (polygon.getPoints()[1].getRow() - polygon.getPoints()[2].getRow()) +
                                     (polygon.getPoints()[1].getColumn() - polygon.getPoints()[2].getColumn()) * (polygon.getPoints()[1].getColumn() - polygon.getPoints()[2].getColumn()));
                double b = Math.sqrt((polygon.getPoints()[0].getRow() - polygon.getPoints()[2].getRow()) * (polygon.getPoints()[0].getRow() - polygon.getPoints()[2].getRow()) +
                                     (polygon.getPoints()[0].getColumn() - polygon.getPoints()[2].getColumn()) * (polygon.getPoints()[0].getColumn() - polygon.getPoints()[2].getColumn()));
                double c = Math.sqrt((polygon.getPoints()[0].getRow() - polygon.getPoints()[1].getRow()) * (polygon.getPoints()[0].getRow() - polygon.getPoints()[1].getRow()) +
                                     (polygon.getPoints()[0].getColumn() - polygon.getPoints()[1].getColumn()) * (polygon.getPoints()[0].getColumn() - polygon.getPoints()[1].getColumn()));
                            
                double perimeter = a + b + c;

                double x = (a * polygon.getPoints()[0].getColumn() + b * polygon.getPoints()[1].getColumn() + c * polygon.getPoints()[2].getColumn()) / perimeter;
                double y = (a * polygon.getPoints()[0].getRow() + b * polygon.getPoints()[1].getRow() + c * polygon.getPoints()[2].getRow()) / perimeter;

                double mCB = ((double)polygon.getPoints()[2].getRow() - polygon.getPoints()[1].getRow()) /
                             ((double)polygon.getPoints()[2].getColumn() - polygon.getPoints()[1].getColumn());   
                double mr = (-1) / mCB;

                double xl, yl;
                if(Double.isInfinite(mCB)) {

                    xl = polygon.getPoints()[1].getColumn();
                    yl = y + mr * (xl - x);
                }
                else if(Double.isInfinite(mr)) {
    
                    xl = x;
                    yl = polygon.getPoints()[1].getRow() + mCB * (xl - polygon.getPoints()[1].getColumn());
                }
                else {
                                     
                    xl = (y - polygon.getPoints()[1].getRow() + mCB * polygon.getPoints()[1].getColumn() - mr * x ) / (mCB - mr);
                    yl = y + mr * (xl - x);
                }

                double radius = Math.sqrt((x-xl) * (x-xl) + (y-yl) * (y-yl));

                Circle circle = new Circle("circle " + numberOfCircles++, new Point((int)(y+0.5), (int)(x+0.5)), 1, this.model.getImplicitColor());
                circle.setRadius((int)(radius+0.5));
                this.model.addGeometricFigure(circle);
                
                this.view.updateDrawingSpace();
                return ;
            }
        }
    }

    public void saveXMLFile(String filePath) {

        if(filePath.contains(".xml")) {
            
            new PersistenceModel(filePath).saveModel(this.model);
        }
    }

    public void loadXMLFile(String filePath) {

        if(filePath.contains(".xml")) {
            
            this.model = new PersistenceModel(filePath).loadModel();
            this.numberOfPolygons = this.model.getGeometricFigures().length;
            this.numberOfCircles = this.model.getGeometricFigures().length;
            this.view.updateDrawingSpace();
        }
    }

    public void drawGeometricFigures(Graphics g) {

        for(GeometricFigure geometricFigure : this.model.getGeometricFigures()) {

            if(geometricFigure instanceof Circle) {

                Circle circle = (Circle)geometricFigure;
                this.view.drawCircle(g, circle.getFillState(), circle.getColor(), circle.getCenterPoint().getRow() - circle.getRadius(), circle.getCenterPoint().getColumn() - circle.getRadius(), 2 * circle.getRadius());
            }
            if(geometricFigure instanceof Polygon) {

                Polygon polygon = (Polygon)geometricFigure;
                
                int rows[] = new int[polygon.getPoints().length];
                int columns[] = new int[polygon.getPoints().length];

                for(int i=0; i<polygon.getPoints().length; i++) {

                    rows[i] = polygon.getPoints()[i].getRow();
                    columns[i] = polygon.getPoints()[i].getColumn();
                }

                if(creatingPolygon && this.model.getLastGeometricFigure() == geometricFigure) {
                        
                    this.view.drawPolyline(g, this.model.getImplicitColor(), rows, columns);
                }
                else {
                        
                    this.view.drawPolygon(g, polygon.getFillState(), polygon.getColor(), rows, columns);
                }
            }
        }
    }
}