package com.infotech.wedonate.data;

public class Token_model {

    String  token,email,donor_email,usertype;

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public Token_model(String token) {
        this.token = token;
    }

    public Token_model() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getDonor_email() {
        return donor_email;
    }

    public void setDonor_email(String donor_email) {
        this.donor_email = donor_email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
