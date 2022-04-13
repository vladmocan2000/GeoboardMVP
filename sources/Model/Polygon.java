package Model;

import java.awt.*;

public class Polygon extends GeometricFigure {

    private Point[] points;

    public Polygon() {
        
    }

    public Polygon(String name, Color color) {

        super(name, color);
        points = new Point[0];
    }

    public void addPoint(Point point) {

        Point[] array = new Point[this.points.length + 1];

        for(int i=0; i<this.points.length; i++) {

            array[i] = this.points[i];
        }
        array[array.length - 1] = point;

        this.points = array;
    }

    public Point[] getPoints() {

        return this.points;
    }

    public void setPoints(Point[] points) {

        this.points = points;
    }

    public Point getLastPoint() {

        return this.points[this.points.length - 1];
    }

    public void removeLastPoint() {

        Point[] array = new Point[this.points.length - 1];

        for(int i=0; i<this.points.length - 1; i++) {

            array[i] = this.points[i];
        }

        this.points = array;
    }

    @Override
    public String toString() {

        String s = new String();
        s = "Name: " + super.getName() +", Points: {";
        for(Point point : this.points) {

            s += point + ", ";
        }
        s = s.substring(0, s.length() - 2) + "}";

        return s;
    }
}
