package BussinessObjects.Show_Test_BO;

/**
 * Created by Muhammad Taimoor on 7/4/2017.
 */

public class ParagraphQuestions {
    private int id;
    private String title;
    private int test_id;
    private int marks;
    private Paragraph_Answer answer;

    public ParagraphQuestions() {}

    public ParagraphQuestions(int id, String title, int test_id, int marks, Paragraph_Answer answer) {
        this.id = id;
        this.title = title;
        this.test_id = test_id;
        this.marks = marks;
        this.answer = answer;
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

    public Paragraph_Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Paragraph_Answer answer) {
        this.answer = answer;
    }
}
