package Model;

public class Point {

    private Integer row;
    private Integer column;

    public Point() {

    }
    
    public Point(Integer row, Integer column) {

        this.row = row;
        this.column = column;
    }

    public Integer getRow() {

        return this.row;
    }

    public Integer getColumn() {

        return this.column;
    }

    public void setRow(Integer row) {

        this.row = row;
    }

    public void setColumn(Integer column) {

        this.column = column;
    }

    @Override
    public String toString() {

        return "(" + this.row + ", " + this.column + ")";
    }
}
