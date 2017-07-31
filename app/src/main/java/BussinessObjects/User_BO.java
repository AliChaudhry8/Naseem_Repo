package BussinessObjects;

import java.io.Serializable;

/**
 * Created by Muhammad Taimoor on 5/22/2017.
 */

public class User_BO implements Serializable{
    private int Id;
    private String Username;
    private String Email;
    private String First_name;
    private String second_name;
    private String School_name;
    private String Section_name;
    private String Gender;
    private int Age;
    private String role;
    private String Telephone;
    private int School_id;
    private int Section_id;
    private String Avatar;
    private String Thumb;
    private String Authentication_token;
    private String password;
    private String password_confirmation;

    public User_BO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getSchool_name() {
        return School_name;
    }

    public void setSchool_name(String school_name) {
        School_name = school_name;
    }

    public String getSection_name() {
        return Section_name;
    }

    public void setSection_name(String section_name) {
        Section_name = section_name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public int getSchool_id() {
        return School_id;
    }

    public void setSchool_id(int school_id) {
        School_id = school_id;
    }

    public int getSection_id() {
        return Section_id;
    }

    public void setSection_id(int section_id) {
        Section_id = section_id;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    public String getAuthentication_token() {
        return Authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        Authentication_token = authentication_token;
    }
}
