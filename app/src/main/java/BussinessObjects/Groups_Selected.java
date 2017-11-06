package BussinessObjects;

import java.util.ArrayList;

/**
 * Created by DELL on 10/4/2017.
 */

public class Groups_Selected {
    private ArrayList<Group_BO> groups;
    private ArrayList<String> selected;

    public ArrayList<Group_BO> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group_BO> groups) {
        this.groups = groups;
    }

    public ArrayList<String> getSelected() {
        return selected;
    }

    public void setSelected(ArrayList<String> selected) {
        this.selected = selected;
    }

    public Groups_Selected(){
        groups = new ArrayList<Group_BO>();
        selected = new ArrayList<String>();
    }

    public Groups_Selected(ArrayList<Group_BO> groups, ArrayList<String> selected) {
        this.groups = groups;
        this.selected = selected;
    }


}
