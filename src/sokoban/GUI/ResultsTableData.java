package sokoban.GUI;

import sokoban.logic.Result;
import sokoban.logic.ResultMovesComparator;
import sokoban.logic.ResultPushesComparator;
import sokoban.logic.Results;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static sokoban.logic.Results.ResultsOrder.*;

public class ResultsTableData extends AbstractTableModel {

    private List<Result> levelResults;
    private Results.ResultsOrder ordering;

    public ResultsTableData(List<Result> levelResults) {
        this.levelResults = levelResults;
    }

    public List<Result> getLevelResults() {
        return levelResults;
    }
    public void setLevelResults(List<Result> levelResults) {
        this.levelResults = levelResults;
    }

    public Results.ResultsOrder getOrdering() {
        return ordering;
    }
    public void setOrdering(Results.ResultsOrder ordering) {
        this.ordering = ordering;
    }

    public void addResult(String name, int moveCount, int pushCount, int time) {
        int count = levelResults.size();
        Result newResult = new Result(0, name, moveCount, pushCount, time,
                LocalDateTime.now());
        levelResults.add(newResult);

        if (ordering == MOVES) {
            levelResults.sort(new ResultMovesComparator());
        } else {
            levelResults.sort(new ResultPushesComparator());
        }
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public int getRowCount() {
        return levelResults.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Result result = levelResults.get(rowIndex);
        switch(columnIndex) {
            case 0: return (rowIndex+1);
            case 1: return result.getName();
            case 2: return result.getMoveCount();
            case 3: return result.getPushCount();
            case 4: return result.getTime();
        }
        return result.getDate();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Rank";
            case 1: return "Name";
            case 2: return "Moves";
            case 3: return "Pushes";
            case 4: return "Time";
        }
        return "Date";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 2:
            case 3:
            case 4:
                return Integer.class;
            case 1:
                return String.class;
            case 5:

        }
        return LocalDateTime.class;
    }

}
