package sokoban.GUI;

import org.xml.sax.SAXException;
import sokoban.IO.ResultsHandler;
import sokoban.logic.LevelState;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MenuListener implements ActionListener {
    GameFrame gameFrame;
    public MenuListener(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        LevelState state = gameFrame.getMapPanel().getState();
        switch (ae.getActionCommand()) {
            case "loadCollection":
                try {
                    FileOutputStream os = new FileOutputStream(
                            "results"+ File.separator+
                                    gameFrame.getCollFilename()+"_results.xml");
                    ResultsHandler.saveResults(os, gameFrame.getResults());
                    gameFrame.loadCollection();
                }
                catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "resetLevel":
                gameFrame.getMapPanel().getState().resetLevel();
                break;

            case "showLevelResults":
                ResultsFrame resultsFrame = new ResultsFrame(
                        new ResultsTableData(gameFrame.getLevelResults()),
                        gameFrame.getMapPanel().getState());
                resultsFrame.setVisible(true);
                break;

            default:
                break;
        }

        gameFrame.getLevelSolvedLabel().setVisible(
                state.getMap().levelSolved());
        gameFrame.refreshStatLabels();
        gameFrame.getMapPanel().updateUI();
    }
}
