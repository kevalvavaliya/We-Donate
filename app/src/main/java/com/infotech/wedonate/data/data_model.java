package com.infotech.wedonate.data;

public class data_model {
    public String name, email, pass, mobile,usertype,charityemail,otp,activity,address,item_name,item_desc,item_category;
    int code;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setCharityemail(String charityemail) {
        this.charityemail = charityemail;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setActivity(String activity) {
        this.activity = activity;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getCharityemail() {
        return charityemail;
    }
}
