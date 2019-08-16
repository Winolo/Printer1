package com.example.acer.printer;

import java.util.List;

public class Orderstatusresponse {

    private List<Orderstatusclass> Orderstatusclass;

    public Orderstatusresponse(List<Orderstatusclass> Orderstatusclass) {
        this.Orderstatusclass = Orderstatusclass;
    }

    public List<Orderstatusclass> getOrderstatusclassList() {
        return Orderstatusclass;
    }
}
