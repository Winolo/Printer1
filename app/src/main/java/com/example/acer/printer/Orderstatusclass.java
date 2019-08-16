package com.example.acer.printer;

public class Orderstatusclass {
    private String order_id;

    private int pro_id;

    private int qty,price_normal;

    private String pro_pix;

    public Orderstatusclass(String order_id,int pro_id,String pro_pix,int qty,int price_normal){
        this.order_id=order_id;
        this.pro_id=pro_id;
        this.pro_pix=pro_pix;
        this.qty=qty;
        this.price_normal=price_normal;


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
