package View;

import java.awt.*;
import java.awt.event.*;

public class DialogShowAreaAndPerimeter extends Dialog {

    private Panel panel;
    private Label areaLabel;
    private Label perimeterLabel;

    public DialogShowAreaAndPerimeter(Frame parent, Boolean modal, Double area, Double perimeter) {

        super(parent, "Info", modal);
        this.setLocation(600, 400);
        this.setSize(300, 100);

        areaLabel = new Label();
        areaLabel.setText("Area = " + area.toString());
        perimeterLabel = new Label();
        perimeterLabel.setText("Perimeter = " + perimeter.toString());

        panel = new Panel(new GridLayout(2, 1));
        panel.add(areaLabel);
        panel.add(perimeterLabel);

        this.add(panel);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                DialogShowAreaAndPerimeter.this.dispose();
            }
        });

        this.setVisible(true);
    }
}