package sokoban.IO;

import static sokoban.logic.Field.FieldType.*;

import sokoban.logic.*;
import sokoban.logic.Field.FieldType;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelCollectionHandler {
    private Scanner sc;

    private FieldType charToFieldType(char ch) {
        switch(ch) {
            case '#': return WALL;
            case '@': return PLAYER;
            case '+': return PLAYER_GOAL;
            case '$': return BOX;
            case '*': return BOX_GOAL;
            case '.': return GOAL;
            default: return FLOOR;
        }
    }

    private Warehouse loadWarehouse() {
        sc.useDelimiter("(?:)((?=)(\\s*Title:\\s*))");
        String warehouseStr = sc.next();

        Scanner scWh = new Scanner(warehouseStr);
        int cols = 0;
        List<String> lines = new ArrayList<String>();
        while (scWh.hasNextLine()) {
            String line = scWh.nextLine();
            lines.add(line);
            if (line.length() > cols) {
                cols = line.length();
            }
        }
        scWh.close();

        ArrayList<ArrayList<Field>> fields = new ArrayList<ArrayList<Field>>();
        Pos playerPos = new Pos(-1, -1);
        int nLines = lines.size();
        for (int row = 0; row < nLines; row++) {
            ArrayList<Field> rowFields = new ArrayList<Field>();
            String line = lines.get(row);
            int len = line.length();

            for (int col = 0; col < len; col++) {
                FieldType fieldType = charToFieldType(line.charAt(col));
                Field field = new Field(fieldType);
                rowFields.add(field);
                if (field.hasPlayer()) {
                    playerPos = new Pos(row, col);
                }
            }
            for (int col = len; col < cols; col++) {
                rowFields.add(new Field(FLOOR));
            }
            fields.add(rowFields);
        }
        return new Warehouse(fields, playerPos);
    }

    private Level loadLevel() {
        int seq = Integer.parseInt(sc.nextLine());
        Warehouse map = loadWarehouse();
        sc.useDelimiter("\\s*Title:\\s*");
        sc.skip(sc.delimiter());
        sc.useDelimiter("\\s*Author:\\s*");
        String title = sc.next();
        sc.skip(sc.delimiter());
        sc.useDelimiter("\\s*Comment:\\s*");
        String author = sc.next();
        sc.skip(sc.delimiter());
        sc.useDelimiter("\\s*Comment-End:\\s*");
        String comment = sc.next();
        sc.skip(sc.delimiter());

        return new Level(seq, title, author, comment, map);
    }

    public LevelCollection loadLevelCollection(FileInputStream fis) {
        sc = new Scanner(fis);

        sc.useDelimiter("\\s*Set:\\s*");
        sc.next();
        sc.skip(sc.delimiter());
        sc.useDelimiter("\\s*Copyright:\\s*");
        String name = sc.next();
        sc.skip(sc.delimiter());
        sc.useDelimiter("\\s*Email:\\s*");
        String copyright = sc.next();
        sc.skip(sc.delimiter());
        sc.useDelimiter("\\s*Homepage:\\s*");
        String email = sc.next();
        sc.skip(sc.delimiter());
        String homepage = sc.nextLine();
        sc.useDelimiter("\\:{2,}\\s+");
        sc.next();
        sc.skip(sc.delimiter());

        List<Level> levels = new ArrayList<Level>();
        while (sc.hasNext()) {
            levels.add(loadLevel());
        }

        sc.close();
        return new LevelCollection(name, copyright,
                copyright, email, homepage, levels);
    }

}
