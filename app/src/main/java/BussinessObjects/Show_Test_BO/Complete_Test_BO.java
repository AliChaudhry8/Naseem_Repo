package BussinessObjects.Show_Test_BO;

import java.util.ArrayList;

/**
 * Created by Muhammad Taimoor on 7/4/2017.
 */

public class Complete_Test_BO {

    private ArrayList<Text_Questions> text_questions;
    private ArrayList<SingleChoiceQuestions> singleChoiceQuestions;
    private ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions;
    private ArrayList<ParagraphQuestions> paragraphQuestions;
    private ArrayList<BooleanQuestions> booleanQuestions;
    private ArrayList<Image_Questions> imagesQuestions;

    public ArrayList<Image_Questions> getImagesQuestions() {
        return imagesQuestions;
    }

    public void setImagesQuestions(ArrayList<Image_Questions> imagesQuestions) {
        this.imagesQuestions = imagesQuestions;
    }

    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Complete_Test_BO() {
        text_questions = new ArrayList<Text_Questions>();
        singleChoiceQuestions = new ArrayList<SingleChoiceQuestions>();
        multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestions>();
        paragraphQuestions = new ArrayList<ParagraphQuestions>();
        booleanQuestions = new ArrayList<BooleanQuestions>();
        imagesQuestions = new ArrayList<Image_Questions>();
        i = 0;
    }

    public Complete_Test_BO(ArrayList<Text_Questions> text_questions, ArrayList<SingleChoiceQuestions> singleChoiceQuestions, ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions, ArrayList<ParagraphQuestions> paragraphQuestions, ArrayList<BooleanQuestions> booleanQuestions  , ArrayList<Image_Questions> image_questionses) {
        text_questions = new ArrayList<Text_Questions>();
        singleChoiceQuestions = new ArrayList<SingleChoiceQuestions>();
        multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestions>();
        paragraphQuestions = new ArrayList<ParagraphQuestions>();
        booleanQuestions = new ArrayList<BooleanQuestions>();
        imagesQuestions = new ArrayList<Image_Questions>();
        this.text_questions = text_questions;
        this.singleChoiceQuestions = singleChoiceQuestions;
        this.multipleChoiceQuestions = multipleChoiceQuestions;
        this.paragraphQuestions = paragraphQuestions;
        this.booleanQuestions = booleanQuestions;
        this.imagesQuestions = image_questionses;

        i = 0;
    }

    public ArrayList<Text_Questions> getText_questions() {
        return text_questions;
    }

    public void setText_questions(ArrayList<Text_Questions> text_questions) {
        this.text_questions = text_questions;
    }

    public ArrayList<SingleChoiceQuestions> getSingleChoiceQuestions() {
        return singleChoiceQuestions;
    }

    public void setSingleChoiceQuestions(ArrayList<SingleChoiceQuestions> singleChoiceQuestions) {
        this.singleChoiceQuestions = singleChoiceQuestions;
    }

    public ArrayList<MultipleChoiceQuestions> getMultipleChoiceQuestions() {
        return multipleChoiceQuestions;
    }

    public void setMultipleChoiceQuestions(ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions) {
        this.multipleChoiceQuestions = multipleChoiceQuestions;
    }

    public ArrayList<ParagraphQuestions> getParagraphQuestions() {
        return paragraphQuestions;
    }

    public void setParagraphQuestions(ArrayList<ParagraphQuestions> paragraphQuestions) {
        this.paragraphQuestions = paragraphQuestions;
    }

    public ArrayList<BooleanQuestions> getBooleanQuestions() {
        return booleanQuestions;
    }

    public void setBooleanQuestions(ArrayList<BooleanQuestions> booleanQuestions) {
        this.booleanQuestions = booleanQuestions;
    }
}
