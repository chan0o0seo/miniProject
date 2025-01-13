package controller;

import api.Api;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Board;
import model.Post;
import model.Review;
import model.User;
import org.json.JSONObject;
import service.BoardService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {

    private BoardService boardService;

    public BoardController() {
        boardService = new BoardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();


        if(action.equals("/list")) {
            // 게시판 버튼을 클릭하면 들어오는 요청
            // list 요청 받으면 post 리스트 만들어서 board.jsp 띄워주기

            List<Post> postList = boardService.getPost();
            req.setAttribute("postList", postList);
            req.getRequestDispatcher("/view/board/board.jsp").forward(req, resp);
        } else if(action.equals("/add")) {
            // 게시물 작성버튼을 클릭하면 들어오는 요청
            // add 요청 받으면 새로운 board db에 넣고 /board/list로 요청보내서 board.jsp로 이동
            Board board = new Board(
                    req.getParameter("title"),
                    req.getParameter("content"),
                    "writer"
            );
            boardService.write(board);
            resp.sendRedirect("/board/list");
        } else if(action.equals("/content")) {
            // 게시판 목록 클릭하면 들어오는 요청
            // 해당 게시글 내용과 리뷰를 담아서 content.jsp로 이동
            int idx = Integer.parseInt(req.getParameter("idx"));
            Post post = boardService.getContent(idx);
            List<Review> reviewList = boardService.getReview(Integer.parseInt(req.getParameter("idx")));

            System.out.println("reviewList: " + reviewList);
            req.setAttribute("idx", idx);
            req.setAttribute("post", post);
            req.setAttribute("reviewList", reviewList);
            req.getRequestDispatcher("/view/board/content.jsp").forward(req, resp);
        }else if(action.equals("/delete")) {
            boardService.deleteBoard(Integer.parseInt(req.getParameter("idx")));
            resp.sendRedirect("/board/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        // 세션에 user가 없으면 logout하고 메인페이지로 이동
        if(req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("/user/logout");
        }

        if(action.equals("/list")) {
            List<Post> postList = boardService.getPost();
            req.setAttribute("postList", postList);
            req.getRequestDispatcher("/view/board/board.jsp").forward(req, resp);
        } else if(action.equals("/add")) {
            User user = (User)req.getSession().getAttribute("user");
            Board board = new Board(
                    req.getParameter("title"),
                    req.getParameter("content"),
                    user.getName()
            );
            boardService.write(board);
            resp.sendRedirect("/board/list");
        } else if(action.equals("/review")) {
            User user = (User)req.getSession().getAttribute("user");
            if(user != null) {
                Review review = new Review(
                        Integer.parseInt(req.getParameter("boardIdx")),
                        req.getParameter("title"),
                        req.getParameter("content"),
                        user.getName()
                );
                boardService.insertReview(review);
                resp.sendRedirect("/board/content?idx=" + review.getBoard_idx());
            } else {
                resp.sendRedirect("/user/login");
            }
        } else if(action.equals("/payment")) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());

            JSONObject jsonObject = new JSONObject(sb.toString());

            String paymentId = jsonObject.getString("paymentId");
            System.out.println("paymentId: " + paymentId);

            try {
                JSONObject jsonObject1 = Api.getPaymentInfo(paymentId);
                System.out.println("jsonObject1: " + jsonObject1);
                String status = jsonObject1.getString("status");
                System.out.println("status: " + status);

                JSONObject amountObject = jsonObject1.getJSONObject("amount");
                int amount = amountObject.getInt("total");
                System.out.println("amount: " + amount);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
