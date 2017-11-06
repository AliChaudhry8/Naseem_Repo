package Model;

import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;

/**
 * Created by DELL on 10/2/2017.
 */

public class Multi_Choise_Option_Event {
    private Multi_Choice_Options mco;

    public Multi_Choise_Option_Event(){

    }

    public Multi_Choice_Options getMco() {
        return mco;
    }

    public void setMco(Multi_Choice_Options mco) {
        this.mco = mco;
    }

    public Multi_Choise_Option_Event(Multi_Choice_Options mco) {
        this.mco = mco;
    }
}
