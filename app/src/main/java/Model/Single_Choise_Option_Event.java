package Model;

import BussinessObjects.Show_Test_BO.Single_Choice_Options;

/**
 * Created by DELL on 10/1/2017.
 */

public class Single_Choise_Option_Event {

    private Single_Choice_Options sco;

    public Single_Choise_Option_Event(){

    }

    public Single_Choice_Options getSco() {
        return sco;
    }

    public void setSco(Single_Choice_Options sco) {
        this.sco = sco;
    }

    public Single_Choise_Option_Event(Single_Choice_Options sco) {
        this.sco = sco;
    }
}
