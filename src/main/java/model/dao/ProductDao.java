package model.dao;

import model.Bag;
import model.Board;
import model.Post;
import model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private Connection conn;
    List<Product> productList;

    public ProductDao(Connection conn) {
        this.productList = new ArrayList<>();
        this.conn = conn;
    }

    public void insertProduct(Product product) {
        String sql = "INSERT INTO product (name, original_price, price, description, quantity, path) " +
                "VALUE ('"+product.getName()+"','"+product.getOriginal_price()+"','"+product.getPrice()+"','"+product.getDescription()+"','"+product.getQuantity()+"','"+product.getPath()+"')";


        try {
            System.out.println(sql);
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertBag(Bag bag) {
        String sql = "INSERT INTO bag (product_idx, user_idx) " +
                "VALUE ('"+bag.getProduct_idx()+"','"+bag.getUser_idx()+"')";
        System.out.println(sql);
        try {
            System.out.println(sql);
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Product> selectProduct() {
        productList.clear();
        String sql = "SELECT * FROM product";
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while(resultSet.next()) {

                productList.add(
                        new Product(
                                resultSet.getString("product_idx"),
                                resultSet.getString("name"),
                                resultSet.getString("original_price"),
                                resultSet.getString("price"),
                                resultSet.getString("description"),
                                resultSet.getString("quantity"),
                                resultSet.getString("path")
                                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public Product selectBoardByIdx(int idx) {
        String sql = "SELECT * FROM product where product_idx = '"+idx+"'";
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            resultSet.next();
            return new Product(
                    resultSet.getString("product_idx"),
                    resultSet.getString("name"),
                    resultSet.getString("original_price"),
                    resultSet.getString("price"),
                    resultSet.getString("description"),
                    resultSet.getString("quantity"),
                    resultSet.getString("path")
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> selectProductById(int idx) {
        productList.clear();
        String sql = "SELECT * FROM bag where user_idx = '"+idx+"'";
        System.out.println(sql);
        List<Bag> bagList = new ArrayList<>();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while(resultSet.next()) {
                bagList.add(new Bag(
                        resultSet.getInt("user_idx"),
                        resultSet.getInt("product_idx")
                ));
            }
            resultSet.close();
            for(Bag bag : bagList) {
                String sql2 = "SELECT * FROM product where product_idx = '"+bag.getProduct_idx()+"'";

                System.out.println(sql2);
                ResultSet resultSet2 = conn.createStatement().executeQuery(sql2);
                resultSet2.next();
                productList.add( new Product(
                        resultSet2.getString("product_idx"),
                        resultSet2.getString("name"),
                        resultSet2.getString("original_price"),
                        resultSet2.getString("price"),
                        resultSet2.getString("description"),
                        resultSet2.getString("quantity"),
                        resultSet2.getString("path")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public boolean selectBagByBag(Bag bag) {
        String sql = "SELECT * FROM bag WHERE user_idx = '" + bag.getUser_idx() + "' AND product_idx = '" + bag.getProduct_idx() + "'";

        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
