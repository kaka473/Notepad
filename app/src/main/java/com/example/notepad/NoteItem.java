package com.example.notepad;

public class NoteItem {
    private long id;
    private String content;
    private String time;

    public NoteItem() {
        super();
        content="";
        time="";
    }
    public NoteItem(String content, String time)
    {
        this.content=content;
        this.time=time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }



}
