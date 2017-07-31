package BussinessObjects;

import java.io.Serializable;

/**
 * Created by Muhammad Taimoor on 7/3/2017.
 */

public class Game_BO implements Serializable{
    private String name;
    private String url;

    public Game_BO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game_BO(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
