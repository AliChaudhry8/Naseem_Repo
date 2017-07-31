package BussinessObjects.Show_Test_BO;

/**
 * Created by Muhammad Taimoor on 7/4/2017.
 */

public class BooleanQuestions {
    private int id;
    private String title;
    private boolean correct;
    private int test_id;
    private int marks;
    private boolean std_answer;


    public BooleanQuestions() {
    }

    public BooleanQuestions(int id, String title, boolean correct, int test_id, int marks, boolean std_answer) {
        this.id = id;
        this.title = title;
        this.correct = correct;
        this.test_id = test_id;
        this.marks = marks;
        this.std_answer = std_answer;
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

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
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

    public boolean isStd_answer() {
        return std_answer;
    }

    public void setStd_answer(boolean std_answer) {
        this.std_answer = std_answer;
    }
}
