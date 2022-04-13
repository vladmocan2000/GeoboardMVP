package View;

import java.awt.*;
import java.awt.event.*;

import Model.Model;
import Presenter.Presenter;

public class View implements InterfaceView {

    private Frame frame;

    private MenuBar menu;

    private MenuItem load;
    private MenuItem save;
    private MenuItem areaAndPerimeter;
    private MenuItem sectorOfCircleArea;
    private MenuItem arcOfCircleLength;
    private MenuItem circumscribedCircle;
    private MenuItem inscribedCircle;
    private CheckboxMenuItem red, green, blue;

    private CheckboxMenuItem circleDrawingMode;
    private CheckboxMenuItem equilateralTriangleDrawingMode;
    private CheckboxMenuItem polygonDrawingMode;

    private Menu color;
    private Menu file;
    private Menu options;
    private Menu style;
    private Menu circleOptions;
    private Menu triangleOptions;
    private Menu polygonOptions;
    private Menu drawingMode;
    
    private Label mousePositionLabel;

    private DrawingSpace drawingSpace;

    private Presenter presenter;

    public View(String titlu) {

        this.load = new MenuItem("Load");
        this.save = new MenuItem("Save");

        this.file = new Menu("File");
        this.file.add(this.load);
        this.file.add(this.save);

        this.sectorOfCircleArea = new MenuItem("Sector of circle area");
        this.arcOfCircleLength = new MenuItem("Arc of circle length");

        this.circleOptions = new Menu("Circle");
        this.circleOptions.add(this.sectorOfCircleArea);
        this.circleOptions.add(this.arcOfCircleLength);

        this.inscribedCircle = new MenuItem("Inscribed circle");

        this.triangleOptions = new Menu("Triangle");
        this.triangleOptions.add(this.inscribedCircle);

        this.circumscribedCircle = new MenuItem("Circumscribed circle");

        this.polygonOptions = new Menu("Polygon");
        this.polygonOptions.add(this.circumscribedCircle);

        this.areaAndPerimeter = new MenuItem("Area and Perimeter");

        this.options = new Menu("Options");
        this.options.add(this.areaAndPerimeter);
        this.options.add(this.circleOptions);
        this.options.add(this.triangleOptions);
        this.options.add(this.polygonOptions);
        
        this.color = new Menu("Color");

        red = new CheckboxMenuItem("Red");
        green = new CheckboxMenuItem("Green");
        blue = new CheckboxMenuItem("Blue");
        this.color.add(this.red);
        this.color.add(this.green);
        this.color.add(this.blue);

        this.style = new Menu("Style");
        this.style.add(this.color);

        this.circleDrawingMode = new CheckboxMenuItem("Circle");
        this.equilateralTriangleDrawingMode = new CheckboxMenuItem("Equilateral triangle");
        this.polygonDrawingMode = new CheckboxMenuItem("Polygon");

        this.drawingMode = new Menu("Drawing mode");
        this.drawingMode.add(this.circleDrawingMode);
        this.drawingMode.add(this.equilateralTriangleDrawingMode);
        this.drawingMode.add(this.polygonDrawingMode);

        this.menu = new MenuBar();
        this.menu.add(this.file);
        this.menu.add(this.options);
        this.menu.add(this.style);
        this.menu.add(this.drawingMode);

        this.mousePositionLabel = new Label();
        this.mousePositionLabel.setAlignment(Label.LEFT);
        this.mousePositionLabel.setBackground(Color.GRAY);
        this.mousePositionLabel.setText("");

        this.presenter = new Presenter(this, new Model());
        
        this.drawingSpace = new DrawingSpace(this.presenter);

        this.frame = new Frame(titlu);
        this.frame.setSize(1200, 600);
        this.frame.setLayout(new BorderLayout());
        this.frame.setMenuBar(this.menu);
        this.frame.add(this.mousePositionLabel, BorderLayout.SOUTH);
        this.frame.add(this.drawingSpace, BorderLayout.CENTER);
        
        this.frame.setVisible(true);

        this.frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                View.this.presenter.onWindowClosing();
            }
        });

        this.drawingSpace.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                View.this.presenter.onMouseMove(e.getY(), e.getX());
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                View.this.presenter.onMouseDragged(e.getY(), e.getX());
            }
        });

        this.drawingSpace.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {

                View.this.presenter.onMouseExited();
            }

            @Override
            public void mousePressed(MouseEvent e) {

                View.this.presenter.onMousePressed(e.getY(), e.getX());
            }
        });

        this.circleDrawingMode.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                View.this.presenter.onCircleDrawingModeStateChange(View.this.circleDrawingMode.getState());
            }
        });

        this.equilateralTriangleDrawingMode.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                View.this.presenter.onEquilateralTriangleDrawingModeStateChange(View.this.equilateralTriangleDrawingMode.getState());
            }
        });

        this.polygonDrawingMode.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                View.this.presenter.onPolygonDrawingModeStateChange(View.this.polygonDrawingMode.getState());
            }
        });

        this.areaAndPerimeter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                View.this.presenter.onAreaAndPerimeterButtonPressed(new DialogSelectGeometricFigure(View.this.frame, true, View.this.presenter).getGeometricFigureName());
            }
        });

        this.load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                FileDialog fileDialog = new FileDialog(View.this.frame, "Choose file", FileDialog.LOAD);
                fileDialog.setDirectory(".");
                fileDialog.setVisible(true);

                View.this.presenter.loadXMLFile(fileDialog.getDirectory() + fileDialog.getFile());
            }
        });

        this.save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                FileDialog fileDialog = new FileDialog(View.this.frame, "Choose file", FileDialog.SAVE);
                fileDialog.setDirectory(".");
                fileDialog.setVisible(true);

                View.this.presenter.saveXMLFile(fileDialog.getDirectory() + fileDialog.getFile());
            }
        });

        this.red.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                View.this.presenter.onRedChange(View.this.red.getState());
            }
        });

        this.green.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                View.this.presenter.onGreenChange(View.this.green.getState());
            }
        });

        this.blue.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                View.this.presenter.onBlueChange(View.this.blue.getState());
            }
        });

        this.circumscribedCircle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                View.this.presenter.onCircumscribedCircleButtonPressed(new DialogSelectGeometricFigure(View.this.frame, true, View.this.presenter).getGeometricFigureName());
            }
        });

        this.inscribedCircle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                View.this.presenter.onInscribedCircleButtonPressed(new DialogSelectGeometricFigure(View.this.frame, true, View.this.presenter).getGeometricFigureName());
            }
        });
    }

    @Override
    public void setMousePositionLabel(String s) {

        this.mousePositionLabel.setText(s);
    }

    @Override
    public void drawCircle(Graphics g, Boolean fillState, Color color, int row, int column, int diameter) {

        if(fillState) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillOval(column, row, diameter, diameter);

            return ;
        }

        g.setColor(color);
        g.drawOval(column, row, diameter, diameter);
    }

    @Override
    public void drawPolygon(Graphics g, Boolean fillState, Color color, int[] rows, int[] columns) {

        if(fillState) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillPolygon(columns, rows, rows.length);
            
            return ;
        }

        g.setColor(color);
        g.drawPolygon(columns, rows, rows.length);
    }

    @Override
    public void drawPolyline(Graphics g, Color color, int[] rows, int[] columns) {

        g.setColor(color);
        g.drawPolyline(columns, rows, rows.length);
    }

    @Override
    public void updateDrawingSpace() {

        this.drawingSpace.repaint();
    }

    @Override
    public void resetCircleDrawingMode() {

        this.circleDrawingMode.setState(false);  
    }

    @Override
    public void resetEquilateralTriangleDrawingMode() {

        this.equilateralTriangleDrawingMode.setState(false);
    }

    @Override
    public void resetPolygonDrawingMode() {

        this.polygonDrawingMode.setState(false);
    }

    @Override
    public void resetRedState() {

        this.red.setState(false); 
    }

    @Override
    public void resetBlueState() {

        this.blue.setState(false);
    }

    @Override
    public void resetGreenState() {

        this.green.setState(false);
    }

    @Override
    public void showAreaAndPerimeter(Double area, Double perimeter) {

        new DialogShowAreaAndPerimeter(this.frame, true, area, perimeter);
    }
}