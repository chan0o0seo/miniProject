package model;

public class Bag {
    int user_idx;
    int product_idx;

    public Bag(int user_idx, int product_idx) {
        this.user_idx = user_idx;
        this.product_idx = product_idx;
    }

    public int getProduct_idx() {
        return product_idx;
    }

    public int getUser_idx() {
        return user_idx;
    }
}
