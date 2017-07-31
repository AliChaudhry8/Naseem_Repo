package BussinessObjects;

import java.io.Serializable;

/**
 * Created by Muhammad Taimoor on 5/29/2017.
 */

public class Test_BO implements Serializable{
    private int id;
    private String name;
    private String start_time;
    private String attempt_time;
    private int teacher_id;

    public Test_BO(int id, String name, String start_time, String attempt_time, int teacher_id) {
        this.id = id;
        this.name = name;
        this.start_time = start_time;
        this.attempt_time = attempt_time;
        this.teacher_id = teacher_id;
    }

    public Test_BO() {
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getAttempt_time() {
        return attempt_time;
    }

    public void setAttempt_time(String attempt_time) {
        this.attempt_time = attempt_time;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
