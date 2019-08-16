package com.example.acer.printer;

import java.util.List;

public class Paymentresponse {

    private List<Paymentdetailclass> paymentdetail;

    public Paymentresponse(List<Paymentdetailclass> paymentdetail) {
        this.paymentdetail = paymentdetail;
    }

    public List<Paymentdetailclass> getPaymentdetail() {
        return paymentdetail;
    }
}
