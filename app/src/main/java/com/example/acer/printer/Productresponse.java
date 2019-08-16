package com.example.acer.printer;

import java.util.ArrayList;
import java.util.List;

public class Productresponse {

    private List<Product> productList;

    public Productresponse(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
