package com.example.notepad;

public class NoteItem {
    private long id;
    private String title;
    private String content;
    private String time;

    public NoteItem() {
        super();
        title="";
        content="";
        time="";
    }
    public NoteItem(String title,String content, String time)
    {
        this.title=title;
        this.content=content;
        this.time=time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
