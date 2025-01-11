package model;

public class Board {
    private int idx;
    private String title;
    private String content;
    private String writer;

    public Board(int idx,String title, String content, String writer) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
    public String getWriter() {
        return writer;
    }
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getIdx() {
        return idx;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
