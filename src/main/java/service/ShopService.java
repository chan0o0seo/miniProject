package service;

import common.DbConnection;
import model.*;
import model.dao.BoardDao;
import model.dao.ProductDao;
import model.dao.ReviewDao;

import java.util.List;

public class ShopService {
    private ProductDao productDao;

    public ShopService() {
        this.productDao = new ProductDao(DbConnection.getConnection());
    }
    public List<Product> getProducts() {return this.productDao.selectProduct(); }
    public void addProduct(Product product) {this.productDao.insertProduct(product);}
    public Product getProduct(int id) {
        return this.productDao.selectBoardByIdx(id);
    }

    public List<Product> getProductBags(int idx) {
        return this.productDao.selectProductById(idx);
    }
    public void addProductBag(Bag bag) {
        this.productDao.insertBag(bag);
    }
    public boolean selectBagByBag(Bag bag) {
        return this.productDao.selectBagByBag(bag);
    }
    public void deleteProductById(int idx) {this.productDao.deleteProductById(idx);}
    public void deleteBagById(int p_idx,int u_idx) {this.productDao.deleteBag(p_idx,u_idx);}

}
