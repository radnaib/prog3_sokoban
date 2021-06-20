package sokoban.logic;

import java.util.ArrayList;

public class Results {

    public enum ResultsOrder {
        MOVES, PUSHES
    }

    private ArrayList<ArrayList<Result>> results;
    private ResultsOrder ordering;

    public Results(ArrayList<ArrayList<Result>> results) {
        this.results = results;
        ordering = ResultsOrder.MOVES;
    }

    public ResultsOrder getOrdering() {
        return ordering;
    }
    public void setOrdering(ResultsOrder ordering) {
        this.ordering = ordering;
    }

    public ArrayList<ArrayList<Result>> getResults() {
        return results;
    }
    public ArrayList<Result> getLevelResults(int levelNumber) {
        return results.get(levelNumber);
    }
    public void setLevelResults(int levelNumber, ArrayList<Result> levelResults) {
        results.set(levelNumber, levelResults);
    }

    public int getNLevels() {
        return results.size();
    }
    public void setNLevels(int nLevels) {
        while (results.size() < nLevels) {
            results.add(new ArrayList<Result>());
        }
    }

    public Result getResult(int levelNumber, int resultSeq) {
        return results.get(levelNumber).get(resultSeq);
    }
    public void setResult(int levelNumber, Result result) {
        ArrayList<Result> levelResults = results.get(levelNumber);
        levelResults.add(result);
    }
}
