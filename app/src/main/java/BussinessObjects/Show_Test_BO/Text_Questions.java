package BussinessObjects.Show_Test_BO;

/**
 * Created by Muhammad Taimoor on 7/4/2017.
 */

public class Text_Questions {

    private int id;
    private String title;
    private int test_id;
    private int marks;
    private String answer;
    private String std_answer;

    public Text_Questions() {}

    public Text_Questions(int id, String title, int test_id, int marks, String answer, String std_answer) {
        this.id = id;
        this.title = title;
        this.test_id = test_id;
        this.marks = marks;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStd_answer() {
        return std_answer;
    }

    public void setStd_answer(String std_answer) {
        this.std_answer = std_answer;
    }
}
