package com.example.acer.printer;

public class Productlistclass {

    private String order_id;

    private int pro_id;

    private int qty,price_normal;

    private String pro_pix;

    public Productlistclass(int pro_id,int qty,int price_normal,String pro_pix){
        this.pro_id=pro_id;
        this.qty=qty;
        this.price_normal=price_normal;
        this.pro_pix=pro_pix;

    }

    public String getOrder_id() {
        return order_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public int getQty() {
        return qty;
    }

    public int getPrice_normal() {
        return price_normal;
    }

    public String getPro_pix() {
        return pro_pix;
    }
}
