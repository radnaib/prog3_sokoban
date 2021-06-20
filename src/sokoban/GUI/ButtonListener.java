package sokoban.GUI;

import static sokoban.logic.Pos.Direction.*;
import sokoban.logic.LevelState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    GameFrame gameFrame;
    public ButtonListener(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        LevelState state = gameFrame.getMapPanel().getState();
        switch (ae.getActionCommand()) {
            case "previousLevel": gameFrame.setPreviousLevel();
                gameFrame.getPrevLevelButton().setFocusable(false); break;
            case "nextLevel": gameFrame.setNextLevel();
                gameFrame.getNextLevelButton().setFocusable(false); break;
            case "moveLeft": state.move(LEFT);
                gameFrame.getMoveLeftButton().setFocusable(false); break;
            case "moveRight": state.move(RIGHT);
                gameFrame.getMoveRightButton().setFocusable(false); break;
            case "moveUp": state.move(UP);
                gameFrame.getMoveUpButton().setFocusable(false); break;
            case "moveDown": state.move(DOWN);
                gameFrame.getMoveDownButton().setFocusable(false); break;
            default: break;
        }

        gameFrame.getLevelSolvedLabel().setVisible(
                state.getMap().levelSolved());
        gameFrame.refreshStatLabels();
        gameFrame.getMapPanel().updateUI();
    }
}
