package sokoban.GUI;

import sokoban.logic.LevelState;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ResultsFrame extends JFrame {
    private LevelState state;
    private ResultsTableData resultsData;
    private JPanel panelAddResult;
    private JTextField nameField;

    private void initComponents() {
        this.setLayout(new BorderLayout());

        JTable tableStudentData = new JTable(resultsData);
        tableStudentData.setFillsViewportHeight(true);
        tableStudentData.setAutoCreateRowSorter(false);
        this.add(new JScrollPane(tableStudentData), BorderLayout.CENTER);

        panelAddResult = new JPanel();
        nameField = new JTextField(24);
        JButton addButton = new JButton("Beküldöm!");
        addButton.setActionCommand("addResult");
        addButton.addActionListener(
                new AddResultActionListener(
                        resultsData, panelAddResult, nameField, state)
        );

        panelAddResult.add(new JLabel("Írd be a neved: "));
        panelAddResult.add(nameField);
        panelAddResult.add(addButton);
        this.add(panelAddResult, BorderLayout.SOUTH);
    }

    public ResultsFrame(ResultsTableData resultsData, LevelState state) {
        super("Level Results");
        this.resultsData = resultsData;
        this.state = state;

        setMinimumSize(new Dimension(500, 200));
        initComponents();
    }

}

