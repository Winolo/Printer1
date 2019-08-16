package com.example.acer.printer;

public class Product {
    private int pro_id;

    public static final String URL="http://192.168.43.222/ecomfish/Timageproduct/";

   private String pro_name,pro_pix,Title,Detail,price_normal,stock;

    public Product(int pro_id,String pro_name,String pro_pix,String Title,String Detail,String price_normal,String stock){
        this.pro_id=pro_id;
        this.pro_name=pro_name;
        this.pro_pix=pro_pix;
        this.Title=Title;
        this.Detail=Detail;
        this.price_normal=price_normal;
        this.stock=stock;
    }


    public int getPro_Id() {
        return pro_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public String getPro_pic() {
        return pro_pix;
    }

    public String getTitle() {
        return Title;
    }

    public String getDetail() {
        return Detail;
    }

    public String getPrice_normal() {
        return price_normal;
    }

    public String getStock() {
        return stock;
    }
}
