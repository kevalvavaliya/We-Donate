package com.infotech.wedonate.data;

public class user_model {
   private String name,email,usertype,mobile,address;
    int code;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
