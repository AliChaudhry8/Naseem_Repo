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
    private int checkMeta;

    private String subject;
    private String grade;
    private String topic;
    private String subtopic;
    private String inputmode;
    private String presentationmode;
    private String cognitivefaculty;
    private String steps;
    private String teacherdifficulty;
    private String deviation;
    private String ambiguity;
    private String clarity;
    private String blooms;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }

    public String getInputmode() {
        return inputmode;
    }

    public void setInputmode(String inputmode) {
        this.inputmode = inputmode;
    }

    public String getPresentationmode() {
        return presentationmode;
    }

    public void setPresentationmode(String presentationmode) {
        this.presentationmode = presentationmode;
    }

    public String getCognitivefaculty() {
        return cognitivefaculty;
    }

    public void setCognitivefaculty(String cognitivefaculty) {
        this.cognitivefaculty = cognitivefaculty;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getTeacherdifficulty() {
        return teacherdifficulty;
    }

    public void setTeacherdifficulty(String teacherdifficulty) {
        this.teacherdifficulty = teacherdifficulty;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public String getAmbiguity() {
        return ambiguity;
    }

    public void setAmbiguity(String ambiguity) {
        this.ambiguity = ambiguity;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getBlooms() {
        return blooms;
    }

    public void setBlooms(String blooms) {
        this.blooms = blooms;
    }

    public int getCheckMeta() {
        return checkMeta;
    }

    public void setCheckMeta(int checkMeta) {
        this.checkMeta = checkMeta;
    }

    public Text_Questions() {
        subject = "";
        grade = "";
        topic = "";
        subtopic = "";
        inputmode = "";
        presentationmode = "";
        cognitivefaculty = "";
        steps = "";
        teacherdifficulty = "";
        deviation = "";
        ambiguity = "";
        clarity = "";
        blooms = "";
    }

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
