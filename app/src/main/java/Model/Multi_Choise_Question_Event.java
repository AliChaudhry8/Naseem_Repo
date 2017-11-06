package Model;

import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;

/**
 * Created by DELL on 10/2/2017.
 */

public class Multi_Choise_Question_Event {
    private MultipleChoiceQuestions mcq;

    public Multi_Choise_Question_Event(){

    }

    public MultipleChoiceQuestions getMcq() {
        return mcq;
    }

    public void setMcq(MultipleChoiceQuestions mcq) {
        this.mcq = mcq;
    }

    public Multi_Choise_Question_Event(MultipleChoiceQuestions mcq) {
        this.mcq = mcq;
    }
}
