/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KafeIn;

import java.sql.Date;

/**
 *
 * @author 0xwighozali
 */
public class ProductData {

    private String productId;
    private String productName;
    private String type;
    private Integer stock;
    private Double price;
    private String image;
    private Date date;
    private Integer quantity;
    private Integer total;
    private String customerName;
    protected Integer txId;

    public ProductData(String productId, String productName, String type, Integer stock, Double price,  String image, Date date) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.date = date;
    }

    public ProductData(String productId, String productName, Double price, String image) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.image = image;
    }

    public ProductData(String productId, String productName, Integer quantity, Double price, Date date) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    public ProductData(Integer txId, String customerName, Integer total, Date date) {
        this.txId = txId;
        this.customerName = customerName;
        this.total = total;
        this.date = date;
    }
    
        public ProductData(String productId, String productName, Integer quantity, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
    public Integer getTxId() {
        return txId;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public Integer getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }
}
