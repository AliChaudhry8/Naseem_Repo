package BussinessObjects;

import java.io.Serializable;

/**
 * Created by Muhammad Taimoor on 8/2/2017.
 */

public class Parent_Test_BO implements Serializable {
    private int id;
    private String name;
    private int taken;

    public Parent_Test_BO() {
    }

    public Parent_Test_BO(int id, String name, int taken) {
        this.id = id;
        this.name = name;
        this.taken = taken;
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

    public int getTaken() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }
}
