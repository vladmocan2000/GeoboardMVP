package Model;

import java.awt.*;

public class Circle extends GeometricFigure {

    private Point centerPoint;
    private Integer radius;

    public Circle() {

    }

    public Circle(String name, Point centerPoint, Integer radius, Color color) {

        super(name, color);
        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    public Integer getRadius() {

        return this.radius;
    }

    public void setRadius(Integer radius) {

        this.radius = radius;
    }

    public Point getCenterPoint() {

        return this.centerPoint;
    }

    public void setCenterPoint(Point centerPoint) {

        this.centerPoint = centerPoint;
    }

    @Override
    public String toString() {

        return "Name: " + super.getName() + ", Center: " + this.centerPoint + ", Radius: " + this.radius;
    }
}
