package Model;

import java.awt.Color;

public class Model {

    private GeometricFigure geometricFigures[];
    static Color implicitColor = Color.BLACK;

    public Model() {

        this.geometricFigures = new GeometricFigure[0];
    }

    public Color getImplicitColor() {

        return Model.implicitColor;
    }

    public void setImplicitColor(Color color) {

        Model.implicitColor = color;
    }

    public void addGeometricFigure(GeometricFigure geometricFigure) {

        GeometricFigure[] array = new GeometricFigure[this.geometricFigures.length + 1];

        for(int i=0; i<this.geometricFigures.length; i++) {

            array[i] = this.geometricFigures[i];
        }
        array[array.length - 1] = geometricFigure;

        this.geometricFigures = array;
    }

    public GeometricFigure getLastGeometricFigure() {

        return this.geometricFigures[this.geometricFigures.length - 1];
    }

    public GeometricFigure[] getGeometricFigures() {

        return this.geometricFigures;
    }

    public void setGeometricFigures(GeometricFigure[] geometricFigures) {

        this.geometricFigures = geometricFigures;
    }

    public void removeLastGeometricFigure() {

        GeometricFigure[] array = new GeometricFigure[this.geometricFigures.length - 1];

        for(int i=0; i<this.geometricFigures.length - 1; i++) {

            array[i] = this.geometricFigures[i];
        }

        this.geometricFigures = array;
    }

    public void putFirstKElemet(int k) {

        GeometricFigure[] array = new GeometricFigure[this.geometricFigures.length];

        array[0] = this.geometricFigures[k];
        array[k] = this.geometricFigures[0];
        for(int i=1; i<this.geometricFigures.length; i++) {

            if(i != k) {

                array[i] = this.geometricFigures[i];
            }
        }

        this.geometricFigures = array;
    }

    @Override
    public String toString() {

        String s = new String();
        s = "Geometric figures of the model:\n";
        for(GeometricFigure geometricFigure : this.geometricFigures) {

            s += "   -" + geometricFigure + "\n";
        }

        return s;
    }
}