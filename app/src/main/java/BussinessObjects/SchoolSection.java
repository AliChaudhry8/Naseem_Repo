package BussinessObjects;

import java.util.ArrayList;

/**
 * Created by Muhammad Taimoor on 5/23/2017.
 */

public class SchoolSection {
    private ArrayList<School_BO> school;
    private ArrayList<Section_BO> section;

    public SchoolSection() {
        school = new ArrayList<School_BO>();
        section = new ArrayList<Section_BO>();
    }

    public SchoolSection(ArrayList<School_BO> school, ArrayList<Section_BO> section) {
        this.school = new ArrayList<School_BO>();
        this.section = new ArrayList<Section_BO>();
        this.school = school;
        this.section = section;
    }

    public ArrayList<School_BO> getSchool() {
        return school;
    }

    public void setSchool(ArrayList<School_BO> school) {
        this.school = school;
    }

    public ArrayList<Section_BO> getSection() {
        return section;
    }

    public void setSection(ArrayList<Section_BO> section) {
        this.section = section;
    }
}
