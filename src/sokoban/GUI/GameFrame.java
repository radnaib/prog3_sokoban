package sokoban.GUI;

import org.xml.sax.SAXException;
import sokoban.IO.LevelCollectionHandler;
import sokoban.IO.ResultsHandler;
import sokoban.logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class GameFrame extends JFrame {
    private String collName;
    private LevelCollection levelCollection;
    private Results results;
    private int levelNumber;
    private BufferedImage pictures;
    private WarehousePanel mapPanel;

    private JLabel levelNameLabel;
    private JLabel authorLabel;
    private JLabel movesLabel;
    private JLabel pushesLabel;
    private JLabel timeLabel;
    private JLabel levelSolvedLabel;

    private JButton prevLevelButton;
    private JButton nextLevelButton;
    private JButton moveLeftButton;
    private JButton moveRightButton;
    private JButton moveUpButton;
    private JButton moveDownButton;

    public String getCollFilename() {
        return collName;
    }

    public JMenuBar getMyMenuBar() {
        return menuBar;
    }

    private JPanel titlePanel;
    private JPanel statPanel;
    private JPanel controlPanel;
    private JPanel westernPanel;
    private JMenuBar menuBar;

    public WarehousePanel getMapPanel() {
        return mapPanel;
    }
    public List<Result> getLevelResults() {
        return results.getLevelResults(levelNumber);
    }
    public Results getResults() {
        return results;
    }

    public JLabel getMovesLabel() {
        return movesLabel;
    }
    public JLabel getPushesLabel() {
        return pushesLabel;
    }
    public JLabel getTimeLabel() {
        return timeLabel;
    }
    public JLabel getLevelNameLabel() {
        return levelNameLabel;
    }
    public JLabel getAuthorLabel() {
        return authorLabel;
    }
    public JLabel getLevelSolvedLabel() {
        return levelSolvedLabel;
    }

    public JButton getPrevLevelButton() {
        return prevLevelButton;
    }
    public JButton getNextLevelButton() {
        return nextLevelButton;
    }
    public JButton getMoveLeftButton() {
        return moveLeftButton;
    }
    public JButton getMoveRightButton() {
        return moveRightButton;
    }
    public JButton getMoveUpButton() {
        return moveUpButton;
    }
    public JButton getMoveDownButton() {
        return moveDownButton;
    }

    public JPanel getTitlePanel() {
        return titlePanel;
    }
    public JPanel getStatPanel() {
        return statPanel;
    }
    public JPanel getControlPanel() {
        return controlPanel;
    }
    public JPanel getWesternPanel() {
        return westernPanel;
    }

    public void initData() throws IOException,
            ParserConfigurationException, SAXException {
        collName = "Pufiban";
        FileInputStream fis = new FileInputStream(
                "levels"+File.separator+collName+".sok");
        LevelCollectionHandler lch = new LevelCollectionHandler();
        levelCollection = lch.loadLevelCollection(fis);
        levelNumber = 0;
        results = ResultsHandler.loadResults(
                new File("results"+File.separator+collName+"_results.xml"));
        results.setNLevels(levelCollection.getLevelCount());

        pictures = ImageIO.read(new File("pictures.png"));
    }

    public void initButtons() {
        prevLevelButton = new JButton("Previous level");
        prevLevelButton.setActionCommand("previousLevel");
        prevLevelButton.setFocusable(false);
        nextLevelButton = new JButton("Next Level");
        nextLevelButton.setActionCommand("nextLevel");
        nextLevelButton.setFocusable(false);

        moveLeftButton = new JButton("<");
        moveLeftButton.setActionCommand("moveLeft");
        moveLeftButton.setFocusable(false);
        moveRightButton = new JButton(">");
        moveRightButton.setActionCommand("moveRight");
        moveRightButton.setFocusable(false);
        moveUpButton = new JButton("^");
        moveUpButton.setActionCommand("moveUp");
        moveUpButton.setFocusable(false);
        moveDownButton = new JButton("v");
        moveDownButton.setActionCommand("moveDown");
        moveDownButton.setFocusable(false);

        ActionListener bl = new ButtonListener(this);
        prevLevelButton.addActionListener(bl);
        nextLevelButton.addActionListener(bl);
        moveLeftButton.addActionListener(bl);
        moveRightButton.addActionListener(bl);
        moveUpButton.addActionListener(bl);
        moveDownButton.addActionListener(bl);
    }

    public void initMenus() {
        menuBar = new JMenuBar();
        JMenu loadMenu = new JMenu("Load...");
        JMenuItem loadMenuItem = new JMenuItem("Load level collection");
        loadMenuItem.setActionCommand("loadCollection");
        ActionListener ml = new MenuListener(this);
        loadMenuItem.addActionListener(ml);
        loadMenu.add(loadMenuItem);
        menuBar.add(loadMenu);

        JMenu restartMenu = new JMenu("Reset");
        JMenuItem restartMenuItem = new JMenuItem("Restart level");
        restartMenuItem.setActionCommand("resetLevel");
        restartMenuItem.addActionListener(ml);
        restartMenu.add(restartMenuItem);
        menuBar.add(restartMenu);

        JMenu resultsMenu = new JMenu("Results");
        JMenuItem resultsMenuItem = new JMenuItem("Level results");
        resultsMenuItem.setActionCommand("showLevelResults");
        resultsMenuItem.addActionListener(ml);
        resultsMenu.add(resultsMenuItem);
        menuBar.add(resultsMenu);
    }

    public void initPanels() {
        mapPanel = new WarehousePanel(pictures);
        mapPanel.setSize(new Dimension(600, 480));
        mapPanel.setFocusable(true);
        KeyListener keyListener = new KeyEventsAdapter(this, mapPanel);
        this.addKeyListener(keyListener);
        this.setFocusable(true);

        levelNameLabel = new JLabel();
        authorLabel = new JLabel();
        titlePanel = new JPanel();
        titlePanel.add(levelNameLabel);
        titlePanel.add(authorLabel);
        titlePanel.setSize(new Dimension(780, 60));

        movesLabel = new JLabel();
        pushesLabel = new JLabel();
        timeLabel = new JLabel();
        levelSolvedLabel = new JLabel(
                "You have solved this level. Congratulations!");
        levelSolvedLabel.setVisible(false);
        statPanel = new JPanel();
        statPanel.add(movesLabel);
        statPanel.add(pushesLabel);
        statPanel.add(timeLabel);
        statPanel.add(levelSolvedLabel);
        statPanel.setSize(new Dimension(780, 60));

        westernPanel = new JPanel();
        westernPanel.setSize(new Dimension(90, 480));

        controlPanel = new JPanel();
        controlPanel.add(prevLevelButton);
        controlPanel.add(nextLevelButton);
        controlPanel.add(moveLeftButton);
        controlPanel.add(moveRightButton);
        controlPanel.add(moveUpButton);
        controlPanel.add(moveDownButton);
        controlPanel.setSize(new Dimension(90, 480));
    }

    public void initFrame() {
        initButtons();
        initMenus();
        initPanels();

        this.setJMenuBar(menuBar);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(statPanel, BorderLayout.SOUTH);
        this.add(westernPanel, BorderLayout.WEST);
        this.add(controlPanel, BorderLayout.EAST);
        this.add(mapPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLevel(0);
        this.setVisible(true);
    }

    public GameFrame() throws IOException,
            ParserConfigurationException, SAXException {
        super("Sokoban Game");
        initData();
        initFrame();
    }

    public void loadResults()
            throws IOException, SAXException, ParserConfigurationException {
        String resultsFilename = "results"+File.separator+collName+"_results.xml";
        results = ResultsHandler.loadResults(new File(resultsFilename));
    }

    public void loadCollection()
            throws IOException, SAXException, ParserConfigurationException {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("levels").getAbsoluteFile());
        int returnVal = chooser.showDialog(this, "Select");
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File collFile = chooser.getSelectedFile().getAbsoluteFile();
        collName = collFile.getName().
                substring(0, collFile.getName().length()-4);
        FileInputStream fis = new FileInputStream(collFile);
        LevelCollectionHandler lch = new LevelCollectionHandler();
        levelCollection = lch.loadLevelCollection(fis);
        levelNumber = 0;
        mapPanel.setState(new LevelState(
                levelCollection.getLevel(0).getMap()));

        loadResults();
        results.setNLevels(levelCollection.getLevelCount());
    }

    public void setLevel(int levelNum) throws IllegalArgumentException {
        if (levelNum < 0 || levelNum >= levelCollection.getLevelCount()) {
            throw new IllegalArgumentException("There is no level with this index.");
        }
        levelNumber = levelNum;
        Level level = levelCollection.getLevel(levelNumber);
        mapPanel.setState(new LevelState(level.getMap()));

        levelNameLabel.setText("Level: "+level.getTitle());
        authorLabel.setText("Author: "+level.getAuthor());
    }

    public void refreshStatLabels() {
        LevelState state = mapPanel.getState();
        movesLabel.setText("Moves: "+state.getMoveCount());
        pushesLabel.setText("Pushes: "+state.getPushCount());
        timeLabel.setText("Time: "+state.getTime());
    }

    public void setPreviousLevel() {
        if (levelNumber > 0) {
            levelNumber--;
            setLevel(levelNumber);
        }
    }

    public void setNextLevel() {
        if (levelNumber < levelCollection.getLevelCount()-1) {
            levelNumber++;
            setLevel(levelNumber);
        }
    }

    public static void main(String[] args) {
        try {
            GameFrame gameWindow = new GameFrame();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
