package Model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import BussinessObjects.Parent_Test_BO;
import BussinessObjects.Principal_Test_BO;
import BussinessObjects.Show_Test_BO.BooleanQuestions;
import BussinessObjects.Show_Test_BO.Complete_Test_BO;
import BussinessObjects.SchoolSection;
import BussinessObjects.School_BO;
import BussinessObjects.Section_BO;
import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import BussinessObjects.Show_Test_BO.ParagraphQuestions;
import BussinessObjects.Show_Test_BO.Paragraph_Answer;
import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import BussinessObjects.Show_Test_BO.Text_Questions;
import BussinessObjects.Test_BO;
import BussinessObjects.Test_Taken_Schedule;
import BussinessObjects.User_BO;

/**
 * Created by Muhammad Taimoor on 5/22/2017.
 */

public class JsonParsor {

    public JsonParsor(){}

    public User_BO parseUser(String response)
    {
        User_BO u = new User_BO();
        try{
            JSONObject jsonObject = new JSONObject(response);
            u.setId(Integer.parseInt(jsonObject.getString(Constants.User_Key_Id)));
            u.setUsername(jsonObject.getString(Constants.User_Key_Username));
            if(jsonObject.getString(Constants.User_Key_Email).equals("") || jsonObject.getString(Constants.User_Key_Email).equals(null) || jsonObject.getString(Constants.User_Key_Email).equals("null"))
                u.setEmail("");
            else
                u.setEmail(jsonObject.getString(Constants.User_Key_Email));
            if(jsonObject.getString(Constants.User_Key_First_name).equals("") || jsonObject.getString(Constants.User_Key_First_name).equals(null) | jsonObject.getString(Constants.User_Key_First_name).equals("null"))
                u.setFirst_name("");
            else
                u.setFirst_name(jsonObject.getString(Constants.User_Key_First_name));
            if(jsonObject.getString(Constants.User_Key_Second_name).equals("") || jsonObject.getString(Constants.User_Key_Second_name).equals("null") || jsonObject.getString(Constants.User_Key_Second_name).equals(null))
                u.setSecond_name("");
            else
                u.setSecond_name(jsonObject.getString(Constants.User_Key_Second_name));
            u.setSchool_name(jsonObject.getString(Constants.User_Key_School_name));
            u.setSection_name(jsonObject.getString(Constants.User_Key_Section_name));
            if(jsonObject.getString(Constants.User_Key_Gender).equals("") || jsonObject.getString(Constants.User_Key_Gender).equals("null") || jsonObject.getString(Constants.User_Key_Gender).equals(null))
                u.setGender("Male");
            else
                u.setGender(jsonObject.getString(Constants.User_Key_Gender));
            if(jsonObject.getString(Constants.User_Key_Age).toString().equals(null) || jsonObject.getString(Constants.User_Key_Age).toString().equals("null"))
                u.setAge(0);
            else
                u.setAge(Integer.parseInt(jsonObject.getString(Constants.User_Key_Age)));
            u.setRole(jsonObject.getString(Constants.User_Key_role));
            if(jsonObject.getString(Constants.User_Key_Telephone).equals("") || jsonObject.getString(Constants.User_Key_Telephone).equals("null") || jsonObject.getString(Constants.User_Key_Telephone).equals(null))
                u.setTelephone("");
            else
                u.setTelephone(jsonObject.getString(Constants.User_Key_Telephone));

            if (jsonObject.getString(Constants.User_Key_School_id).equals("") || jsonObject.getString(Constants.User_Key_School_id).equals("null") || jsonObject.getString(Constants.User_Key_School_id).equals(null))
                u.setSchool_id(0);
            else
                u.setSchool_id(Integer.parseInt(jsonObject.getString(Constants.User_Key_School_id)));
            if(jsonObject.getString(Constants.User_Key_Section_id).equals("") || jsonObject.getString(Constants.User_Key_Section_id).equals("null") || jsonObject.getString(Constants.User_Key_Section_id).equals(null))
                u.setSection_id(0);
            else
                u.setSection_id(Integer.parseInt(jsonObject.getString(Constants.User_Key_Section_id)));

            JSONObject avatar = new JSONObject(jsonObject.getString(Constants.User_Key_Avatar));
            u.setAvatar(avatar.getString(Constants.User_Key_URL));
            JSONObject thumb = new JSONObject(avatar.getString(Constants.User_Key_Thumb));
            u.setThumb(thumb.getString(Constants.User_Key_URL));
            u.setAuthentication_token(jsonObject.getString(Constants.User_Key_Authentication_token));
            return u;
        }catch (Exception e)
        {
            u.setAuthentication_token(e.getMessage());
            return u;
        }
    }

    public SchoolSection parseSchoolSection(String response)
    {
        ArrayList<School_BO> schools = new ArrayList<School_BO>();
        ArrayList<Section_BO> sections = new ArrayList<Section_BO>();
        try {
            JSONObject schoolSection = new JSONObject(response);
            JSONArray schoolArray = schoolSection.getJSONArray(Constants.School);
            JSONArray sectionArray = schoolSection.getJSONArray(Constants.Section);

            for(int i=0; i<schoolArray.length(); i++)
            {
                School_BO s = new School_BO();
                JSONObject js = schoolArray.getJSONObject(i);
                s.setId(Integer.parseInt(js.getString(Constants.School_Key_Id)));
                s.setName(js.getString(Constants.School_Key_Name));
                schools.add(s);
            }

            for(int i=0; i<sectionArray.length(); i++)
            {
                Section_BO s = new Section_BO();
                JSONObject js = sectionArray.getJSONObject(i);
                s.setId(Integer.parseInt(js.getString(Constants.Section_Key_Id)));
                s.setName(js.getString(Constants.Section_Key_Name));
                s.setSchool_id(Integer.parseInt(js.getString(Constants.Section_Key_School_Id)));
                sections.add(s);
            }
            SchoolSection ss = new SchoolSection(schools, sections);
            return ss;
        }
        catch (Exception e)
        {
            SchoolSection ss = new SchoolSection(schools, sections);
            return ss;
        }
    }

    public Test_Taken_Schedule parseTest(String response) {
        ArrayList<Test_BO> test_taken = new ArrayList<Test_BO>();
        ArrayList<Test_BO> test_schedule = new ArrayList<Test_BO>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray schedule = jsonObject.getJSONArray(Constants.Key_Test_Schedule);
            JSONArray taken = jsonObject.getJSONArray(Constants.Key_Test_Taken);

            for(int i=0; i<schedule.length(); i++) {
                Test_BO test_bo = new Test_BO();
                JSONObject js = schedule.getJSONObject(i);
                test_bo.setId(Integer.parseInt(js.getString(Constants.Key_Test_Id)));
                test_bo.setName(js.getString(Constants.Key_Test_Name));
                test_bo.setStart_time(js.getString(Constants.Key_Test_Start_Time));
                test_bo.setAttempt_time(js.getString(Constants.Key_Test_Attempt_Time));
                test_bo.setTeacher_id(Integer.parseInt(js.getString(Constants.Key_Test_Teacher_Id)));
                test_schedule.add(test_bo);
            }

            for(int i=0; i<taken.length(); i++)
            {
                Test_BO test_bo = new Test_BO();
                JSONObject js = taken.getJSONObject(i);
                test_bo.setId(Integer.parseInt(js.getString(Constants.Key_Test_Id)));
                test_bo.setName(js.getString(Constants.Key_Test_Name));
                test_bo.setStart_time(js.getString(Constants.Key_Test_Start_Time));
                test_bo.setAttempt_time(js.getString(Constants.Key_Test_Attempt_Time));
                test_bo.setTeacher_id(Integer.parseInt(js.getString(Constants.Key_Test_Teacher_Id)));
                test_taken.add(test_bo);
            }
            Test_Taken_Schedule test_taken_schedule = new Test_Taken_Schedule(test_taken, test_schedule);
            return test_taken_schedule;
        }catch (Exception e){
            Test_Taken_Schedule test_taken_schedule = new Test_Taken_Schedule(test_taken, test_schedule);
            return test_taken_schedule;
        }
    }

    public Complete_Test_BO parseShowTest(String s) {
        try {
            ArrayList<Text_Questions> text_questions = new ArrayList<Text_Questions>();
            ArrayList<SingleChoiceQuestions> singleChoiceQuestions = new ArrayList<SingleChoiceQuestions>();
            ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestions>();
            ArrayList<ParagraphQuestions> paragraphQuestions = new ArrayList<ParagraphQuestions>();
            ArrayList<BooleanQuestions> booleanQuestions = new ArrayList<BooleanQuestions>();

            JSONObject jsonObject = new JSONObject(s);
            JSONArray json_text_questions = jsonObject.getJSONArray(Constants.Test_Key_Text_Questions);
            JSONArray json_single_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Single_Choice_Questions);
            JSONArray json_multi_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Multi_Choice_Questions);
            JSONArray json_paragraph_questions = jsonObject.getJSONArray(Constants.Test_Key_Paragraph_Questions);
            JSONArray json_boolean_questions = jsonObject.getJSONArray(Constants.Test_Key_Boolean_Questions);

            // Parsing Text Questions
            for(int i=0; i<json_text_questions.length(); i++){
                JSONObject js = json_text_questions.getJSONObject(i);
                Text_Questions tq = new Text_Questions();
                tq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                tq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                tq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                tq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                tq.setAnswer(js.getString(Constants.Test_Key_Generic_Answer));
                tq.setStd_answer(js.getString(Constants.Test_Key_Generic_Std_Answer));
                text_questions.add(tq);
            }

            // Parsing Single Choice Questions
            for (int i=0; i<json_single_choice_questions.length(); i++){
                JSONObject js = json_single_choice_questions.getJSONObject(i);
                SingleChoiceQuestions scq = new SingleChoiceQuestions();
                scq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                scq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                scq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                scq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                scq.setStd_answer(js.getInt(Constants.Test_Key_Generic_Std_Answer));
                JSONArray options = js.getJSONArray(Constants.Test_Key_Single_Choice_Options);
                ArrayList<Single_Choice_Options> single_choice_options = new ArrayList<Single_Choice_Options>();
                for (int j=0; j<options.length(); j++){
                    Single_Choice_Options sco = new Single_Choice_Options();
                    JSONObject O = options.getJSONObject(j);
                    sco.setId(O.getInt(Constants.Test_Key_Single_Choice_Options_Id));
                    sco.setOption(O.getString(Constants.Test_Key_Single_Choice_Options_Optioon));
                    sco.setCorrect(O.getBoolean(Constants.Test_Key_Single_Choice_Options_Correct));
                    sco.setQuestion_id(O.getInt(Constants.Test_Key_Single_Choice_Options_Question_Id));
                    single_choice_options.add(sco);
                }
                scq.setOptions(single_choice_options);
                singleChoiceQuestions.add(scq);
            }

            // Parsing Multi Choice Questions
            for(int i=0; i<json_multi_choice_questions.length(); i++){
                MultipleChoiceQuestions mcq = new MultipleChoiceQuestions();
                JSONObject js = json_multi_choice_questions.getJSONObject(i);
                mcq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                mcq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                mcq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                mcq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                String std = js.getString(Constants.Test_Key_Generic_Std_Answer);
                ArrayList<String> ans = new ArrayList<String>();
                if(std.contains(",")){
                    String[] str = std.split(",");
                    for(int j=0; j<str.length; j++) {
                        String s1 = str[j];
                        ans.add(s1);
                    }
                }
                else{
                    ans.add(std);
                }
                mcq.setStd_answer(ans);
                JSONArray json_options = js.getJSONArray(Constants.Test_Key_Multi_Choice_Options);
                ArrayList<Multi_Choice_Options> multi_choice_options = new ArrayList<Multi_Choice_Options>();
                for(int j=0; j<json_options.length(); j++){
                    JSONObject jss = json_options.getJSONObject(j);
                    Multi_Choice_Options option = new Multi_Choice_Options();
                    option.setId(jss.getInt(Constants.Test_Key_Multi_Choice_Options_Id));
                    option.setOption(jss.getString(Constants.Test_Key_Multi_Choice_Options_Option));
                    option.setCorrect(jss.getBoolean(Constants.Test_Key_Multi_Choice_Options_Correct));
                    option.setQuestion_id(jss.getInt(Constants.Test_Key_Multi_Choice_Options_Question_Id));
                    multi_choice_options.add(option);
                }
                mcq.setOptions(multi_choice_options);
                multipleChoiceQuestions.add(mcq);
            }

            // Parsing Paragraph Questions
            for(int i=0; i<json_paragraph_questions.length(); i++){
                ParagraphQuestions pq = new ParagraphQuestions();
                JSONObject js = json_paragraph_questions.getJSONObject(i);
                pq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                pq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                pq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                pq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                JSONObject js1 = js.getJSONObject(Constants.Test_Key_Generic_Std_Answer);
                Paragraph_Answer pa = new Paragraph_Answer();
                pa.setId(js1.getInt(Constants.Test_Key_Paragraph_Id));
                pa.setAnswer(js1.getString(Constants.Test_Key_Paragraph_Answer));
                String str = js1.getString(Constants.Test_Key_Paragraph_Marks);
                if(str.equals("") || str.equals(null) || str.equals("null"))
                    pa.setMarks(-1);
                else
                    pa.setMarks(Integer.parseInt(str));
                pq.setAnswer(pa);
                paragraphQuestions.add(pq);
            }

            // Parsing Boolean Questions
            for(int i=0; i<json_boolean_questions.length(); i++){
                BooleanQuestions bq = new BooleanQuestions();
                JSONObject js = json_boolean_questions.getJSONObject(i);
                bq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                bq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                bq.setCorrect(js.getBoolean(Constants.Test_Key_Boolean_Correct));
                bq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                bq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                bq.setStd_answer(js.getBoolean(Constants.Test_Key_Generic_Std_Answer));
                booleanQuestions.add(bq);
            }
            Complete_Test_BO test = new Complete_Test_BO();
            test.setText_questions(text_questions);
            test.setSingleChoiceQuestions(singleChoiceQuestions);
            test.setMultipleChoiceQuestions(multipleChoiceQuestions);
            test.setParagraphQuestions(paragraphQuestions);
            test.setBooleanQuestions(booleanQuestions);
            return test;
        }catch (Exception e) {
            return null;
        }
    }


    public Complete_Test_BO parseStartTest(String s) {
        try {
            ArrayList<Text_Questions> text_questions = new ArrayList<Text_Questions>();
            ArrayList<SingleChoiceQuestions> singleChoiceQuestions = new ArrayList<SingleChoiceQuestions>();
            ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestions>();
            ArrayList<ParagraphQuestions> paragraphQuestions = new ArrayList<ParagraphQuestions>();
            ArrayList<BooleanQuestions> booleanQuestions = new ArrayList<BooleanQuestions>();

            JSONObject jsonObject = new JSONObject(s);
            JSONArray json_text_questions = jsonObject.getJSONArray(Constants.Test_Key_Text_Questions);
            JSONArray json_single_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Single_Choice_Questions);
            JSONArray json_multi_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Multi_Choice_Questions);
            JSONArray json_paragraph_questions = jsonObject.getJSONArray(Constants.Test_Key_Paragraph_Questions);
            JSONArray json_boolean_questions = jsonObject.getJSONArray(Constants.Test_Key_Boolean_Questions);

            // Parsing Text Questions
            for(int i=0; i<json_text_questions.length(); i++){
                JSONObject js = json_text_questions.getJSONObject(i);
                Text_Questions tq = new Text_Questions();
                tq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                tq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                tq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                tq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                text_questions.add(tq);
            }

            // Parsing Single Choice Questions
            for (int i=0; i<json_single_choice_questions.length(); i++){
                JSONObject js = json_single_choice_questions.getJSONObject(i);
                SingleChoiceQuestions scq = new SingleChoiceQuestions();
                scq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                scq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                scq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                scq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                JSONArray options = js.getJSONArray(Constants.Test_Key_Single_Choice_Options);
                ArrayList<Single_Choice_Options> single_choice_options = new ArrayList<Single_Choice_Options>();
                for (int j=0; j<options.length(); j++){
                    Single_Choice_Options sco = new Single_Choice_Options();
                    JSONObject O = options.getJSONObject(j);
                    sco.setOption(O.getString(Constants.Test_Key_Single_Choice_Options_Optioon));
                    single_choice_options.add(sco);
                }
                scq.setOptions(single_choice_options);
                singleChoiceQuestions.add(scq);
            }

            // Parsing Multi Choice Questions
            for(int i=0; i<json_multi_choice_questions.length(); i++){
                MultipleChoiceQuestions mcq = new MultipleChoiceQuestions();
                JSONObject js = json_multi_choice_questions.getJSONObject(i);
                mcq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                mcq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                mcq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                mcq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                JSONArray json_options = js.getJSONArray(Constants.Test_Key_Multi_Choice_Options);
                ArrayList<Multi_Choice_Options> multi_choice_options = new ArrayList<Multi_Choice_Options>();
                for(int j=0; j<json_options.length(); j++){
                    JSONObject jss = json_options.getJSONObject(j);
                    Multi_Choice_Options option = new Multi_Choice_Options();
                    option.setOption(jss.getString(Constants.Test_Key_Multi_Choice_Options_Option));
                    multi_choice_options.add(option);
                }
                mcq.setOptions(multi_choice_options);
                multipleChoiceQuestions.add(mcq);
            }

            // Parsing Paragraph Questions
            for(int i=0; i<json_paragraph_questions.length(); i++){
                ParagraphQuestions pq = new ParagraphQuestions();
                JSONObject js = json_paragraph_questions.getJSONObject(i);
                pq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                pq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                pq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                pq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                paragraphQuestions.add(pq);
            }

            // Parsing Boolean Questions
            for(int i=0; i<json_boolean_questions.length(); i++){
                BooleanQuestions bq = new BooleanQuestions();
                JSONObject js = json_boolean_questions.getJSONObject(i);
                bq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                bq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                bq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                bq.setMarks(js.getInt(Constants.Test_Key_Generic_Marks));
                booleanQuestions.add(bq);
            }
            Complete_Test_BO test = new Complete_Test_BO();
            test.setText_questions(text_questions);
            test.setSingleChoiceQuestions(singleChoiceQuestions);
            test.setMultipleChoiceQuestions(multipleChoiceQuestions);
            test.setParagraphQuestions(paragraphQuestions);
            test.setBooleanQuestions(booleanQuestions);
            return test;
        }catch (Exception e) {
            return null;
        }
    }

    public ArrayList<User_BO> parseStudentsTeachers(String s){
        ArrayList<User_BO> users = new ArrayList<User_BO>();
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.Principal_Key_Students_Teachers);
            for(int i=0; i<jsonArray.length(); i++){
                User_BO u = new User_BO();
                JSONObject js = jsonArray.getJSONObject(i);
                u.setId(Integer.parseInt(js.getString(Constants.User_Key_Id)));
                u.setUsername(js.getString(Constants.User_Key_Username));
                u.setSchool_name(js.getString(Constants.User_Key_School_name));
                u.setSection_name(js.getString(Constants.User_Key_Section_name));
                JSONObject avatar = new JSONObject(js.getString(Constants.User_Key_Avatar));
                u.setAvatar(avatar.getString(Constants.User_Key_URL));
                JSONObject thumb = new JSONObject(avatar.getString(Constants.User_Key_Thumb));
                u.setThumb(thumb.getString(Constants.User_Key_URL));
                users.add(u);
            }
            return users;
        }catch (Exception e){
            return users;
        }
    }




    public User_BO parseStudentTeacher(String response)
    {
        User_BO u = new User_BO();
        try{
            JSONObject js = new JSONObject(response);
            JSONObject jsonObject = js.getJSONObject(Constants.User_Key_User);
            u.setUsername(jsonObject.getString(Constants.User_Key_Username));
            if(jsonObject.getString(Constants.User_Key_Email).equals("") || jsonObject.getString(Constants.User_Key_Email).equals(null) || jsonObject.getString(Constants.User_Key_Email).equals("null"))
                u.setEmail("");
            else
                u.setEmail(jsonObject.getString(Constants.User_Key_Email));
            if(jsonObject.getString(Constants.User_Key_First_name).equals("") || jsonObject.getString(Constants.User_Key_First_name).equals(null) | jsonObject.getString(Constants.User_Key_First_name).equals("null"))
                u.setFirst_name("");
            else
                u.setFirst_name(jsonObject.getString(Constants.User_Key_First_name));
            if(jsonObject.getString(Constants.User_Key_Second_name).equals("") || jsonObject.getString(Constants.User_Key_Second_name).equals("null") || jsonObject.getString(Constants.User_Key_Second_name).equals(null))
                u.setSecond_name("");
            else
                u.setSecond_name(jsonObject.getString(Constants.User_Key_Second_name));
            u.setSchool_name(jsonObject.getString(Constants.User_Key_School_name));
            u.setSection_name(jsonObject.getString(Constants.User_Key_Section_name));
            if(jsonObject.getString(Constants.User_Key_Gender).equals("") || jsonObject.getString(Constants.User_Key_Gender).equals("null") || jsonObject.getString(Constants.User_Key_Gender).equals(null))
                u.setGender("Male");
            else
                u.setGender(jsonObject.getString(Constants.User_Key_Gender));
            if(jsonObject.getString(Constants.User_Key_Age).toString().equals(null) || jsonObject.getString(Constants.User_Key_Age).toString().equals("null"))
                u.setAge(0);
            else
                u.setAge(Integer.parseInt(jsonObject.getString(Constants.User_Key_Age)));
            u.setRole(jsonObject.getString(Constants.User_Key_role));
            if(jsonObject.getString(Constants.User_Key_Telephone).equals("") || jsonObject.getString(Constants.User_Key_Telephone).equals("null") || jsonObject.getString(Constants.User_Key_Telephone).equals(null))
                u.setTelephone("");
            else
                u.setTelephone(jsonObject.getString(Constants.User_Key_Telephone));
            return u;
        }catch (Exception e)
        {
            u.setAuthentication_token(e.getMessage());
            return u;
        }
    }


    public String parseErrors(String s){
        String errors = "";
        try{
            JSONObject js = new JSONObject(s);
            JSONArray array = js.getJSONArray("errors");
            for(int i=0; i<array.length(); i++){
                String str = array.getString(i);
                errors = errors + str + "\n";
            }
            return errors;
        }catch (Exception e){
            return errors;
        }
    }


    public ArrayList<Parent_Test_BO> parse_Parent_Test(String s){
        ArrayList<Parent_Test_BO> test = new ArrayList<Parent_Test_BO>();
        try{
            JSONObject object = new JSONObject(s);
            JSONArray js = object.getJSONArray(Constants.Key_Test_Schedule);
            for(int i=0; i<js.length(); i++){
                JSONObject jsonObject = js.getJSONObject(i);
                Parent_Test_BO p = new Parent_Test_BO();
                p.setId(jsonObject.getInt(Constants.Key_Test_Id));
                String str = jsonObject.getString(Constants.Key_Test_Name);
                String[] str_array = str.split(":");
                p.setName(str_array[0]);
                p.setTaken(Integer.parseInt(str_array[1]));
                p.setStart_time(jsonObject.getString(Constants.Key_Test_Start_Time));
                test.add(p);
            }
            return test;
        }
        catch (Exception e){
            test = null;
            return test;
        }
    }


    public ArrayList<Principal_Test_BO> parse_Principal_Test(String s){
        ArrayList<Principal_Test_BO> test = new ArrayList<Principal_Test_BO>();
        try{
            JSONObject object = new JSONObject(s);
            JSONArray js = object.getJSONArray(Constants.Key_Test_Schedule);
            for(int i=0; i<js.length(); i++){
                JSONObject jsonObject = js.getJSONObject(i);
                Principal_Test_BO p = new Principal_Test_BO();
                p.setId(jsonObject.getInt(Constants.Key_Test_Id));
                p.setName(jsonObject.getString(Constants.Key_Test_Name));
                p.setStart_time(jsonObject.getString(Constants.Key_Test_Start_Time));
                test.add(p);
            }
            return test;
        }
        catch (Exception e){
            test = null;
            return test;
        }
    }
}
