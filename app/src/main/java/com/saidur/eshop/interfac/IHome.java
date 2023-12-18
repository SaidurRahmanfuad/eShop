package com.saidur.eshop.interfac;

import com.saidur.eshop.model.ModelBanner;

import java.util.List;

public interface IHome {
    interface Presenter{
     void getBanner();
     void getCategory();
    }
    interface view{
      void onViewBanner( List<ModelBanner> result);
      void onViewCategory( List<ModelBanner> result);
    }
}
