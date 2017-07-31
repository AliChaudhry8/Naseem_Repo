package BussinessObjects;

/**
 * Created by Muhammad Taimoor on 5/23/2017.
 */

public class Section_BO {
    private int id;
    private String name;
    private int school_id;

    public Section_BO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }
}
