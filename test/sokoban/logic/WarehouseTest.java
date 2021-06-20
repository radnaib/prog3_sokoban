package sokoban.logic;

import static sokoban.logic.Field.FieldType.*;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sokoban.logic.Field.FieldType;
import sokoban.logic.Pos.Direction;

public class WarehouseTest {
	Warehouse warehouse;
	ArrayList<ArrayList<Field>> fields;
	Pos playerPos;
	
	private FieldType charToFieldType(char ch) {
        switch(ch) {
            case '#': return WALL;
            case '@': return PLAYER;
            case '+': return PLAYER_GOAL;
            case '$': return BOX;
            case '*': return BOX_GOAL;
            case '.': return GOAL;
            default: return FLOOR;
        }
    }
	
	@Before
	public void setUp() {
		String[] fieldsChar = {" #####\r\n",  "##.@ #\r\n",  
				"# $$ #\r\n", "#.  ##\r\n", "#####"};
		fields = new ArrayList<ArrayList<Field>>();
		for (int i = 0; i < 5; i++) {
			ArrayList<Field> row = new ArrayList<Field>();
			for (int j = 0; j < fieldsChar[i].length(); j++) {
				row.add(new Field(charToFieldType(fieldsChar[i].charAt(j))));
			}
			fields.add(row);
		}
		playerPos = new Pos(1, 3);
		warehouse = new Warehouse(fields, playerPos);
	}
	
	@Test
	public void levelSolvedTest() {
		Assert.assertEquals(warehouse.levelSolved(), false);
	}
	
	@Test
	public void cloneTest() {
		Warehouse whCopy = new Warehouse(fields, new Pos(0, 0));
		try {
			whCopy = (Warehouse) warehouse.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		Assert.assertNotSame(warehouse, whCopy);
		Assert.assertEquals(warehouse, whCopy);
	}
	
	@Test
	public void movePlayerTest() {
		warehouse.movePlayer(Direction.LEFT);
		Assert.assertEquals(warehouse.getPlayerPos(), new Pos(1, 2));
		warehouse.movePlayer(Direction.DOWN);
		Assert.assertEquals(warehouse.getPlayerPos(), new Pos(2, 2));
		Assert.assertEquals(warehouse.getField(new Pos(3, 2)).getType(), BOX);
	}
	
	@Test
	public void undoMovePlayerTest() {
		warehouse.undoMovePlayer(Direction.DOWN, true);
		Assert.assertEquals(warehouse.getPlayerPos(), new Pos(1, 2));
		Assert.assertEquals(warehouse.getField(new Pos(2, 2)).getType(), BOX);
		Assert.assertEquals(warehouse.getField(new Pos(3, 2)).getType(), FLOOR);
	}
	
}
