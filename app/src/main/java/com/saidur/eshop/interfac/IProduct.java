package com.saidur.eshop.interfac;
import com.saidur.eshop.model.ModelProductDtls;


public interface IProduct {
    interface Presenter{
     void getProductDetails(int id);
    }
    interface view{
      void onProductDtls(ModelProductDtls result);
    }
}
