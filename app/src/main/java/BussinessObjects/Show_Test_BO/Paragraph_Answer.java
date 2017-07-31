package BussinessObjects.Show_Test_BO;

/**
 * Created by Muhammad Taimoor on 7/6/2017.
 */

public class Paragraph_Answer {
    private int id;
    private String answer;
    private int marks;

    public Paragraph_Answer() {
    }

    public Paragraph_Answer(int id, String answer, int marks) {
        this.id = id;
        this.answer = answer;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
