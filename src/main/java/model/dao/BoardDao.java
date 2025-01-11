package model.dao;

import model.Board;
import model.Post;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
    private Connection conn;
    List<Post> postList;

    public BoardDao(Connection conn) {
        this.postList = new ArrayList<>();
        this.conn = conn;
    }

    public void insertBoard(Board board) {
        String sql = "INSERT INTO board (writer, title, content) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, board.getWriter());   // 첫 번째 ?에 writer 값 바인딩
            pstmt.setString(2, board.getTitle());    // 두 번째 ?에 title 값 바인딩
            pstmt.setString(3, board.getContent());  // 세 번째 ?에 content 값 바인딩

            int result = pstmt.executeUpdate();      // 쿼리 실행
            System.out.println("삽입 결과: " + result + "행이 추가되었습니다.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Post> selectBoard() {
        postList.clear();
        String sql = "SELECT * FROM board ORDER BY idx DESC LIMIT 10 OFFSET 0";
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while(resultSet.next()) {
                System.out.println(resultSet.getString("title")+resultSet.getString("content"));
                postList.add(new Post(resultSet.getInt("idx"),resultSet.getString("title"), resultSet.getString("content"),resultSet.getString("writer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return postList;
    }
    public Post selectBoardByIdx(int idx) {
        String sql = "SELECT * FROM board WHERE idx = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idx);  // 첫 번째 ?에 idx 값 바인딩

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {  // 결과가 있을 때만 Post 생성
                    return new Post(
                            resultSet.getInt("idx"),
                            resultSet.getString("title"),
                            resultSet.getString("content"),
                            resultSet.getString("writer")
                    );
                } else {
                    // 결과가 없으면 null 반환 또는 예외 처리
                    System.out.println("해당 idx의 게시글이 없습니다.");
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
