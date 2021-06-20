package sokoban.GUI;

import sokoban.logic.LevelState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddResultActionListener implements ActionListener {
    private ResultsTableData resultsData;
    private JPanel panelAddResult;
    private JTextField nameField;
    private LevelState state;

    AddResultActionListener(ResultsTableData resultsData, JPanel panelAddResult,
                            JTextField nameField, LevelState state) {
        this.resultsData = resultsData;
        this.panelAddResult = panelAddResult;
        this.nameField = nameField;
        this.state = state;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("addResult")) {
            resultsData.addResult(nameField.getText(), state.getMoveCount(),
                    state.getPushCount(), state.getTime());
            panelAddResult.setVisible(false);
        }
    }
}
