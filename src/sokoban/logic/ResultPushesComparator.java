package sokoban.logic;

import java.util.Comparator;

public class ResultPushesComparator implements Comparator<Result> {

    @Override
    public int compare(Result r1, Result r2) {
        if (r1.getPushCount() < r2.getPushCount()) {
            return 1;
        } else if (r1.getPushCount() > r2.getPushCount()) {
            return -1;
        } else {
            if (r1.getTime() < r2.getTime()) {
                return 1;
            } else if (r1.getTime() > r2.getTime()) {
                return -1;
            } else {
                return r1.getDate().compareTo(r2.getDate());
            }
        }
    }
}
