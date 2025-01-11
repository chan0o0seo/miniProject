package valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidPassword(String password) {
        // 정규식 정의
        String regex = "^(?=.*[A-Z])(?=.*[\\W_]).{8,}$";

        // 정규식 패턴을 컴파일
        Pattern pattern = Pattern.compile(regex);

        // 패턴에 맞는지 검사
        Matcher matcher = pattern.matcher(password);

        // 일치하면 true, 아니면 false
        return matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        // 정규식 패턴을 컴파일
        Pattern pattern = Pattern.compile(regex);

        // 패턴에 맞는지 검사
        Matcher matcher = pattern.matcher(email);

        // 일치하면 true, 아니면 false
        return matcher.matches();
    }
}
