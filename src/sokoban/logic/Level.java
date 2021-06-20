package sokoban.logic;

public class Level {

    private int sequenceNumber;
    private String title;
    private String author;
    private String comment;
    private Warehouse map;

    public Level(int sequenceNumber, String title,
                 String author, String comment, Warehouse map) {
        this.sequenceNumber = sequenceNumber;
        this.title = title;
        this.author = author;
        this.comment = comment;
        this.map = map;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getComment() {
        return comment;
    }
    public Warehouse getMap() {
        return map;
    }

}
