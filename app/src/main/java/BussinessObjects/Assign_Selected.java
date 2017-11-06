package BussinessObjects;

import java.util.ArrayList;

import Model.Groups.Assign_Students_Adapter;

/**
 * Created by DELL on 9/22/2017.
 */

public class Assign_Selected {

    private ArrayList<User_BO> users;
    private ArrayList<String> selected;

    public Assign_Selected(){
        users = new ArrayList<User_BO>();
        selected = new ArrayList<String>();
    }
    public ArrayList<User_BO> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User_BO> users) {
        this.users = users;
    }

    public ArrayList<String> getSelected() {
        return selected;
    }

    public void setSelected(ArrayList<String> selected) {
        this.selected = selected;
    }



}
