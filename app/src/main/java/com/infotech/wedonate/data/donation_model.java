package com.infotech.wedonate.data;

public class donation_model {
    String charity_name;
    String address;
    String item_name;
    String item_desc;
    String item_category;
    String code;
    String time;
    String charity_email;
    String charity_mobile;
    String donor_email;
    String donor_name;
    String mem_email;
    String donor_mobile;
    int id;


    public String getCharity_email() {
        return charity_email;
    }

    public void setCharity_email(String charity_email) {
        this.charity_email = charity_email;
    }

    public String getCharity_mobile() {
        return charity_mobile;
    }

    public void setCharity_mobile(String charity_mobile) {
        this.charity_mobile = charity_mobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCharity_name() {
        return charity_name;
    }

    public void setCharity_name(String charity_name) {
        this.charity_name = charity_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDonor_email() {
        return donor_email;
    }

    public void setDonor_email(String donor_email) {
        this.donor_email = donor_email;
    }

    public String getDonor_name() {
        return donor_name;
    }

    public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }

    public String getDonor_mobile() {
        return donor_mobile;
    }

    public void setDonor_mobile(String donor_mobile) {
        this.donor_mobile = donor_mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
