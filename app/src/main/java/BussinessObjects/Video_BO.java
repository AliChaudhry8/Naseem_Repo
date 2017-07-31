package BussinessObjects;

/**
 * Created by Muhammad Taimoor on 6/2/2017.
 */

public class Video_BO {
    private String link;
    private String desc;

    public Video_BO(String link, String desc) {
        this.link = link;
        this.desc = desc;
    }

    public Video_BO() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
