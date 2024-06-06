package com.techbuddys.appui.model;

public class UserModel {
    String u_id;
    String u_name;
    String u_email;
    String u_password;

    public UserModel(String u_id, String u_name, String u_email, String u_password) {
        this.u_id = u_id;
        this.u_name = u_name;
        this.u_email = u_email;
        this.u_password = u_password;
    }

    public UserModel() {
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }
}
