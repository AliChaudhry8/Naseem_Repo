package Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.BooleanQuestions;
import BussinessObjects.Show_Test_BO.Complete_Test_BO;
import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import BussinessObjects.Show_Test_BO.ParagraphQuestions;
import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import BussinessObjects.Show_Test_BO.Text_Questions;

/**
 * Created by DELL on 9/30/2017.
 */

public class JsonConverter {
    public JsonConverter(){}

    public JSONObject convert_Text_Questions(ArrayList<Text_Questions> text){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            for(int i = 0; i < text.size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("title", (Object) text.get(i).getTitle());
                jsonObject.put("answer", (Object) text.get(i).getAnswer());
                jsonObject.put("marks", (Object) text.get(i).getMarks());
                jsonArray.put(jsonObject);
                jsonObject1.put("text_questions", jsonArray);
            }
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject convert_Single_Choise_Questions(ArrayList<SingleChoiceQuestions> scq){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 ;
        JSONObject jsonObject3 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        try{
            for(int i = 0; i < scq.size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("title", (Object) scq.get(i).getTitle());
                jsonObject.put("marks", (Object) scq.get(i).getMarks());
                for(int j = 0 ; j < scq.get(i).getOptions().size() ; j++){
                    jsonObject2 = new JSONObject();
                    jsonObject2.put("option" , scq.get(i).getOptions().get(j).getOption());
                    jsonObject2.put("correct" , scq.get(i).getOptions().get(j).getCorrect());
                    jsonArray1.put(jsonObject2);
                    jsonObject3.put("Options" , jsonArray1);
                }
                jsonArray.put(jsonObject);
                jsonObject1.put("text_questions", jsonArray);
            }
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject convert_Test_Object(Complete_Test_BO test){
        JSONObject complete_test = new JSONObject();
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 ;
        JSONObject jsonObject3 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        try{
            for(int i = 0; i < test.getText_questions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("title", (Object) test.getText_questions().get(i).getTitle());
                jsonObject.put("answer", (Object) test.getText_questions().get(i).getAnswer());
                jsonObject.put("marks", (Object) test.getText_questions().get(i).getMarks());
                jsonObject.put("subject", (Object) test.getText_questions().get(i).getSubject());
                jsonObject.put("topic", (Object) test.getText_questions().get(i).getTopic());
                jsonObject.put("subtopic", (Object) test.getText_questions().get(i).getSubtopic());
                jsonObject.put("grade", (Object) test.getText_questions().get(i).getGrade());
                jsonObject.put("inputmode", (Object) test.getText_questions().get(i).getInputmode());
                jsonObject.put("presentationmode", (Object) test.getText_questions().get(i).getPresentationmode());
                jsonObject.put("cognitivefaculty", (Object) test.getText_questions().get(i).getCognitivefaculty());
                jsonObject.put("steps", (Object) test.getText_questions().get(i).getSteps());
                jsonObject.put("teacherdifficulty", (Object) test.getText_questions().get(i).getTeacherdifficulty());
                jsonObject.put("deviation", (Object) test.getText_questions().get(i).getDeviation());
                jsonObject.put("ambiguity", (Object) test.getText_questions().get(i).getAmbiguity());
                jsonObject.put("clarity", (Object) test.getText_questions().get(i).getClarity());
                jsonObject.put("blooms", (Object) test.getText_questions().get(i).getBlooms());

                jsonArray.put(jsonObject);
                complete_test.put("text_questions", jsonArray);
            }

            jsonArray = new JSONArray();
            //jsonObject2 = new JSONObject();

            for(int i = 0; i < test.getSingleChoiceQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonArray1 = new JSONArray();
                jsonObject.put("title", (Object) test.getSingleChoiceQuestions().get(i).getTitle());
                jsonObject.put("marks", (Object) test.getSingleChoiceQuestions().get(i).getMarks());
                jsonObject.put("subject", (Object) test.getSingleChoiceQuestions().get(i).getSubject());
                jsonObject.put("topic", (Object) test.getSingleChoiceQuestions().get(i).getTopic());
                jsonObject.put("subtopic", (Object) test.getSingleChoiceQuestions().get(i).getSubtopic());
                jsonObject.put("grade", (Object) test.getSingleChoiceQuestions().get(i).getGrade());
                jsonObject.put("inputmode", (Object) test.getSingleChoiceQuestions().get(i).getInputmode());
                jsonObject.put("presentationmode", (Object) test.getSingleChoiceQuestions().get(i).getPresentationmode());
                jsonObject.put("cognitivefaculty", (Object) test.getSingleChoiceQuestions().get(i).getCognitivefaculty());
                jsonObject.put("steps", (Object) test.getSingleChoiceQuestions().get(i).getSteps());
                jsonObject.put("teacherdifficulty", (Object) test.getSingleChoiceQuestions().get(i).getTeacherdifficulty());
                jsonObject.put("deviation", (Object) test.getSingleChoiceQuestions().get(i).getDeviation());
                jsonObject.put("ambiguity", (Object) test.getSingleChoiceQuestions().get(i).getAmbiguity());
                jsonObject.put("clarity", (Object) test.getSingleChoiceQuestions().get(i).getClarity());
                jsonObject.put("blooms", (Object) test.getSingleChoiceQuestions().get(i).getBlooms());
                for(int j = 0 ; j < test.getSingleChoiceQuestions().get(i).getOptions().size() ; j++){
                    jsonObject2 = new JSONObject();
                    jsonObject2.put("option" , test.getSingleChoiceQuestions().get(i).getOptions().get(j).getOption());
                    jsonObject2.put("correct" , test.getSingleChoiceQuestions().get(i).getOptions().get(j).getCorrect());
                    jsonArray1.put(jsonObject2);
                    jsonObject.put("Options" , jsonArray1);
                }
                jsonArray.put(jsonObject);
                complete_test.put("Single_Choise_Questions", jsonArray);
            }

            jsonArray = new JSONArray();
            //jsonObject2 = new JSONObject();

            for(int i = 0; i < test.getMultipleChoiceQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonArray1 = new JSONArray();
                jsonObject.put("title", (Object) test.getMultipleChoiceQuestions().get(i).getTitle());
                jsonObject.put("marks", (Object) test.getMultipleChoiceQuestions().get(i).getMarks());
                jsonObject.put("subject", (Object) test.getMultipleChoiceQuestions().get(i).getSubject());
                jsonObject.put("topic", (Object) test.getMultipleChoiceQuestions().get(i).getTopic());
                jsonObject.put("subtopic", (Object) test.getMultipleChoiceQuestions().get(i).getSubtopic());
                jsonObject.put("grade", (Object) test.getMultipleChoiceQuestions().get(i).getGrade());
                jsonObject.put("inputmode", (Object) test.getMultipleChoiceQuestions().get(i).getInputmode());
                jsonObject.put("presentationmode", (Object) test.getMultipleChoiceQuestions().get(i).getPresentationmode());
                jsonObject.put("cognitivefaculty", (Object) test.getMultipleChoiceQuestions().get(i).getCognitivefaculty());
                jsonObject.put("steps", (Object) test.getMultipleChoiceQuestions().get(i).getSteps());
                jsonObject.put("teacherdifficulty", (Object) test.getMultipleChoiceQuestions().get(i).getTeacherdifficulty());
                jsonObject.put("deviation", (Object) test.getMultipleChoiceQuestions().get(i).getDeviation());
                jsonObject.put("ambiguity", (Object) test.getMultipleChoiceQuestions().get(i).getAmbiguity());
                jsonObject.put("clarity", (Object) test.getMultipleChoiceQuestions().get(i).getClarity());
                jsonObject.put("blooms", (Object) test.getMultipleChoiceQuestions().get(i).getBlooms());
                for(int j = 0 ; j < test.getMultipleChoiceQuestions().get(i).getOptions().size() ; j++){
                    jsonObject2 = new JSONObject();
                    jsonObject2.put("option" , test.getMultipleChoiceQuestions().get(i).getOptions().get(j).getOption());
                    jsonObject2.put("correct" , test.getMultipleChoiceQuestions().get(i).getOptions().get(j).isCorrect());
                    jsonArray1.put(jsonObject2);
                    jsonObject.put("Options" , jsonArray1);
                }
                jsonArray.put(jsonObject);
                complete_test.put("Multi_Choise_Questions", jsonArray);
            }
            jsonArray = new JSONArray();

            for(int i = 0; i < test.getParagraphQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("title", (Object) test.getParagraphQuestions().get(i).getTitle());
                jsonObject.put("marks", (Object) test.getParagraphQuestions().get(i).getMarks());
                jsonObject.put("subject", (Object) test.getParagraphQuestions().get(i).getSubject());
                jsonObject.put("topic", (Object) test.getParagraphQuestions().get(i).getTopic());
                jsonObject.put("subtopic", (Object) test.getParagraphQuestions().get(i).getSubtopic());
                jsonObject.put("grade", (Object) test.getParagraphQuestions().get(i).getGrade());
                jsonObject.put("inputmode", (Object) test.getParagraphQuestions().get(i).getInputmode());
                jsonObject.put("presentationmode", (Object) test.getParagraphQuestions().get(i).getPresentationmode());
                jsonObject.put("cognitivefaculty", (Object) test.getParagraphQuestions().get(i).getCognitivefaculty());
                jsonObject.put("steps", (Object) test.getParagraphQuestions().get(i).getSteps());
                jsonObject.put("teacherdifficulty", (Object) test.getParagraphQuestions().get(i).getTeacherdifficulty());
                jsonObject.put("deviation", (Object) test.getParagraphQuestions().get(i).getDeviation());
                jsonObject.put("ambiguity", (Object) test.getParagraphQuestions().get(i).getAmbiguity());
                jsonObject.put("clarity", (Object) test.getParagraphQuestions().get(i).getClarity());
                jsonObject.put("blooms", (Object) test.getParagraphQuestions().get(i).getBlooms());
                jsonArray.put(jsonObject);
                complete_test.put("paragraph_questions", jsonArray);
            }

            jsonArray = new JSONArray();

            for(int i = 0; i < test.getBooleanQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("title", (Object) test.getBooleanQuestions().get(i).getTitle());
                jsonObject.put("marks", (Object) test.getBooleanQuestions().get(i).getMarks());
                jsonObject.put("correct" , (Object) test.getBooleanQuestions().get(i).isCorrect());
                jsonObject.put("subject", (Object) test.getBooleanQuestions().get(i).getSubject());
                jsonObject.put("topic", (Object) test.getBooleanQuestions().get(i).getTopic());
                jsonObject.put("subtopic", (Object) test.getBooleanQuestions().get(i).getSubtopic());
                jsonObject.put("grade", (Object) test.getBooleanQuestions().get(i).getGrade());
                jsonObject.put("inputmode", (Object) test.getBooleanQuestions().get(i).getInputmode());
                jsonObject.put("presentationmode", (Object) test.getBooleanQuestions().get(i).getPresentationmode());
                jsonObject.put("cognitivefaculty", (Object) test.getBooleanQuestions().get(i).getCognitivefaculty());
                jsonObject.put("steps", (Object) test.getBooleanQuestions().get(i).getSteps());
                jsonObject.put("teacherdifficulty", (Object) test.getBooleanQuestions().get(i).getTeacherdifficulty());
                jsonObject.put("deviation", (Object) test.getBooleanQuestions().get(i).getDeviation());
                jsonObject.put("ambiguity", (Object) test.getBooleanQuestions().get(i).getAmbiguity());
                jsonObject.put("clarity", (Object) test.getBooleanQuestions().get(i).getClarity());
                jsonObject.put("blooms", (Object) test.getBooleanQuestions().get(i).getBlooms());
                jsonArray.put(jsonObject);
                complete_test.put("boolean_questions", jsonArray);
            }
            jsonArray = new JSONArray();
            for(int i = 0; i < test.getImagesQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("imageses" , (Object) test.getImagesQuestions().get(i).getImageKey());
                jsonObject.put("title", (Object) test.getImagesQuestions().get(i).getTitle());
                jsonObject.put("description", (Object) test.getImagesQuestions().get(i).getDescription());
                jsonObject.put("marks", (Object) test.getImagesQuestions().get(i).getMarks());
                jsonObject.put("subject", (Object) test.getImagesQuestions().get(i).getSubject());
                jsonObject.put("topic", (Object) test.getImagesQuestions().get(i).getTopic());
                jsonObject.put("subtopic", (Object) test.getImagesQuestions().get(i).getSubtopic());
                jsonObject.put("grade", (Object) test.getImagesQuestions().get(i).getGrade());
                jsonObject.put("inputmode", (Object) test.getImagesQuestions().get(i).getInputmode());
                jsonObject.put("presentationmode", (Object) test.getImagesQuestions().get(i).getPresentationmode());
                jsonObject.put("cognitivefaculty", (Object) test.getImagesQuestions().get(i).getCognitivefaculty());
                jsonObject.put("steps", (Object) test.getImagesQuestions().get(i).getSteps());
                jsonObject.put("teacherdifficulty", (Object) test.getImagesQuestions().get(i).getTeacherdifficulty());
                jsonObject.put("deviation", (Object) test.getImagesQuestions().get(i).getDeviation());
                jsonObject.put("ambiguity", (Object) test.getImagesQuestions().get(i).getAmbiguity());
                jsonObject.put("clarity", (Object) test.getImagesQuestions().get(i).getClarity());
                jsonObject.put("blooms", (Object) test.getImagesQuestions().get(i).getBlooms());
                jsonArray.put(jsonObject);
                complete_test.put("image_questions", jsonArray);
            }
            return complete_test;
        }catch (JSONException e){
            return  complete_test;
        }
    }


    public JSONObject convert_Student_Answers(Complete_Test_BO test){
        JSONObject complete_test = new JSONObject();
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 ;
        JSONObject jsonObject3 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        try{
            for(int i = 0; i < test.getText_questions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("id" , (Object) test.getText_questions().get(i).getId());
                jsonObject.put("answer", (Object) test.getText_questions().get(i).getStd_answer());
                jsonArray.put(jsonObject);
                complete_test.put("text_questions", jsonArray);
            }

            jsonArray = new JSONArray();
            //jsonObject2 = new JSONObject();

            for(int i = 0; i < test.getSingleChoiceQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonArray1 = new JSONArray();
                jsonObject.put("id", (Object) test.getSingleChoiceQuestions().get(i).getId());
                jsonObject.put("answer" , (Object) test.getSingleChoiceQuestions().get(i).getStd_answer());
                jsonArray.put(jsonObject);
                complete_test.put("Single_Choise_Questions", jsonArray);
            }

            jsonArray = new JSONArray();
            //jsonObject2 = new JSONObject();

            for(int i = 0; i < test.getMultipleChoiceQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonArray1 = new JSONArray();
                jsonObject.put("id", (Object) test.getMultipleChoiceQuestions().get(i).getId());
                for(int j = 0 ; j < test.getMultipleChoiceQuestions().get(i).getStd_answer().size() ; j++){
                    jsonObject2 = new JSONObject();
                    jsonObject2.put("option" , test.getMultipleChoiceQuestions().get(i).getStd_answer().get(j).toString());
                    jsonArray1.put(jsonObject2);
                    jsonObject.put("answer" , jsonArray1);
                }
                jsonArray.put(jsonObject);
                complete_test.put("Multi_Choise_Questions", jsonArray);
            }
            jsonArray = new JSONArray();

            for(int i = 0; i < test.getParagraphQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("answer", (Object) test.getParagraphQuestions().get(i).getStu_answer());
                jsonObject.put("id", (Object) test.getParagraphQuestions().get(i).getId());
                jsonArray.put(jsonObject);
                complete_test.put("paragraph_questions", jsonArray);
            }

            jsonArray = new JSONArray();

            for(int i = 0; i < test.getBooleanQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("answer" , (Object) test.getBooleanQuestions().get(i).isStd_answer());
                jsonObject.put("id" , (Object) test.getBooleanQuestions().get(i).getId());
                jsonArray.put(jsonObject);
                complete_test.put("boolean_questions", jsonArray);
            }
            jsonArray = new JSONArray();
            for(int i = 0; i < test.getImagesQuestions().size() ; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("answer" , (Object) test.getImagesQuestions().get(i).getImageKey());
                jsonObject.put("id", (Object) test.getImagesQuestions().get(i).getId());
                jsonArray.put(jsonObject);
                complete_test.put("image_questions", jsonArray);
            }
            return complete_test;
        }catch (JSONException e){
            return  complete_test;
        }
    }


    public JSONObject edit_test(Text_Questions text){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) text.getTitle());
            jsonObject.put("answer", (Object) text.getAnswer());
            jsonObject.put("marks", (Object) text.getMarks());
            jsonArray.put(jsonObject);
            jsonObject1.put("text_questions", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject edit_para(ParagraphQuestions para){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) para.getTitle());
            jsonObject.put("marks", (Object) para.getMarks());
            jsonArray.put(jsonObject);
            jsonObject1.put("paragraph_questions", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject edit_bool(BooleanQuestions bool){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) bool.getTitle());
            jsonObject.put("marks", (Object) bool.getMarks());
            jsonObject.put("correct" , (Object) bool.isCorrect());
            jsonArray.put(jsonObject);
            jsonObject1.put("boolean_questions", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject edit_single(SingleChoiceQuestions single){
        JSONObject jsonObject;
        JSONObject jsonObject2;
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) single.getTitle());
            jsonObject.put("marks", (Object) single.getMarks());

            for(int j = 0 ; j < single.getOptions().size() ; j++){
                jsonObject2 = new JSONObject();
                jsonObject2.put("option" , single.getOptions().get(j).getOption());
                jsonObject2.put("correct" , single.getOptions().get(j).getCorrect());
                jsonArray1.put(jsonObject2);
                jsonObject.put("Options" , jsonArray1);
            }
            jsonArray.put(jsonObject);
            jsonObject1.put("Single_Choise_Questions", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject edit_multi(MultipleChoiceQuestions multi){
        JSONObject jsonObject;
        JSONObject jsonObject2;
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) multi.getTitle());
            jsonObject.put("marks", (Object) multi.getMarks());

            for(int j = 0 ; j < multi.getOptions().size() ; j++){
                jsonObject2 = new JSONObject();
                jsonObject2.put("option" , multi.getOptions().get(j).getOption());
                jsonObject2.put("correct" , multi.getOptions().get(j).isCorrect());
                jsonArray1.put(jsonObject2);
                jsonObject.put("Options" , jsonArray1);
            }
            jsonArray.put(jsonObject);
            jsonObject1.put("Multi_Choise_Questions", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }

    public JSONObject edit_sco(Single_Choice_Options sco){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) sco.getOption());
            jsonObject.put("correct" , (Object) sco.getCorrect());
            jsonArray.put(jsonObject);
            jsonObject1.put("Options", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }
    public JSONObject edit_mco(Multi_Choice_Options mco){
        JSONObject jsonObject;
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            jsonObject = new JSONObject();
            jsonObject.put("title", (Object) mco.getOption());
            jsonObject.put("correct" , (Object) mco.isCorrect());
            jsonArray.put(jsonObject);
            jsonObject1.put("Options", jsonArray);
            return jsonObject1;

        }catch (JSONException e){
            return jsonObject1;
        }
    }
}
