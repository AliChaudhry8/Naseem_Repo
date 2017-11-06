package Model;

import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;

/**
 * Created by DELL on 10/1/2017.
 */

public class Single_Choise_Question_Event {
    private SingleChoiceQuestions scq;

    public Single_Choise_Question_Event(){

    }

    public SingleChoiceQuestions getScq() {
        return scq;
    }

    public void setScq(SingleChoiceQuestions scq) {
        this.scq = scq;
    }

    public Single_Choise_Question_Event(SingleChoiceQuestions scq) {
        this.scq = scq;
    }
}
