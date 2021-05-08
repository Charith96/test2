package com.example.studentdemo;

import java.sql.Timestamp;
import java.util.Date;

public class Note {
    private String id;
    private String name;
    private String note;
    private String createDate;


    public Note() {
    }

    public Note(String id, String name, String note, String createDate) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

