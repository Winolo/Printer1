package com.example.acer.printer;

import java.util.ArrayList;
import java.util.List;

public class Productlistresponse {

    private List<Productlistclass> productList;

    public Productlistresponse(List<Productlistclass> productList) {
        this.productList = productList;
    }

    public List<Productlistclass> getProductList() {
        return productList;
    }
}
