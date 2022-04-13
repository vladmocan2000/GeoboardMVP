package View;

import java.awt.*;

import Presenter.Presenter;

public class DrawingSpace extends Canvas{

    Presenter presenter;

    public DrawingSpace(Presenter presenter) {

        super();
        this.presenter = presenter;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        this.presenter.drawGeometricFigures(g);
    }
}