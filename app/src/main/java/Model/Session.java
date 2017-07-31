package Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import BussinessObjects.User_BO;

/**
 * Created by Muhammad Taimoor on 5/22/2017.
 */

public class Session {
    private SharedPreferences shared;
    private SharedPreferences.Editor ed;

    public Session(Context c)
    {
        shared = PreferenceManager.getDefaultSharedPreferences(c);
        ed = shared.edit();
    }

    public void create_session(User_BO user)
    {
        ed.putInt(Constants.User_Key_Id, user.getId());
        ed.putString(Constants.User_Key_Username, user.getUsername());
        ed.putString(Constants.User_Key_Email, user.getEmail());
        ed.putString(Constants.User_Key_First_name, user.getFirst_name());
        ed.putString(Constants.User_Key_Second_name, user.getSecond_name());
        ed.putString(Constants.User_Key_School_name, user.getSchool_name());
        ed.putString(Constants.User_Key_Section_name, user.getSection_name());
        ed.putString(Constants.User_Key_Gender, user.getGender());
        ed.putInt(Constants.User_Key_Age, user.getAge());
        ed.putString(Constants.User_Key_role, user.getRole());
        ed.putString(Constants.User_Key_Telephone, user.getTelephone());
        ed.putInt(Constants.User_Key_School_id, user.getSchool_id());
        ed.putInt(Constants.User_Key_Section_id, user.getSection_id());
        ed.putString(Constants.User_Key_Avatar, user.getAvatar());
        ed.putString(Constants.User_Key_Thumb, user.getThumb());
        ed.putString(Constants.User_Key_Authentication_token, user.getAuthentication_token());
        ed.commit();
    }

    public void destroy_session()
    {
        ed.remove(Constants.User_Key_Id);
        ed.remove(Constants.User_Key_Username);
        ed.remove(Constants.User_Key_Email);
        ed.remove(Constants.User_Key_First_name);
        ed.remove(Constants.User_Key_Second_name);
        ed.remove(Constants.User_Key_School_name);
        ed.remove(Constants.User_Key_Section_name);
        ed.remove(Constants.User_Key_Gender);
        ed.remove(Constants.User_Key_Age);
        ed.remove(Constants.User_Key_role);
        ed.remove(Constants.User_Key_Telephone);
        ed.remove(Constants.User_Key_School_id);
        ed.remove(Constants.User_Key_Section_id);
        ed.remove(Constants.User_Key_Avatar);
        ed.remove(Constants.User_Key_Thumb);
        ed.remove(Constants.User_Key_Authentication_token);
        ed.commit();
    }

    public int getUserId(){
        return shared.getInt(Constants.User_Key_Id, -1);
    }
    public String getUsername(){
        return shared.getString(Constants.User_Key_Username, "");
    }
    public String getSchoolName(){
        return shared.getString(Constants.User_Key_School_name, "");
    }
    public String getSectionName(){return shared.getString(Constants.User_Key_Section_name, "");}
    public String getFirstName(){
        return shared.getString(Constants.User_Key_First_name, "");
    }
    public String getSecondName(){return shared.getString(Constants.User_Key_Second_name, "");}
    public String getRole(){
        return shared.getString(Constants.User_Key_role, "");
    }
    public String getAvatar(){
        return shared.getString(Constants.User_Key_Avatar, "");
    }
    public String getTelephone(){return shared.getString(Constants.User_Key_Telephone, "");}
    public String getThumb(){
        return shared.getString(Constants.User_Key_Thumb, "");
    }
    public String getEmail(){return shared.getString(Constants.User_Key_Email, "");}
    public int getSchoolId(){
        return shared.getInt(Constants.User_Key_School_id, -1);
    }
    public int getSectionId(){
        return shared.getInt(Constants.User_Key_Section_id, -1);
    }
    public String getAuthenticationToken(){ return shared.getString(Constants.User_Key_Authentication_token, "");}
}
