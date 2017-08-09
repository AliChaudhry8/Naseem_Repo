package BussinessObjects;

/**
 * Created by Muhammad Taimoor on 8/9/2017.
 */

public class Principal_Students_Test_Attempts_BO {
    private int taken;
    private int user_id;
    private String first_name;
    private String second_name;

    public Principal_Students_Test_Attempts_BO() {
    }

    public Principal_Students_Test_Attempts_BO(int taken, int user_id, String first_name, String second_name) {
        this.taken = taken;
        this.user_id = user_id;
        this.first_name = first_name;
        this.second_name = second_name;
    }

    public int getTaken() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }
}
