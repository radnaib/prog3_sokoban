package sokoban.logic;

import static sokoban.logic.Pos.Direction.*;

public class Pos implements Cloneable {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, STAY
    }

    public static Direction oppositeDirection(Direction dir) {
        switch (dir) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return STAY;
        }
    }

    public static char directionToChar(Direction dir) {
        switch (dir) {
            case UP: return 'u';
            case DOWN: return 'd';
            case LEFT: return 'l';
            case RIGHT: return 'r';
            default: return ' ';
        }
    }

    public static Pos directionToPos(Direction dir) {
        switch (dir) {
            case UP: return new Pos(-1, 0);
            case DOWN: return new Pos(1, 0);
            case LEFT: return new Pos(0, -1);
            case RIGHT: return new Pos(0, 1);
            default: return new Pos(0, 0);
        }
    }

    private int row;
    private int col;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return ((Pos) super.clone());
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }

    public static Pos addPos(Pos pos1, Pos pos2) {
        return new Pos(pos1.getRow()+pos2.getRow(),
                pos1.getCol()+pos2.getCol());
    }

    public void add(Pos other) {
        row += other.getRow();
        col += other.getCol();
    }

}
