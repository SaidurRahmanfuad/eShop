package com.saidur.eshop.request;

import java.util.List;

public class ORequest {
    Omaster orderMaster;
    List<Obody> CreateEcomOrderDetails;

    public ORequest(Omaster orderMaster, List<Obody> createEcomOrderDetails) {
        this.orderMaster = orderMaster;
        CreateEcomOrderDetails = createEcomOrderDetails;
    }

    public Omaster getOrderMaster() {
        return orderMaster;
    }

    public void setOrderMaster(Omaster orderMaster) {
        this.orderMaster = orderMaster;
    }

    public List<Obody> getCreateEcomOrderDetails() {
        return CreateEcomOrderDetails;
    }

    public void setCreateEcomOrderDetails(List<Obody> createEcomOrderDetails) {
        CreateEcomOrderDetails = createEcomOrderDetails;
    }
}
