package model.dao;

import model.Board;
import model.Post;
import model.Review;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private Connection conn;
    List<Review> reviewList;

    public ReviewDao(Connection conn) {
        this.reviewList = new ArrayList<>();
        this.conn = conn;
    }

    public void insertReview(Review review) {
        String sql = "INSERT INTO review (board_idx,writer,title, content) " +
                "VALUE ('"+review.getBoard_idx()+"','"+review.getWriter()+"','"+review.getTitle()+"','"+review.getContent()+"')";

        try {
            System.out.println(sql);
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Review> selectReview(int board_idx) {
        reviewList.clear();
        String sql = "SELECT * FROM review where board_idx = '"+board_idx+"'";
        System.out.println(sql);
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while(resultSet.next()) {
                System.out.println(resultSet.getString("writer"));
                reviewList.add(new Review(Integer.parseInt(resultSet.getString("board_idx")),resultSet.getString("title"), resultSet.getString("content"),resultSet.getString("writer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviewList;
    }
}
