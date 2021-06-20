package sokoban.logic;

import static sokoban.logic.Field.FieldType.*;

public class Field {

    public enum FieldType implements Cloneable {
        WALL, PLAYER, PLAYER_GOAL, BOX, BOX_GOAL, GOAL, FLOOR
    }

    public FieldType type;

    public Field(FieldType type){
        this.type = type;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        System.out.println("Field clone: " + this);
        Field f = (Field) super.clone();
        f.type = type;
        return f;
    }

    public FieldType getType() {
        return type;
    }
    public void setType(FieldType type) {
        this.type = type;
    }

    public boolean isEmpty() {
        return (type == FLOOR | type == GOAL);
    }
    public boolean hasBox() {
        return (type == BOX | type == BOX_GOAL);
    }
    public boolean hasPlayer() {
        return (type == PLAYER | type == PLAYER_GOAL);
    }

    public void removePlayer() {
        if (type == PLAYER) {
            type = FLOOR;
        } else if (type == PLAYER_GOAL) {
            type = GOAL;
        }
    }

    public void acceptPlayer() {
        if (type == FLOOR) {
            type = PLAYER;
        } else if (type == GOAL) {
            type = PLAYER_GOAL;
        }
    }

    public void removeBox() {
        if (type == BOX) {
            type = FLOOR;
        } else if (type == BOX_GOAL) {
            type = GOAL;
        }
    }

    public void acceptBox() {
        if (type == FLOOR) {
            type = BOX;
        } else if (type == GOAL) {
            type = BOX_GOAL;
        }
    }
}
