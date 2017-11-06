package Model;

import BussinessObjects.Show_Test_BO.ParagraphQuestions;

/**
 * Created by DELL on 10/2/2017.
 */

public class Paragraph_Question_Event {
    private ParagraphQuestions pgh;

    public ParagraphQuestions getPgh() {
        return pgh;
    }

    public void setPgh(ParagraphQuestions pgh) {
        this.pgh = pgh;
    }

    public Paragraph_Question_Event(){

    }

    public Paragraph_Question_Event(ParagraphQuestions pgh) {
        this.pgh = pgh;
    }
}
