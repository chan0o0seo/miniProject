package model.dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO user (email, password, name, type) VALUES (?, ?, ?, ?)";


        try {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());   // 첫 번째 ?에 user.getEmail() 값 바인딩
            pstmt.setString(2, user.getPassword()); // 두 번째 ?에 user.getPassword() 값 바인딩
            pstmt.setString(3, user.getName());     // 세 번째 ?에 user.getName() 값 바인딩
            pstmt.setString(4, user.getType());

            // 쿼리 실행
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User selectUser(User user) {
        String sql = "SELECT * FROM user WHERE email = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) return new User(Integer.parseInt(resultSet.getString("idx")),resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("name"),resultSet.getString("type"));
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) return new User(resultSet.getInt("idx"),resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("name"),resultSet.getString("type"));
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
