package com.saidur.eshop.interfac;

import com.saidur.eshop.model.ModelBanner;
import com.saidur.eshop.model.ModelCategory;
import com.saidur.eshop.model.ModelProduct;

import java.util.List;

public interface IHome {
    interface Presenter{
     void getBanner();
     void getCategory();
     void getProductList();
    }
    interface view{
      void onViewBanner( List<ModelBanner> result);
      void onViewCategory( List<ModelCategory> result);
      void onViewProductList( List<ModelProduct> result);
    }
}
