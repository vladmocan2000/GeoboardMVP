package View;

import java.awt.*;
import java.awt.event.*;
import Presenter.Presenter;

public class DialogSelectGeometricFigure extends Dialog {

    private String geometricFigureName = null; 

    private Presenter presenter;

    private List geometricFiguresList;
    private Button okButton;
    private Panel panel;

    public DialogSelectGeometricFigure(Frame parent, Boolean modal, Presenter presenter) {

        super(parent, "Select geometric figure", modal);
        this.presenter = presenter;

        this.setSize(400, 300);
        this.setLocation(600, 400);
        
        geometricFiguresList = new List();

        String[] list = this.presenter.getListOfGeometricFigureNames();
        for (String geometricFigureName : list) {
            
            geometricFiguresList.add(geometricFigureName);
        }

        okButton = new Button("Ok");

        panel = new Panel(new BorderLayout());
        panel.add(geometricFiguresList, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        this.add(panel);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                DialogSelectGeometricFigure.this.geometricFigureName = null;
                DialogSelectGeometricFigure.this.dispose();
            }
        });

        this.okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                DialogSelectGeometricFigure.this.dispose();
            }
        });

        this.geometricFiguresList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                DialogSelectGeometricFigure.this.geometricFigureName = DialogSelectGeometricFigure.this.geometricFiguresList.getSelectedItem();
                DialogSelectGeometricFigure.this.presenter.setFillStateAfterName(DialogSelectGeometricFigure.this.geometricFigureName);
            }
        });

        this.setVisible(true);
    }

    public String getGeometricFigureName() {

        return this.geometricFigureName;
    }    
}