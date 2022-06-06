package com.usman.testhms3.Dashboard.My_Data;

public class Transiction_Model
{
    String student_id, transiction_date, transiction_type, transiction_id, transiction_amount, transiction_remarks;

    public Transiction_Model() {
    }

    public Transiction_Model(String student_id, String transiction_date, String transiction_type, String transiction_id, String transiction_amount, String transiction_remarks) {
        this.student_id = student_id;
        this.transiction_date = transiction_date;
        this.transiction_type = transiction_type;
        this.transiction_id = transiction_id;
        this.transiction_amount = transiction_amount;
        this.transiction_remarks = transiction_remarks;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTransiction_date() {
        return transiction_date;
    }

    public void setTransiction_date(String transiction_date) {
        this.transiction_date = transiction_date;
    }

    public String getTransiction_type() {
        return transiction_type;
    }

    public void setTransiction_type(String transiction_type) {
        this.transiction_type = transiction_type;
    }

    public String getTransiction_id() {
        return transiction_id;
    }

    public void setTransiction_id(String transiction_id) {
        this.transiction_id = transiction_id;
    }

    public String getTransiction_amount() {
        return transiction_amount;
    }

    public void setTransiction_amount(String transiction_amount) {
        this.transiction_amount = transiction_amount;
    }

    public String getTransiction_remarks() {
        return transiction_remarks;
    }

    public void setTransiction_remarks(String transiction_remarks) {
        this.transiction_remarks = transiction_remarks;
    }
}
