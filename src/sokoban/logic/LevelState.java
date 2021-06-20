package sokoban.logic;

import static sokoban.logic.Pos.Direction.*;
import static sokoban.logic.Warehouse.PlayerMoveSuccess.*;
import sokoban.logic.Warehouse.PlayerMoveSuccess;
import sokoban.logic.Pos.Direction;

import java.util.ArrayList;
import java.util.List;

public class LevelState {
    private Warehouse initialMap;
    private Warehouse map;
    private List<Character> moves;
    private int moveCount;
    private int pushCount;
    private int time;
    private boolean gameStarted;
    private boolean levelSolved;

    public LevelState(Warehouse initialMap) {
        try {
            this.initialMap = (Warehouse) initialMap.clone();
            this.map = (Warehouse) this.initialMap.clone();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        moves = new ArrayList<Character>();
        moveCount = 0;
        pushCount = 0;
        time = 0;
        gameStarted = false;
        levelSolved = false;
    }

    public Warehouse getMap() {
        return map;
    }
    public int getMoveCount() {
        return moveCount;
    }
    public int getPushCount() {
        return pushCount;
    }
    public int getTime() {
        return time;
    }
    public boolean hasGameStarted() {
        return gameStarted;
    }
    public boolean isLevelSolved() {
        return levelSolved;
    }

    private Direction charToDirection(char cMove) {
        cMove = Character.toLowerCase(cMove);
        switch(cMove) {
            case 'l': return LEFT;
            case 'u': return UP;
            case 'r': return RIGHT;
            case 'd': return DOWN;
            default: return Direction.STAY;
        }
    }

    public void move(Direction dir) {
        if (levelSolved) {
            return;
        }
        if (!gameStarted) {
            gameStarted = true;
        }

        PlayerMoveSuccess moveSuccess = map.movePlayer(dir);
        char dirChar = Pos.directionToChar(dir);
        if (moveSuccess == PUSH) {
            pushCount++;
            dirChar = Character.toTitleCase(dirChar);
        }
        if (moveSuccess == PUSH | moveSuccess == MOVE) {
            moveCount++;
            moves.add(dirChar);
        }
        levelSolved = map.levelSolved();
    }

    public void undoMove() {
        if (moveCount == 0) {
            return;
        }
        levelSolved = false;

        boolean pullBox;
        char cMove = moves.remove(moveCount-1);
        moveCount--;
        if (Character.isUpperCase(cMove)) {
            pullBox = true;
            pushCount--;
        } else {
            pullBox = false;
        }
        map.undoMovePlayer(charToDirection(cMove), pullBox);
    }

    public void resetLevel() {
        try {
            map = (Warehouse) initialMap.clone();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        moves.clear();
        moveCount = 0;
        pushCount = 0;
        time = 0;
        gameStarted = false;
        levelSolved = false;
    }

    public void increaseTime() {
        if (gameStarted & !levelSolved) {
            time++;
        }
    }

}
