package BussinessObjects;

import java.io.Serializable;

/**
 * Created by Muhammad Taimoor on 8/3/2017.
 */

public class Principal_Test_BO implements Serializable{
    private int id;
    private int status;
    private String name;
    private String teacher_name;
    private String start_time;

    public Principal_Test_BO() {
    }

    public Principal_Test_BO(int id, int status, String name, String teacher_name, String start_time) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.teacher_name = teacher_name;
        this.start_time = start_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
