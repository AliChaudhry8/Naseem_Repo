package Model;


import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BussinessObjects.Assign_Selected;
import BussinessObjects.Group_BO;
import BussinessObjects.Groups_Selected;
import BussinessObjects.Parent_Test_BO;
import BussinessObjects.Principal_Students_Test_Attempts_BO;
import BussinessObjects.Principal_Test_BO;
import BussinessObjects.Show_Test_BO.BooleanQuestions;
import BussinessObjects.Show_Test_BO.Complete_Test_BO;
import BussinessObjects.SchoolSection;
import BussinessObjects.School_BO;
import BussinessObjects.Section_BO;
import BussinessObjects.Show_Test_BO.Image_Questions;
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
import BussinessObjects.Video_BO;

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




    public Assign_Selected parse_Users(String response)
    {
        Assign_Selected as = new Assign_Selected();
        ArrayList<User_BO> users = new ArrayList<User_BO>();
        ArrayList<String> selected = new ArrayList<String>();
        try{
            JSONObject jsonObject1 = new JSONObject(response);
            JSONArray array = jsonObject1.getJSONArray("assign");
            JSONArray array1 = jsonObject1.getJSONArray("selected");

            for (int i = 0; i < array.length(); i++) {
                User_BO u = new User_BO();
                JSONObject jsonObject = array.getJSONObject(i);
                u.setId(Integer.parseInt(jsonObject.getString(Constants.User_Key_Id)));
                u.setUsername(jsonObject.getString(Constants.User_Key_Username));

                if (jsonObject.getString(Constants.User_Key_First_name).equals("") || jsonObject.getString(Constants.User_Key_First_name).equals(null) | jsonObject.getString(Constants.User_Key_First_name).equals("null"))
                    u.setFirst_name("");
                else
                    u.setFirst_name(jsonObject.getString(Constants.User_Key_First_name));
                if (jsonObject.getString(Constants.User_Key_Second_name).equals("") || jsonObject.getString(Constants.User_Key_Second_name).equals("null") || jsonObject.getString(Constants.User_Key_Second_name).equals(null))
                    u.setSecond_name("");
                else
                    u.setSecond_name(jsonObject.getString(Constants.User_Key_Second_name));
                users.add(u);
            }
            as.setUsers(users);
            if (array1.equals("") || array1.equals(null) || array1.equals("null") || array1.equals("[]") || array1.length() <= 0 ) {

            }
            else{
                for (int i = 0; i < array1.length(); i++) {
                    String select;
                    //JSONObject jsonObject = array1.getJSONObject(i);
                    select = array1.get(i).toString();
                    //if (jsonObject.getString("student_id").equals("") || jsonObject.getString("student_id").equals(null) || jsonObject.getString("student_id").equals("null")) {
                     //   select = "";
                    //} else {
                     //   select = String.valueOf(jsonObject.getInt("student_id"));
                  //  }
                    selected.add(select);
                }
            }



            as.setSelected(selected);

            return as;
        }catch (Exception e)
        {
            as.setUsers(users);
            as.setSelected(selected);
            return as;
        }
    }

    public Groups_Selected parse_Groups(String response)
    {
        Groups_Selected as = new Groups_Selected();
        ArrayList<Group_BO> users = new ArrayList<Group_BO>();
        ArrayList<String> selected = new ArrayList<String>();
        try{
            JSONObject jsonObject1 = new JSONObject(response);
            JSONArray array = jsonObject1.getJSONArray("assign");
            JSONArray array1 = jsonObject1.getJSONArray("selected");

            for (int i = 0; i < array.length(); i++) {
                Group_BO u = new Group_BO();
                JSONObject jsonObject = array.getJSONObject(i);
                u.setId(Integer.parseInt(jsonObject.getString("id")));
                u.setName(jsonObject.getString("name"));
                users.add(u);
            }
            as.setGroups(users);
            if (array1.equals("") || array1.equals(null) || array1.equals("null") || array1.equals("[]") || array1.length() <= 0 ) {

            }
            else{
                for (int i = 0; i < array1.length(); i++) {
                    String select;
                    //JSONObject jsonObject = array1.getJSONObject(i);
                    select = array1.get(i).toString();
                    //if (jsonObject.getString("student_id").equals("") || jsonObject.getString("student_id").equals(null) || jsonObject.getString("student_id").equals("null")) {
                    //   select = "";
                    //} else {
                    //   select = String.valueOf(jsonObject.getInt("student_id"));
                    //  }
                    selected.add(select);
                }
            }



            as.setSelected(selected);

            return as;
        }catch (Exception e)
        {
            as.setGroups(users);
            as.setSelected(selected);
            return as;
        }
    }

    public ArrayList<Video_BO> parse_Videos(String response)
    {
        ArrayList<Video_BO> video_bos = new ArrayList<Video_BO>();
        Video_BO video_bo;

        try{
            JSONObject jsonObject1 = new JSONObject(response);
            JSONArray array = jsonObject1.getJSONArray("videos");

            for (int i = 0; i < array.length(); i++) {
                video_bo = new Video_BO();
                JSONObject jsonObject = array.getJSONObject(i);
                video_bo.setLink(jsonObject.get("src").toString());
                String [] arr = video_bo.getLink().split("=");
                video_bo.setLink(arr[1]);
                video_bo.setDesc(jsonObject.getString("title"));
                video_bos.add(video_bo);
            }
            return video_bos;
        }catch (Exception e)
        {
            return video_bos;
        }
    }

    public ArrayList<User_BO> parse_attempt(String response)
    {
        ArrayList<User_BO> users = new ArrayList<User_BO>();
        try{
            JSONObject jsonObject1 = new JSONObject(response);
            JSONArray array = jsonObject1.getJSONArray("attempts");

            for (int i = 0; i < array.length(); i++) {
                User_BO u = new User_BO();
                JSONObject jsonObject = array.getJSONObject(i);
                u.setId(Integer.parseInt(jsonObject.getString(Constants.User_Key_Id)));
                u.setUsername(jsonObject.getString(Constants.User_Key_Username));

                if (jsonObject.getString(Constants.User_Key_First_name).equals("") || jsonObject.getString(Constants.User_Key_First_name).equals(null) | jsonObject.getString(Constants.User_Key_First_name).equals("null"))
                    u.setFirst_name("");
                else
                    u.setFirst_name(jsonObject.getString(Constants.User_Key_First_name));
                if (jsonObject.getString(Constants.User_Key_Second_name).equals("") || jsonObject.getString(Constants.User_Key_Second_name).equals("null") || jsonObject.getString(Constants.User_Key_Second_name).equals(null))
                    u.setSecond_name("");
                else
                    u.setSecond_name(jsonObject.getString(Constants.User_Key_Second_name));
                users.add(u);
            }

            return users;
        }catch (Exception e)
        {
            return users;
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

    public Test_BO parse_Test(String response) {
        Test_BO test_bo = new Test_BO();
        try {
            JSONObject js = new JSONObject(response);
            //JSONObject js = new JSONObject();
            //js = jsonObject.getJSONObject("test");

            test_bo.setId(Integer.parseInt(js.getString("id")));
            test_bo.setName(js.getString("name"));
            test_bo.setStart_time(js.getString("start_time"));
            test_bo.setAttempt_time(js.getString("attempt_time"));
            test_bo.setTeacher_id(Integer.parseInt(js.getString("teacher_id")));
        } catch (JSONException e) {
            Log.d("test", "exc:   "+e);
            e.printStackTrace();
            return  new Test_BO();
        }
        return  test_bo;
    }

    public ArrayList<Test_BO> parse_Teacher_Test(String response) {
        ArrayList<Test_BO> test = new ArrayList<Test_BO>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray tests = jsonObject.getJSONArray(Constants.Key_Test_Schedule);

            for(int i=0; i<tests.length(); i++)
            {
                Test_BO test_bo = new Test_BO();
                JSONObject js = tests.getJSONObject(i);
                test_bo.setId(Integer.parseInt(js.getString(Constants.Key_Test_Id)));
                test_bo.setName(js.getString(Constants.Key_Test_Name));
                test_bo.setStart_time(js.getString(Constants.Key_Test_Start_Time));
                test_bo.setAttempt_time(js.getString(Constants.Key_Test_Attempt_Time));
                test_bo.setTeacher_id(Integer.parseInt(js.getString(Constants.Key_Test_Teacher_Id)));
                test.add(test_bo);
            }
            return test;
        }catch (Exception e){
            return test;
        }
    }

    public ArrayList<Group_BO> parse_Group(String response) {
        ArrayList<Group_BO> test = new ArrayList<Group_BO>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray tests = jsonObject.getJSONArray(Constants.Key_Group);

            for(int i=0; i<tests.length(); i++)
            {
                Group_BO group_bo = new Group_BO();
                JSONObject js = tests.getJSONObject(i);
                group_bo.setId(Integer.parseInt(js.getString(Constants.Key_Group_Id)));
                group_bo.setName(js.getString(Constants.Key_Group_Name));
                group_bo.setTeacher_id(Integer.parseInt(js.getString(Constants.Key_Group_Teacher_Id)));
                test.add(group_bo);
            }
            return test;
        }catch (Exception e){
            return test;
        }
    }

    public Complete_Test_BO parseShowTest(String s) {
        try {
            ArrayList<Text_Questions> text_questions = new ArrayList<Text_Questions>();
            ArrayList<SingleChoiceQuestions> singleChoiceQuestions = new ArrayList<SingleChoiceQuestions>();
            ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestions>();
            ArrayList<ParagraphQuestions> paragraphQuestions = new ArrayList<ParagraphQuestions>();
            ArrayList<BooleanQuestions> booleanQuestions = new ArrayList<BooleanQuestions>();
            //ArrayList<Image_Questions> imageQuestions = new ArrayList<Image_Questions>();

            JSONObject jsonObject = new JSONObject(s);
            JSONArray json_text_questions = jsonObject.getJSONArray(Constants.Test_Key_Text_Questions);
            JSONArray json_single_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Single_Choice_Questions);
            JSONArray json_multi_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Multi_Choice_Questions);
            JSONArray json_paragraph_questions = jsonObject.getJSONArray(Constants.Test_Key_Paragraph_Questions);
            JSONArray json_boolean_questions = jsonObject.getJSONArray(Constants.Test_Key_Boolean_Questions);
           // JSONArray json_image_questions = jsonObject.getJSONArray("image_questions");

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
                        ans.add(str[j]);
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
                if(js.getString(Constants.Test_Key_Generic_Std_Answer).equals("True")) {
                    bq.setStd_answer(true);
                }
                else{
                    bq.setStd_answer(false);
                }
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

    public Complete_Test_BO parseShow_Test(String s) {
        try {
            ArrayList<Text_Questions> text_questions = new ArrayList<Text_Questions>();
            ArrayList<SingleChoiceQuestions> singleChoiceQuestions = new ArrayList<SingleChoiceQuestions>();
            ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestions>();
            ArrayList<ParagraphQuestions> paragraphQuestions = new ArrayList<ParagraphQuestions>();
            ArrayList<BooleanQuestions> booleanQuestions = new ArrayList<BooleanQuestions>();
            ArrayList<Image_Questions> imageQuestionses = new ArrayList<Image_Questions>();

            JSONObject jsonObject = new JSONObject(s);
            JSONArray json_text_questions = jsonObject.getJSONArray("text_Q");
            JSONArray json_single_choice_questions = jsonObject.getJSONArray("single_CQ");
            JSONArray json_multi_choice_questions = jsonObject.getJSONArray("multi_CQ");
            JSONArray json_paragraph_questions = jsonObject.getJSONArray("paragraph_Questions");
            JSONArray json_boolean_questions = jsonObject.getJSONArray("boolean_Questions");
            JSONArray json_image_questions = jsonObject.getJSONArray("image_Questions");

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
                js = json_text_questions.getJSONObject(i).getJSONObject("question_metadata");
                tq.setSubject(js.getString("subject"));
                tq.setGrade(js.getString("grade"));
                tq.setTopic(js.getString("topic"));
                tq.setSubtopic(js.getString("sub_topic"));
                tq.setInputmode(js.getString("input_mode"));
                tq.setPresentationmode(js.getString("presentation_mode"));
                tq.setCognitivefaculty(js.getString("cognitive_faculty"));
                tq.setSteps(js.getString("steps"));
                tq.setTeacherdifficulty(js.getString("teacher_difficulty"));
                tq.setDeviation(js.getString("deviation"));
                tq.setAmbiguity(js.getString("ambiguity"));
                tq.setClarity(js.getString("clarity"));
                tq.setBlooms(js.getString("blooms"));


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
                js = json_single_choice_questions.getJSONObject(i).getJSONObject("question_metadata");
                scq.setSubject(js.getString("subject"));
                scq.setGrade(js.getString("grade"));
                scq.setTopic(js.getString("topic"));
                scq.setSubtopic(js.getString("sub_topic"));
                scq.setInputmode(js.getString("input_mode"));
                scq.setPresentationmode(js.getString("presentation_mode"));
                scq.setCognitivefaculty(js.getString("cognitive_faculty"));
                scq.setSteps(js.getString("steps"));
                scq.setTeacherdifficulty(js.getString("teacher_difficulty"));
                scq.setDeviation(js.getString("deviation"));
                scq.setAmbiguity(js.getString("ambiguity"));
                scq.setClarity(js.getString("clarity"));
                scq.setBlooms(js.getString("blooms"));

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
                scq.setAlready(true);
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
                js = json_multi_choice_questions.getJSONObject(i).getJSONObject("question_metadata");
                mcq.setSubject(js.getString("subject"));
                mcq.setGrade(js.getString("grade"));
                mcq.setTopic(js.getString("topic"));
                mcq.setSubtopic(js.getString("sub_topic"));
                mcq.setInputmode(js.getString("input_mode"));
                mcq.setPresentationmode(js.getString("presentation_mode"));
                mcq.setCognitivefaculty(js.getString("cognitive_faculty"));
                mcq.setSteps(js.getString("steps"));
                mcq.setTeacherdifficulty(js.getString("teacher_difficulty"));
                mcq.setDeviation(js.getString("deviation"));
                mcq.setAmbiguity(js.getString("ambiguity"));
                mcq.setClarity(js.getString("clarity"));
                mcq.setBlooms(js.getString("blooms"));

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
                mcq.setAlready(true);
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
                js = json_paragraph_questions.getJSONObject(i).getJSONObject("question_metadata");
                pq.setSubject(js.getString("subject"));
                pq.setGrade(js.getString("grade"));
                pq.setTopic(js.getString("topic"));
                pq.setSubtopic(js.getString("sub_topic"));
                pq.setInputmode(js.getString("input_mode"));
                pq.setPresentationmode(js.getString("presentation_mode"));
                pq.setCognitivefaculty(js.getString("cognitive_faculty"));
                pq.setSteps(js.getString("steps"));
                pq.setTeacherdifficulty(js.getString("teacher_difficulty"));
                pq.setDeviation(js.getString("deviation"));
                pq.setAmbiguity(js.getString("ambiguity"));
                pq.setClarity(js.getString("clarity"));
                pq.setBlooms(js.getString("blooms"));

                Paragraph_Answer pa = new Paragraph_Answer();
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
                js = json_boolean_questions.getJSONObject(i).getJSONObject("question_metadata");
                bq.setSubject(js.getString("subject"));
                bq.setGrade(js.getString("grade"));
                bq.setTopic(js.getString("topic"));
                bq.setSubtopic(js.getString("sub_topic"));
                bq.setInputmode(js.getString("input_mode"));
                bq.setPresentationmode(js.getString("presentation_mode"));
                bq.setCognitivefaculty(js.getString("cognitive_faculty"));
                bq.setSteps(js.getString("steps"));
                bq.setTeacherdifficulty(js.getString("teacher_difficulty"));
                bq.setDeviation(js.getString("deviation"));
                bq.setAmbiguity(js.getString("ambiguity"));
                bq.setClarity(js.getString("clarity"));
                bq.setBlooms(js.getString("blooms"));

                booleanQuestions.add(bq);
            }

            // Parsing Image Questions
            for(int i=0; i<json_image_questions.length(); i++){
                Image_Questions bq = new Image_Questions();
                JSONObject js = json_image_questions.getJSONObject(i);
                bq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                bq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                bq.setDescription(js.getString("description"));
                bq.setImageKey(js.getString("name"));
                bq.setMarks(Integer.valueOf(js.getString("marks")));

                imageQuestionses.add(bq);
            }
            Complete_Test_BO test = new Complete_Test_BO();
            test.setText_questions(text_questions);
            test.setSingleChoiceQuestions(singleChoiceQuestions);
            test.setMultipleChoiceQuestions(multipleChoiceQuestions);
            test.setParagraphQuestions(paragraphQuestions);
            test.setBooleanQuestions(booleanQuestions);
            test.setImagesQuestions(imageQuestionses);
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
            ArrayList<Image_Questions> imageQuestionses = new ArrayList<Image_Questions>();

            JSONObject jsonObject = new JSONObject(s);
            JSONArray json_text_questions = jsonObject.getJSONArray(Constants.Test_Key_Text_Questions);
            JSONArray json_single_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Single_Choice_Questions);
            JSONArray json_multi_choice_questions = jsonObject.getJSONArray(Constants.Test_Key_Multi_Choice_Questions);
            JSONArray json_paragraph_questions = jsonObject.getJSONArray(Constants.Test_Key_Paragraph_Questions);
            JSONArray json_boolean_questions = jsonObject.getJSONArray(Constants.Test_Key_Boolean_Questions);
            JSONArray json_image_questions = jsonObject.getJSONArray("image");

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
                    sco.setId(O.getInt("id"));
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
                    option.setId(jss.getInt("id"));
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
            // Parsing Image Questions
            for(int i=0; i<json_image_questions.length(); i++){
                Image_Questions bq = new Image_Questions();
                JSONObject js = json_image_questions.getJSONObject(i);
                bq.setTest_id(js.getInt(Constants.Test_Key_Generic_Test_Id));
                bq.setId(js.getInt(Constants.Test_Key_Generic_Id));
                bq.setTitle(js.getString(Constants.Test_Key_Generic_Title));
                bq.setDescription(js.getString("description"));
                bq.setImageKey(js.getString("name"));
                bq.setMarks(Integer.valueOf(js.getString("marks")));

                imageQuestionses.add(bq);
            }

            Complete_Test_BO test = new Complete_Test_BO();
            test.setText_questions(text_questions);
            test.setSingleChoiceQuestions(singleChoiceQuestions);
            test.setMultipleChoiceQuestions(multipleChoiceQuestions);
            test.setParagraphQuestions(paragraphQuestions);
            test.setBooleanQuestions(booleanQuestions);
            test.setImagesQuestions(imageQuestionses);
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
                u.setRole(js.getString(Constants.User_Key_role));
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
                p.setTeacher_name(jsonObject.getString(Constants.Key_Test_Teacher_Name));
                test.add(p);
            }
            return test;
        }
        catch (Exception e){
            test = null;
            return test;
        }
    }

    public ArrayList<Principal_Students_Test_Attempts_BO> parse_students_attempts(String s){
        ArrayList<Principal_Students_Test_Attempts_BO> attempts = new ArrayList<Principal_Students_Test_Attempts_BO>();
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.Principal_Key_Students_List);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject js = jsonArray.getJSONObject(i);
                Principal_Students_Test_Attempts_BO bo = new Principal_Students_Test_Attempts_BO();
                bo.setTaken(js.getInt(Constants.Principal_Key_Students_List_Taken));
                JSONObject js1 = js.getJSONObject(Constants.Principal_Key_Students_List_User);
                bo.setUser_id(js1.getInt(Constants.Principal_Key_Students_List_Id));
                bo.setFirst_name(js1.getString(Constants.Principal_Key_Students_List_First_Name));
                bo.setSecond_name(js1.getString(Constants.Principal_Key_Students_List_Second_Name));
                attempts.add(bo);
            }
            return attempts;
        }catch (Exception e){
            attempts = null;
            return attempts;
        }
    }


    public ArrayList<Principal_Test_BO> parse_Principal_Student_Test_List(String s){
        ArrayList<Principal_Test_BO> test = new ArrayList<Principal_Test_BO>();
        try{
            JSONObject object = new JSONObject(s);
            JSONArray js = object.getJSONArray(Constants.Key_Test_Schedule);
            for(int i=0; i<js.length(); i++){
                JSONObject jsonObject = js.getJSONObject(i);
                Principal_Test_BO p = new Principal_Test_BO();
                p.setId(jsonObject.getInt(Constants.Key_Test_Id));
                p.setName(jsonObject.getString(Constants.Key_Test_Name));
                p.setTeacher_name(jsonObject.getString(Constants.Key_Test_Teacher_Name));
                p.setStatus(jsonObject.getInt(Constants.Principal_Key_Student_Teacher_Test_List_Attempted));
                test.add(p);
            }
            return test;
        }
        catch (Exception e){
            test = null;
            return test;
        }
    }


    public ArrayList<Principal_Test_BO> parse_Principal_Teacher_Test_List(String s){
        ArrayList<Principal_Test_BO> test = new ArrayList<Principal_Test_BO>();
        try{
            JSONObject object = new JSONObject(s);
            JSONArray js = object.getJSONArray(Constants.Key_Test_Schedule);
            for(int i=0; i<js.length(); i++){
                JSONObject jsonObject = js.getJSONObject(i);
                Principal_Test_BO p = new Principal_Test_BO();
                p.setId(jsonObject.getInt(Constants.Key_Test_Id));
                p.setName(jsonObject.getString(Constants.Key_Test_Name));
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
