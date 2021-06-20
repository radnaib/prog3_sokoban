package sokoban.logic;

import java.util.List;

public class LevelCollection {

    private String name;
    private String author;
    private String copyright;
    private String email;
    private String homepage;
    private List<Level> levels;

    public LevelCollection(String name, String author, String copyright,
                           String email, String homepage, List<Level> levels) {
        this.name = name;
        this.author = author;
        this.copyright = copyright;
        this.email = email;
        this.homepage = homepage;
        this.levels = levels;
    }

    public int getLevelCount() {
        return levels.size();
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getCopyright() {
        return copyright;
    }
    public String getEmail() {
        return email;
    }
    public String getHomepage() {
        return homepage;
    }

    public Level getLevel(int idx) {
        return levels.get(idx);
    }
    public List<Level> getLevels() {
        return levels;
    }

}
