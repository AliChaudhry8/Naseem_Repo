package BussinessObjects;

/**
 * Created by DELL on 9/20/2017.
 */

public class Group_BO {
    private int id;
    private String name;
    private int teacher_id;
    private boolean is_selected;

    public boolean is_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public Group_BO(){

    }

    public Group_BO(int id, String name, int teacher_id) {
        this.id = id;
        this.name = name;
        this.teacher_id = teacher_id;
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

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
