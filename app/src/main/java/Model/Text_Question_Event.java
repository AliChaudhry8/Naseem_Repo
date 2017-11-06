package Model;

import BussinessObjects.Show_Test_BO.Text_Questions;

/**
 * Created by DELL on 9/28/2017.
 */

public class Text_Question_Event {
    private Text_Questions testQ;

    public Text_Question_Event(){

    }

    public Text_Question_Event(Text_Questions testQ) {
        this.testQ = testQ;
    }

    public Text_Questions getTestQ() {
        return testQ;
    }

    public void setTestQ(Text_Questions testQ) {
        this.testQ = testQ;
    }


}
