package com.example.donotforget;

import java.sql.Date;

public class Message {
    private int id;
    private String message;
    private Date date;

    public Message(String message, Date date){
        this.message = message;
        this.date = date;
    }

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}
    public Date getDate(){return date;}
    public void setDate(Date date){this.date = date;}
}
