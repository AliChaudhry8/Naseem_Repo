package Model;

import BussinessObjects.Show_Test_BO.BooleanQuestions;

/**
 * Created by DELL on 10/2/2017.
 */

public class Bool_Question_Event {
    private BooleanQuestions bol;

    public Bool_Question_Event(){

    }

    public BooleanQuestions getBol() {
        return bol;
    }

    public void setBol(BooleanQuestions bol) {
        this.bol = bol;
    }

    public Bool_Question_Event(BooleanQuestions bol) {
        this.bol = bol;
    }
}
