package in.khatri.rahul.amiiboapp.java.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Release {
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
    private String jp;

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
    }
}
