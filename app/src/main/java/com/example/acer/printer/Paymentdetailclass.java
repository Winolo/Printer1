package com.example.acer.printer;

public class Paymentdetailclass {
    private String payment_id;
    private String picture;
    private String cusname;
    private String total;
    private String date;

    public Paymentdetailclass(String payment_id, String picture, String cusname, String total, String date) {
        this.payment_id = payment_id;
        this.picture = picture;
        this.cusname = cusname;
        this.total = total;
        this.date = date;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getPicture() {
        return picture;
    }

    public String getCusname() {
        return cusname;
    }

    public String getTotal() {
        return total;
    }

    public String getDate() {
        return date;
    }
}
