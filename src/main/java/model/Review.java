package model;

public class Review {
    private int board_idx;
    private String title;
    private String content;
    private String writer;

    public Review(int board_idx, String title, String content,String writer) {
        this.board_idx = board_idx;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }
    public int getBoard_idx() {return board_idx;}
    public String getTitle() {
        return title;
    }
    public String getWriter() {
        return writer;
    }
    // Getters and Setters
}
