package in.khatri.rahul.amiiboapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameRetrofitModel {

    @SerializedName("amiiboSeries")
    @Expose
    private String amiiboSeries;

    @SerializedName("character")
    @Expose
    private String character;

    @SerializedName("gameSeries")
    @Expose
    private String gameSeries;

    @SerializedName("head")
    @Expose
    private String head;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("tail")
    @Expose
    private String tail;

    @SerializedName("type")
    @Expose
    private String type;

    /*@SerializedName("release")
    @Expose
    private String release;

    @SerializedName("au")
    @Expose
    private String au;

    @SerializedName("eu")
    @Expose
    private String eu;

    @SerializedName("na")
    @Expose
    private String na;

    @SerializedName("jp")
    @Expose
    private String jp;*/

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

  /*  public String getRelease() {
        return release;
    }

    public String getAu() {
        return au;
    }

    public void setAu(String au) {
        this.au = au;
    }

    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }*/
}
