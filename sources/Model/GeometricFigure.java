package Model;

import java.awt.*;

public abstract class GeometricFigure {

    private String name;
    private Color color;
    private Boolean fillState;

    public GeometricFigure() {
        
    }

    public GeometricFigure(String name, Color color) {

        this.name = name;
        this.color = color;
        this.fillState = false;
    }

    public void setColor(Color color) {

        this.color = color;
    }

    public Color getColor() {

        return this.color;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Boolean getFillState() {

        return this.fillState;
    }

    public void setFillState(Boolean fillState) {

        this.fillState = fillState;
    }

    @Override
    public String toString() {

        return "Figura geometrica nedeterminata";
    }
}
