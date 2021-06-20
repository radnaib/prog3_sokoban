package sokoban.GUI;

import sokoban.logic.LevelState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
    private LevelState state;
    private Timer timer;
    private JLabel timeLabel;

    public TimerListener(LevelState state, JLabel timeLabel) {
        this.state = state;
        this.timeLabel = timeLabel;
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int time = Integer.parseInt(timeLabel.getText().substring(6));
        timeLabel.setText("Time: "+(time+1));
        if (!state.hasGameStarted() | state.isLevelSolved()) {
            timer.stop();
        }
    }
}
