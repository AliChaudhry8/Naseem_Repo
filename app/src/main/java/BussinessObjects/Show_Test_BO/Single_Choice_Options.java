package BussinessObjects.Show_Test_BO;

/**
 * Created by Muhammad Taimoor on 7/6/2017.
 */

public class Single_Choice_Options {
    private int id;
    private String option;
    private boolean correct;
    private int question_id;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Single_Choice_Options(int id, String option, boolean correct, int question_id) {
        this.id = id;
        this.option = option;
        this.correct = correct;
        this.question_id = question_id;
    }

    public Single_Choice_Options() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
