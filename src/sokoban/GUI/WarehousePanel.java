package sokoban.GUI;

import sokoban.logic.Field;
import sokoban.logic.Field.FieldType;
import sokoban.logic.LevelState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WarehousePanel extends JPanel {
    private final int loadImageSize = 64;
    private final int drawImageSize = 64;
    private BufferedImage pictures;
    private LevelState state;

    public WarehousePanel(BufferedImage pictures) {
        this.pictures = pictures;
        this.state = state;
    }

    public LevelState getState() {
        return state;
    }
    public void setState(LevelState state) {
        this.state = state;
    }

    int pictureSeq(FieldType type) {
        switch(type) {
            case WALL: return 1;
            case PLAYER: return 6;
            case PLAYER_GOAL: return 7;
            case BOX: return 4;
            case BOX_GOAL: return 5;
            case GOAL: return 3;
            case FLOOR: return 2;
            default: return 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<ArrayList<Field>> fields = state.getMap().getFields();
        int nRows = fields.size();
        int nCols = fields.get(0).size();
        this.setMinimumSize(new Dimension(
                nCols * drawImageSize, nRows * drawImageSize));
        this.setSize(new Dimension(
                nCols * drawImageSize, nRows * drawImageSize));

        for (int row = 0; row < nRows; row++) {
            ArrayList<Field> rowFields = fields.get(row);
            for (int col = 0; col < nCols; col++) {
                int typeSeq = pictureSeq(rowFields.get(col).getType());

                Image picture = pictures.getSubimage(typeSeq * loadImageSize,
                        0, loadImageSize, loadImageSize);
                g.drawImage(picture, col * drawImageSize,
                        row * drawImageSize, null);
            }
        }
    }
}
