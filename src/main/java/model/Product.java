package model;

public class Product {
    private int idx;
    private String name;
    private int original_price;
    private int price;
    private String description;
    private int quantity;
    private String path;



    public Product(String idx,String name,String original_price,String price,String description,String quantity,String path) {
        this.idx = Integer.parseInt(idx);
        this.name = name;
        this.original_price = Integer.parseInt(original_price);
        this.price = Integer.parseInt(price);
        this.description = description;
        this.quantity = Integer.parseInt(quantity);
        this.path = path;
    }
    public Product(String name,String original_price,String price,String description,String quantity,String path) {
        this.name = name;
        this.original_price = Integer.parseInt(original_price);
        this.price = Integer.parseInt(price);
        this.description = description;
        this.quantity = Integer.parseInt(quantity);
        this.path = path;
    }

    public int getIdx() {
        return idx;
    }
    public String getName() {
        return name;
    }
    public int getOriginal_price() {
        return original_price;
    }
    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getDescription() {
        return description;
    }
    public String getPath() {
        return path;
    }
}
