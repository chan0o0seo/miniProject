package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.KakaoApi;
import model.User;
import service.UserService;
import java.io.IOException;
import org.json.JSONObject;
import java.util.Map;


@WebServlet("/user/*")
public class UserController extends HttpServlet {
    private User nowUser;
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        System.out.println(action);
        if ("/login".equals(action)) {
            req.getRequestDispatcher("/view/user/login.jsp").forward(req, resp);
        } else if ("/signup".equals(action)) {
            System.out.println("signup");
            Cookie sCookie = new Cookie("signup", null); // 쿠키 이름과 null 값
            sCookie.setMaxAge(0); // 쿠키 즉시 만료
            sCookie.setPath("/"); // 경로 지정
            resp.addCookie(sCookie);
            req.getRequestDispatcher("/view/user/signup.jsp").forward(req, resp);
        } else if("/logout".equals(action)) {
            Cookie loginCookie = new Cookie("demkq", null); // 쿠키 이름과 null 값
            loginCookie.setMaxAge(0); // 쿠키 즉시 만료
            loginCookie.setPath("/"); // 경로 지정
            resp.addCookie(loginCookie);
            req.getSession().setAttribute("user", null);
            nowUser = null;
            System.out.println("logout");
            req.getRequestDispatcher("/").forward(req, resp);
        } else if ("/mypage".equals(action)) {
            User user = (User)req.getSession().getAttribute("user");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/view/user/mypage.jsp").forward(req, resp);
        } else if ("/kakao".equals(action)) {
            System.out.println("kakao");
            String code = req.getParameter("code");
            System.out.println(code);
            String token = KakaoApi.getAccessToken(code);
            System.out.println(token);
            try {
                JSONObject userInfo = KakaoApi.getUserInfo(token);
                Map<String,String> userInfoMap = KakaoApi.printUserInfo(userInfo);
                //가입된 카카오 계정이 있을 때
                User user = userService.findUserByEmail(userInfoMap.get("email"));
                if(user!=null) {
                    //로그인 완료!
                    Cookie loginCookie = new Cookie("demkq", "ekwer1");
                    loginCookie.setMaxAge(3600);
                    loginCookie.setPath("/");
                    req.getSession().setAttribute("user", user);
                    resp.addCookie(loginCookie);
                    // JSP로 포워드
                    req.getRequestDispatcher("/").forward(req, resp);
                }
                // 가입된 카카오 계정이 없을 때
                else {
                    user = new User(
                            userInfoMap.get("email"),
                            "qwer1234Q!",
                            userInfoMap.get("userName"),
                            "K"
                    );
                    userService.signup(user);
                    resp.sendRedirect("/");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if(action.equals("/mybag")) {
            resp.sendRedirect("/shop/mybag");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        if ("/signup".equals(action)) {
            User user = new User(
                    req.getParameter("email"),
                    req.getParameter("password"),
                    req.getParameter("userName"),
                    "U"
            );

            System.out.println(user.getName());
            if(userService.signup(user)) {
                resp.sendRedirect("/");
            } else {
                req.getRequestDispatcher("/view/user/signup.jsp").forward(req, resp);
            }
        }
        else if ("/login".equals(action)) {
            User user = new User(
                    req.getParameter("email"),
                    req.getParameter("password")
            );
            User temp = userService.login(user);
            if(temp != null) {
                // login 확인 쿠키 httsp only x
                Cookie loginCookie = new Cookie("demkq", "ekwer1");
                loginCookie.setMaxAge(3600);
                loginCookie.setPath("/");
                req.getSession().setAttribute("user", temp);
                resp.addCookie(loginCookie);
                // JSP로 포워드
                req.getRequestDispatcher("/").forward(req, resp);
            } else {
                resp.sendRedirect("/user/login");
                //req.getRequestDispatcher("/view/user/login.jsp").forward(req, resp);
            }
        }
    }

}