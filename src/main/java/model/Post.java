package model;

public class Post {
    private int idx;
    private String title;
    private String content;
    private String writer;

    public Post(int idx,String title, String content,String writer) {
        this.idx=idx;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
    public int getIdx() {
        return idx;
    }
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
    public String getWriter() {
        return writer;
    }
    // Getters and Setters
}
