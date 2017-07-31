package Model;

/**
 * Created by Muhammad Taimoor on 5/22/2017.
 */

public class Constants {
    // Errors Strings
    public static final String Error_Internet = "No Internet Connection Available\nTry Again Later";
    public static final String Error_Unrecognized_Error = "Unrecognized Error Occur.\nPlease Try Again Later";
    public static final String Error_Unauthorized_Access = "Unauthorized Access.\nPlease Login Again to Verify Your Authorization";
    public static final String Error_USER_Login_Failed = "Login Failed !!!\nIncorrect Username or Password";
    public static final String Error_USER_Sign_Up_Failed = "Registration Failed !!!\nSomething Went Wrong";
    public static final String Error_User_Required = "Enter a Valid Username. At least 4 characters";
    public static final String Error_Password_Required = "Enter Valid Password Between 6 and 20 Alphanumeric Characters";
    public static final String Error_Password_Match = "Password Do not match";
    public static final String Error_School_Required = "Select a School First";
    public static final String Error_Section_Required = "Select a Section First";
    public static final String Error_Username_Not_available = "Username has already been taken";
    public static final String Error_Cannot_Load_Test = "Cannot Load Your Test";
    public static final String Error_Cannot_Start_Test = "Cannot Start Test.\nYou need to wait for the right to start the test.";
    public static final String Error_Cannot_Load_Profile_Picture = "No Internet Connection Found.\nCan't load  Your Profile Picture";
    public static final String Error_Cannot_Load_Profile_Picture_1 = "Failed to Load Profile Picture";
    public static final String Error_GET_WRITE_PERMISSION = "Need Permission for You Storage.\nKindly Grant it";
    public static final String Error_Following = "Following Errors prohibited the update operation:\n";

    // URL's Strings
    public static final String URL_Base = "http://www.naseem.education/";
    public static final String URL_Base_1 = "https://naseem-prod-ibrahimbashir.c9users.io/";
    public static final String URL_Login = URL_Base_1 + "api/v1/sessions";
    public static final String URL_School_Section = URL_Base_1 + "api/v1/school_section";
    public static final String URL_Sign_Up = URL_Base_1 + "api/v1/registrations";
    public static final String URL_Get_Test = URL_Base_1 + "api/v1/tests/getTest?auth=";
    public static final String URL_Logout = URL_Base_1 + "api/v1/sessions/";
    public static final String URL_ShowTest = URL_Base_1 + "api/v1/tests/getTakenTest?auth=";
    public static final String URL_Start_Test = URL_Base_1 + "api/v1/tests/startTest?auth=";
    public static final String URL_Principal_Students = URL_Base_1 + "api/v1/tests/getStudents?auth=";
    public static final String URL_Principal_Teachers = URL_Base_1 + "api/v1/tests/getTeachers?auth=";
    public static final String URL_Principal_User = URL_Base_1 + "api/v1/users/getUser?auth=";
    public static final String URL_Principal_Update_User = URL_Base_1 + "api/v1/users/update_user?auth=";

    // Keys for USER JSON Object
    public static final String User_Authenticating = "Authenticating . . .";
    public static final String User_Registering = "Registering . . .";
    public static final String User_Updating = "Updating . . .";
    public static final String User_Updated_1  = "Profile of User ";
    public static final String User_Updated_2  = " Updated Successfully";

    public static final String USER_U_Name = "uname";
    public static final String USER_U_Pass = "pass";
    public static final String USER_U_School_Name = "schoolname";
    public static final String USER_U_Section_Name = "sectionname";
    public static final String USER_U_School_Id = "schoolid";
    public static final String USER_U_Section_Id = "sectionid";

    public static final String User_Key_Id = "id";
    public static final String User_Key_Username = "username";
    public static final String User_Key_Email = "email";
    public static final String User_Key_First_name = "first_name";
    public static final String User_Key_Second_name = "second_name";
    public static final String User_Key_School_name = "school_name";
    public static final String User_Key_Section_name = "section_name";
    public static final String User_Key_Gender = "gender";
    public static final String User_Key_Age = "age";
    public static final String User_Key_role = "role";
    public static final String User_Key_Telephone = "telephone";
    public static final String User_Key_School_id = "school_id";
    public static final String User_Key_Section_id = "section_id";
    public static final String User_Key_Avatar = "avatar";
    public static final String User_Key_Thumb = "thumb";
    public static final String User_Key_URL = "url";
    public static final String User_Key_Authentication_token = "authentication_token";
    public static final String User_Key_Page = "page";
    public static final String User_Key_U_Id = "u_id";
    public static final String User_Key_User = "user";


    // Key for School and Section JSON Object
    public static final String School = "school";
    public static final String Section = "section";

    public static final String School_Key_Id = "id";
    public static final String School_Key_Name = "name";
    public static final String Section_Key_Id = "id";
    public static final String Section_Key_Name = "name";
    public static final String Section_Key_School_Id = "school_id";


    // Keys for Test Taken and Test Schedule
    public static final String Key_Test_Schedule = "tests";
    public static final String Key_Test_Taken = "test_taken";
    public static final String Key_Test_Id = "id";
    public static final String Key_Test_Name = "name";
    public static final String Key_Test_Start_Time = "start_time";
    public static final String Key_Test_Attempt_Time = "attempt_time";
    public static final String Key_Test_Teacher_Id = "teacher_id";

    // Keys for get Show Test
    public static final String User_Key_Test_Id = "testid";
    public static final String Test_Key_Text_Questions = "text_Q";
    public static final String Test_Key_Single_Choice_Questions = "single_CQ";
    public static final String Test_Key_Multi_Choice_Questions = "multi_CQ";
    public static final String Test_Key_Paragraph_Questions = "paragraph_questions";
    public static final String Test_Key_Boolean_Questions = "boolean_Questions";
    public static final String Test_Key_Generic_Id = "id";
    public static final String Test_Key_Generic_Title = "title";
    public static final String Test_Key_Generic_Marks = "marks";
    public static final String Test_Key_Generic_Std_Answer = "std_answer";
    public static final String Test_Key_Generic_Test_Id = "test_id";
    public static final String Test_Key_Generic_Answer = "answer";
    public static final String Test_Key_Single_Choice_Options = "list_item_single_choice_options";
    public static final String Test_Key_Single_Choice_Options_Id = "id";
    public static final String Test_Key_Single_Choice_Options_Optioon = "option";
    public static final String Test_Key_Single_Choice_Options_Correct = "correct";
    public static final String Test_Key_Single_Choice_Options_Question_Id = "single_choice_question_id";
    public static final String Test_Key_Multi_Choice_Options = "list_item_multi_choice_options";
    public static final String Test_Key_Multi_Choice_Options_Id = "id";
    public static final String Test_Key_Multi_Choice_Options_Option = "option";
    public static final String Test_Key_Multi_Choice_Options_Correct = "correct";
    public static final String Test_Key_Multi_Choice_Options_Question_Id = "multi_choice_question_id";
    public static final String Test_Key_Paragraph_Id = "id";
    public static final String Test_Key_Paragraph_Answer = "answer";
    public static final String Test_Key_Paragraph_Student_Id = "student_id";
    public static final String Test_Key_Paragraph_Paragraph_Id = "paragraph_question_id";
    public static final String Test_Key_Paragraph_Marks = "marks";
    public static final String Test_Key_Boolean_Correct = "correct";
    public static final String Test_Key_Boolean_Boolean_Answers = "boolean_answers";
    public static final String Test_Key_Boolean_Boolean_Answers_Id  = "id";
    public static final String Test_Key_Boolean_Boolean_Answers_Answer  = "answer";
    public static final String Test_Key_Boolean_Boolean_Answers_Student_Id  = "student_id";
    public static final String Test_Key_Boolean_Boolean_Answers_Boolean_Question_Id  = "boolean_question_id";


    // Keys for Principal
    public static final String Principal_Key_Students_Teachers = "results";
    public static final String Principal_Key_Update_User_P_Id = "u_id";
    public static final String Principal_Key_Update_User_Id = "id";
    public static final String Principal_Key_Update_User_First_Name = "first_name";
    public static final String Principal_Key_Update_User_Second_Name = "second_name";
    public static final String Principal_Key_Update_User_Password = "password";
    public static final String Principal_Key_Update_User_Password_Confirmation = "password_confirmation";
    public static final String Principal_Key_Update_User_Email = "email";
    public static final String Principal_Key_Update_User_Username = "username";
    public static final String Principal_Key_Update_User_Section_Name = "section_name";
    public static final String Principal_Key_Update_User_Gender = "gender";
    public static final String Principal_Key_Update_User_Age = "age";
    public static final String Principal_Key_Update_User_Role = "role";
    public static final String Principal_Key_Update_User_Telephone = "telephone";
    public static final String Principal_Key_Update_User_Avatar = "avatar";




    // Naseem Youtube API KEY
    public static final String Naseem_Youtube_API = "AIzaSyBwbhm_f4nzjmAYsE06hA8EaWUh6u77vUE";

    public static final String Jan_ID_1 = "EW5-rJUJEbQ";
    public static final String Jan_ID_1_Desc = "JAN Cartoon New Episode with Guriya Kids - SEE TV";
    public static final String Jan_ID_2 = "B4EKJOQqKtA";
    public static final String Jan_ID_2_Desc = "jaan cartoon EPI# 45";
    public static final String Jan_ID_3 = "zT0bU2OiDhs";
    public static final String Jan_ID_3_Desc = "jan cartoon episode no 27 Buray ke saat bhalai";

    public static final String Motu_Patlu_ID_1 = "p6ZkIt7enIo";
    public static final String Motu_Patlu_ID_1_Desc = "Lambi Lambi Naak - Motu Patlu in Hindi - 3D Animation Cartoon for Kids -As seen on Nickelodeon";
    public static final String Motu_Patlu_ID_2 = "mqHupUB8zFg";
    public static final String Motu_Patlu_ID_2_Desc = "The Bulk - Motu Patlu in Hindi - ENGLISH, SPANISH & FRENCH SUBTITLES! -As seen on Nickelodeon";
    public static final String Motu_Patlu_ID_3 = "u-T3oHbXeT0";
    public static final String Motu_Patlu_ID_3_Desc = "Robot Of Furfuri Nagar - Motu Patlu in Hindi - ENGLISH, SPANISH & FRENCH SUBTITLES!";

    public static final String Meena_ID_1 = "cMGQftehjgQ";
    public static final String Meena_ID_1_Desc = "Meena Kids Cartoons Episode 1 Urdu/Hindi";
    public static final String Meena_ID_2 = "0g0Re2WNztY";
    public static final String Meena_ID_2_Desc = "Meena Kids Cartoons Episode 2 Urdu/Hindi";
    public static final String Meena_ID_3 = "aRAe3VPer6g";
    public static final String Meena_ID_3_Desc = "Meena Kids Cartoons Episode 3 Urdu/Hindi";

    public static final String Tom_And_Jerry_ID_1 = "gDM3NYwMQLU";
    public static final String Tom_And_Jerry_ID_1_Desc = "NEW TOM and JERRY CARTOON 2012 Episode 12 HD";
    public static final String Tom_And_Jerry_ID_2 = "7oKBhEy7Jeo";
    public static final String Tom_And_Jerry_ID_2_Desc = "Tom and Jerry Full Episode Cartoon For Child 2017";
    public static final String Tom_And_Jerry_ID_3 = "TjenbtaxKqo";
    public static final String Tom_And_Jerry_ID_3_Desc = "Tom And Jerry Full Episodes HD - Tom and Jerry Collection 11";

    public static final String Donal_Duck_ID_1 = "S0Gm9CufsN8";
    public static final String Donal_Duck_ID_1_Desc = "Donald Duck With Chip & Dale and Donald Nephews Cartoons Episodes New Collection # 7";
    public static final String Donal_Duck_ID_2 = "riBpB1ECI0I";
    public static final String Donal_Duck_ID_2_Desc = "1080 Donald Duck - Chip and dale - Pluto / Donald Duck Cartoons Full Episodes New HD";
    public static final String Donal_Duck_ID_3 = "SulqdDVmJMY";
    public static final String Donal_Duck_ID_3_Desc = "1080 Donald Duck - Chip and Dale Cartoons Full Episodes - Best Collection 2017 - Mickey and Pluto";

    public static final String Pink_Panther_ID_1 = "u4gWmPNZ7-Y";
    public static final String Pink_Panther_ID_1_Desc = "Pink Trek | The Pink Panther (1993)";
    public static final String Pink_Panther_ID_2 = "3xxrThc9V-I";
    public static final String Pink_Panther_ID_2_Desc = "Three Aliens and a Footstool | The Pink Panther (1993)";
    public static final String Pink_Panther_ID_3 = "svlLdSBOz8w";
    public static final String Pink_Panther_ID_3_Desc = "The Pink Stuff | The Pink Panther (1993)";
}
