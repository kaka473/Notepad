package com.example.notepad;

public class NoteItem {
    private long id;
    private String content;
    private String time;
    private int tag;

    public NoteItem(String content, String time, int tag)
    {
        this.content=content;
        this.time=time;
        this.tag=tag;
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

    public void setTag(int tag) {
        this.tag = tag;
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

    public int getTag() {
        return tag;
    }


}
