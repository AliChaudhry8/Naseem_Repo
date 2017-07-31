package BussinessObjects.Show_Test_BO;

import java.util.ArrayList;

/**
 * Created by Muhammad Taimoor on 7/4/2017.
 */

public class MultipleChoiceQuestions {
    private int id;
    private String title;
    private int test_id;
    private int marks;
    private ArrayList<String> std_answer;
    private ArrayList<Multi_Choice_Options> options;

    public MultipleChoiceQuestions() {}

    public MultipleChoiceQuestions(int id, String title, int test_id, int marks, ArrayList<String> std_answer, ArrayList<Multi_Choice_Options> options) {
        this.id = id;
        this.title = title;
        this.test_id = test_id;
        this.marks = marks;
        this.std_answer = std_answer;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public ArrayList<String> getStd_answer() {
        return std_answer;
    }

    public void setStd_answer(ArrayList<String> std_answer) {
        this.std_answer = std_answer;
    }

    public ArrayList<Multi_Choice_Options> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Multi_Choice_Options> options) {
        this.options = options;
    }
}
