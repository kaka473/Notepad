package com.example.notepad.Todo;

public class TodoItem {
    private long id;
    private String content;
    private String time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public TodoItem() {
        super();
        content="";
        time="";
    }
    public TodoItem(String content, String time)
    {
        this.content=content;
        this.time=time;
    }
}
