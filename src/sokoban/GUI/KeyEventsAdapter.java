package sokoban.GUI;

import static sokoban.logic.Pos.Direction.*;
import sokoban.logic.LevelState;

import javax.swing.*;

import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventsAdapter extends KeyAdapter {
    private GameFrame gameFrame;
    private WarehousePanel mapPanel;

    public KeyEventsAdapter(GameFrame gameFrame, WarehousePanel mapPanel) {
        this.gameFrame = gameFrame;
        this.mapPanel = mapPanel;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        LevelState state = mapPanel.getState();
        switch (ke.getKeyCode()) {
            case VK_LEFT: state.move(LEFT); break;
            case VK_RIGHT: state.move(RIGHT); break;
            case VK_UP: state.move(UP); break;
            case VK_DOWN: state.move(DOWN); break;
            case VK_U: state.undoMove(); break;
            case VK_R: state.resetLevel(); break;
            case VK_B: gameFrame. setPreviousLevel(); break;
            case VK_N: gameFrame.setNextLevel(); break;
            default: break;
        }

        if (mapPanel.getState().hasGameStarted()) {
            TimerListener tl = new TimerListener(mapPanel.getState(),
                    gameFrame.getTimeLabel());
            Timer timer = new Timer(1000, tl);
            tl.setTimer(timer);

            if (state.hasGameStarted() & !state.isLevelSolved() &
                    !timer.isRunning()) {
                timer.start();
            }
        }

        gameFrame.getLevelSolvedLabel().setVisible(
                state.getMap().levelSolved());
        gameFrame.refreshStatLabels();
        mapPanel.updateUI();
    }
}
