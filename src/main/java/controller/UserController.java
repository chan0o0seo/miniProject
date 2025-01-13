package controller;

import common.CookieController;
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
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        System.out.println(action);
        if ("/login".equals(action)) {
            // 로그인 버튼 눌렀을때 이동 -> login.jsp으로 포워딩
            req.getRequestDispatcher("/view/user/login.jsp").forward(req, resp);
        }

        else if ("/signup".equals(action)) {
            // 회원가입 버튼 눌렀을때 이동 -> signup.jsp으로 포워딩
            req.getRequestDispatcher("/view/user/signup.jsp").forward(req, resp);
        }

        else if("/logout".equals(action)) {
            // cookie랑 session에 저장된 user 없애고 메인으로 포워딩
            resp.addCookie(CookieController.makeCookie("demkq",null,"/",0));
            req.getSession().setAttribute("user", null);
            req.getRequestDispatcher("/").forward(req, resp);
        }

        else if ("/mypage".equals(action)) {
            // 마이페이지 버튼 눌렀을 때 -> session에 저장된 user 정보 attribute에 넣고 mypage.jsp로 포워딩
            User user = (User)req.getSession().getAttribute("user");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/view/user/mypage.jsp").forward(req, resp);
        }

        else if ("/kakao".equals(action)) {
            // kakao 로그인
            String code = req.getParameter("code");
            String token = KakaoApi.getAccessToken(code);
            try {
                JSONObject userInfo = KakaoApi.getUserInfo(token);
                Map<String,String> userInfoMap = KakaoApi.printUserInfo(userInfo);
                //가입된 카카오 계정이 있을 때
                User user = userService.findUserByEmail(userInfoMap.get("email"));
                if(user!=null) {
                    //로그인 완료!
                    req.getSession().setAttribute("user", user);
                    resp.addCookie(CookieController.makeCookie("demkq","ekwer1","/",3600));
                    // 메인으로 포워딩
                    req.getRequestDispatcher("/").forward(req, resp);
                }
                // 가입된 카카오 계정이 없을 때 회원가입시키고 메인으로 포워딩
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
        }

        else if(action.equals("/mybag")) {
            // 장바구니 보여주는 요청 잘못들어오는거 shop으로 가게하기
            resp.sendRedirect("/shop/mybag");
        }

        else if("/test".equals(action)) {
            resp.setContentType("text/plain; charset=UTF-8"); // 텍스트 반환
            resp.getWriter().write("ok");  // String 반환
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // login과 signup 요청 처리
        String action = req.getPathInfo();

        if ("/signup".equals(action)) {
            // 회원가입 요청
            User user = new User(
                    req.getParameter("email"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    "U"
            );
            if(userService.signup(user)) {
                // 성공하면 main으로 리다이렉트
                resp.sendRedirect("/");
            } else {
                // 실패하면 회원가입 화면으로 리다이렉트
                resp.sendRedirect("/user/signup");
            }
        }

        else if ("/login".equals(action)) {
            // 로그인 요청이 들어왔을때 email과 password를 가짐
            User user = new User(
                    req.getParameter("email"),
                    req.getParameter("password")
            );
            User temp = userService.login(user);
            if(temp != null) {
                // 맞는 유저가 있을 때
                req.getSession().setAttribute("user", temp);
                resp.addCookie(CookieController.makeCookie("demkq","ekwer2","/",3600));
                // main으로 포워딩
                req.getRequestDispatcher("/").forward(req, resp);
            } else {
                // 유저 없으면 login 화면으로 redirect
                resp.sendRedirect("/user/login");
            }
        }
    }

}