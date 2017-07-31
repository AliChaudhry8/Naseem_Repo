package BussinessObjects;

import java.util.ArrayList;

/**
 * Created by Muhammad Taimoor on 5/29/2017.
 */

public class Test_Taken_Schedule {
    private ArrayList<Test_BO> test_taken;
    private ArrayList<Test_BO> test_schedule;

    public Test_Taken_Schedule(ArrayList<Test_BO> test_taken, ArrayList<Test_BO> test_schedule) {
        this.test_taken = test_taken;
        this.test_schedule = test_schedule;
    }

    public Test_Taken_Schedule() {
    }

    public ArrayList<Test_BO> getTest_taken() {
        return test_taken;
    }

    public void setTest_taken(ArrayList<Test_BO> test_taken) {
        this.test_taken = test_taken;
    }

    public ArrayList<Test_BO> getTest_schedule() {
        return test_schedule;
    }

    public void setTest_schedule(ArrayList<Test_BO> test_schedule) {
        this.test_schedule = test_schedule;
    }
}
