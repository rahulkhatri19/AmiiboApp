package in.khatri.rahul.amiiboapp.java.fastNetworking.model;

public class GameModel {
   private String amiiboSeries, character, gameSeries, head, image, name, tail, type, release, au, eu, jp, na;

    public GameModel(String amiiboSeries, String character, String gameSeries, String head, String image, String name, String tail, String type, String au, String eu, String jp, String na) {
        this.amiiboSeries = amiiboSeries;
        this.character = character;
        this.gameSeries = gameSeries;
        this.head = head;
        this.image = image;
        this.name = name;
        this.tail = tail;
        this.type = type;
        this.au = au;
        this.eu = eu;
        this.jp = jp;
        this.na = na;
    }

    public String getAmiiboSeries() {
        return amiiboSeries;
    }

    public String getCharacter() {
        return character;
    }

    public String getGameSeries() {
        return gameSeries;
    }

    public String getHead() {
        return head;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getTail() {
        return tail;
    }

    public String getType() {
        return type;
    }

    public String getRelease() {
        return release;
    }

    public String getAu() {
        return au;
    }

    public String getEu() {
        return eu;
    }

    public String getJp() {
        return jp;
    }

    public String getNa() {
        return na;
    }
}
