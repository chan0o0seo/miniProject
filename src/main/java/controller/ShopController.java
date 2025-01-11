package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;
import service.ShopService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/shop/*")
@MultipartConfig
public class ShopController extends HttpServlet {
    private ShopService shopService;


    public ShopController() {
        shopService = new ShopService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
        String action = req.getPathInfo();
        System.out.println("action: " + action);
        if(action.equals("/register")) {
            req.getRequestDispatcher("/view/shop/register.jsp").forward(req, resp);
        } else if (action.equals("/list")) {
            List<Product> productList = shopService.getProducts();
            req.setAttribute("productList", productList);
            req.getRequestDispatcher("/view/shop/list.jsp").forward(req, resp);
        } else if (action.equals("/content")) {
            Product product = shopService.getProduct(Integer.parseInt(req.getParameter("idx")));
            req.setAttribute("product", product);
            req.getRequestDispatcher("/view/shop/content.jsp").forward(req, resp);
        } else if(action.equals("/mybag")) {

            User user = (User) req.getSession().getAttribute("user");
            if(user == null) {
                resp.sendRedirect("/user/login");
            }
            System.out.println("mybag");
            System.out.println("user: " + user.getIdx());
            List<Product> productList = this.shopService.getProductBags(user.getIdx());
            req.setAttribute("productList", productList);
            System.out.println("productListt: " + productList);
            req.getRequestDispatcher("/view/user/mybag.jsp").forward(req, resp);
        } else if(action.equals("/addbag")) {
            User user = (User) req.getSession().getAttribute("user");
            System.out.println("addBag");
            if(user == null) {
                resp.sendRedirect("/user/login");
            }
            System.out.println("user: " + user.getIdx());
            Bag bag = new Bag(user.getIdx(),Integer.parseInt(req.getParameter("idx")));
            if(!this.shopService.selectBagByBag(bag)) {
                this.shopService.addProductBag(bag);
            }
            resp.sendRedirect("/shop/list");
        } else if(action.equals("/delete")) {
            shopService.deleteProductById(Integer.parseInt(req.getParameter("idx")));
            resp.sendRedirect("/shop/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
        if(req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("/user/login");
        }
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getPathInfo();
        if (action.equals("/register")) {
            // 파일 업로드 처리
            Part filePart = req.getPart("file"); // 'file'은 HTML 폼에서 지정한 name 속성
            if (filePart != null) {
                String fileName = filePart.getSubmittedFileName()+user.getEmail(); // 업로드된 파일 이름 가져오기
                System.out.println("파일 이름: " + fileName); // 유니크하게 저장하기

                // 파일을 저장할 위치 지정
                String uploadDir = getServletContext().getRealPath("/uploads/");
                System.out.println("uploadDir: " + uploadDir);
                File uploads = new File(uploadDir);
                if (!uploads.exists()) {
                    uploads.mkdir(); // 디렉토리가 없다면 생성
                }

                // 파일 저장
                filePart.write(uploadDir + File.separator + fileName);
                System.out.println("파일이 저장되었습니다: " + uploadDir + File.separator + fileName);
                shopService.addProduct(new Product(
                        req.getParameter("name"),
                        req.getParameter("original_price"),
                        req.getParameter("price"),
                        req.getParameter("description"),
                        req.getParameter("quantity"),
                        "/uploads/"+fileName
                        ));

                resp.sendRedirect("/shop/list");
                //req.setAttribute("uploadedFilePath", "/uploads/" + fileName);
            }

        } else if (action.equals("/list")) {

            List<Product> productList = shopService.getProducts();

            req.setAttribute("productList", productList);
            req.getRequestDispatcher("/view/shop/list.jsp").forward(req, resp);
        }
    }
}
