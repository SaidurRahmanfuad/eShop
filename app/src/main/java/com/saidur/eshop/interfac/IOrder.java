package com.saidur.eshop.interfac;
import com.saidur.eshop.request.ORequest;


public interface IOrder {
    interface Presenter{
     void orderSubmit(ORequest req);
    }
    interface view{
      void onPlaceOrderStatus(String msd);
    }
}
