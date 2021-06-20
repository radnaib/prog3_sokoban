package sokoban.logic;

import java.time.LocalDateTime;

public class Result {
    private int rank;
    private String name;
    private int moveCount;
    private int pushCount;
    private int time;
    private LocalDateTime date;

    public Result(int rank, String name, int moveCount,
                  int pushCount, int time, LocalDateTime date) {
        this.rank = rank;
        this.name = name;
        this.moveCount = moveCount;
        this.pushCount = pushCount;
        this.time = time;
        this.date = date;
    }

    public int getRank() {
        return rank;
    }
    public String getName() {
        return name;
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
    public LocalDateTime getDate() {
        return date;
    }

}
