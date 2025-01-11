package service;

import common.DbConnection;
import model.User;
import model.dao.UserDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import valid.Validator;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao(DbConnection.getConnection());
    }

    public boolean signup(User user) {

        // 입력값 검증

        String password = user.getPassword();
        String email = user.getEmail();

        // password 유효성 검사
        if(!Validator.isValidPassword(password)) {
            System.out.println("Invalid password");
            return false;
        }
        // email 유효성 검사
        if(!Validator.isValidEmail(email)) {
            System.out.println("Invalid email");
            return false;
        }

        // 비밀번호 암호화 처리

        System.out.println("Password: " + user.getPassword());
        String hashedPassword = String.valueOf(user.getPassword().hashCode());
        user.setPassword(hashedPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        // 처리 끝나고 DB에 저장
        this.userDao.insertUser(user);
        return true;
    }

    public User login(User user) {

        User u = this.userDao.selectUser(user);
        String hashedPassword = String.valueOf(user.getPassword().hashCode());

        if(u == null) {
            return null;
        }

        if (u.getPassword().equals(hashedPassword)) {
            System.out.println("Password matches!");
            return u;
        } else {
            System.out.println("Password does not match.");
            return null;
        }

    }

    public User findUserByEmail(String email) {
        return this.userDao.findUserByEmail(email);
    }

}
