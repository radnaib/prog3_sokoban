package sokoban.logic;

import static sokoban.logic.Field.FieldType.*;
import static sokoban.logic.Warehouse.PlayerMoveSuccess.*;

import java.util.ArrayList;

public class Warehouse implements Cloneable {
    public enum PlayerMoveSuccess {
        MOVE, PUSH, STAY
    }

    private ArrayList<ArrayList<Field>> fields;
    private Pos playerPos;
    
    public Warehouse(ArrayList<ArrayList<Field>> fields, Pos playerPos) {
        this.fields = fields;
        this.setPlayerPos(playerPos);
    }
    
    public Pos getPlayerPos() {
		return playerPos;
	}
    
    public void setPlayerPos(Pos pos) {
    	playerPos = pos;
    }
    
    public static ArrayList<ArrayList<Field>> cloneFields(
            ArrayList<ArrayList<Field>> fields) {
        ArrayList<ArrayList<Field>> fieldsClone =
                new ArrayList<ArrayList<Field>>();
        for (ArrayList<Field> row : fields) {
            ArrayList<Field> rowClone = new ArrayList<Field>();
            for (Field f : row) {
                rowClone.add(new Field(f.getType()));
            }
            fieldsClone.add(rowClone);
        }
        return fieldsClone;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Warehouse map = (Warehouse) super.clone();
        map.fields = Warehouse.cloneFields(this.fields);
        map.setPlayerPos((Pos) getPlayerPos().clone());
        return map;
    }

    public ArrayList<ArrayList<Field>> getFields() {
        return fields;
    }
    public Field getField(Pos pos) {
        return fields.get(pos.getRow()).get(pos.getCol());
    }

    public boolean levelSolved() {
        for (ArrayList<Field> row : fields) {
            for (Field field : row) {
                if (field.getType() == BOX) {
                    return false;
                }
            }
        }
        return true;
    }

    public PlayerMoveSuccess movePlayer(Pos.Direction dir) {
        Field playerField = this.getField(getPlayerPos());
        Pos playerGoal = Pos.addPos(getPlayerPos(), Pos.directionToPos(dir));
        Field playerGoalField = this.getField(playerGoal);

        if (playerGoalField.isEmpty()) {
            playerField.removePlayer();
            playerGoalField.acceptPlayer();
            setPlayerPos(playerGoal);
            return MOVE;
        }

        if (playerGoalField.hasBox()) {
            Pos boxGoal = Pos.addPos(playerGoal, Pos.directionToPos(dir));
            Field boxGoalField = this.getField(boxGoal);
            if (boxGoalField.isEmpty()) {
                playerGoalField.removeBox();
                boxGoalField.acceptBox();
                playerField.removePlayer();
                playerGoalField.acceptPlayer();
                setPlayerPos(playerGoal);
                return PUSH;
            }
        }

        return STAY;
    }

    public void undoMovePlayer(Pos.Direction dir, boolean pullBox) {
        Field playerField = this.getField(getPlayerPos());
        Pos playerUndoGoal = Pos.addPos(getPlayerPos(),
                Pos.directionToPos(Pos.oppositeDirection(dir)));
        Field playerUndoGoalField = this.getField(playerUndoGoal);
        playerField.removePlayer();
        playerUndoGoalField.acceptPlayer();
        if (pullBox) {
            Pos boxPos = Pos.addPos(getPlayerPos(), Pos.directionToPos(dir));
            Field boxField = this.getField(boxPos);
            boxField.removeBox();
            playerField.acceptBox();
        }
        setPlayerPos(playerUndoGoal);
    }
    
}
