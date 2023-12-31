package com.saidur.eshop.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.eshop.R;
import com.saidur.eshop.adapter.CartAdapter;
import com.saidur.eshop.customlistener.OnItemClickListener;
import com.saidur.eshop.databinding.FragmentCartBinding;
import com.saidur.eshop.databinding.FragmentHomeBinding;
import com.saidur.eshop.db.MainDB;
import com.saidur.eshop.interfac.IOrder;
import com.saidur.eshop.model.ModelCart;
import com.saidur.eshop.model.ModelProduct;
import com.saidur.eshop.presentar.PresenterOrder;
import com.saidur.eshop.request.ORequest;
import com.saidur.eshop.request.Obody;
import com.saidur.eshop.request.Omaster;
import com.saidur.eshop.utils.Consts;
import com.saidur.eshop.utils.RequestParamUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentCart extends Fragment implements OnItemClickListener, IOrder.view {

    FragmentCartBinding binding;
    CartAdapter cartAdapter;
    List<ModelCart> cartList;
    MainDB dbHelper;
    PresenterOrder presenterOrder;
    ProgressDialog pd;
    public FragmentCart() {
        // Required empty public constructor
    }


    public static FragmentCart newInstance(String param1, String param2) {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false);
        presenterOrder=new PresenterOrder(this,requireActivity());
        pd=new ProgressDialog(requireActivity());
        dbHelper = new MainDB(requireActivity());
        binding.iEmpty.llEmpty.setVisibility(View.GONE);


        setClickEvent();
        //setToolbarTheme();
        // setThemeColor();
        // setScreenLayoutDirection();
        //getIntentData();
        setCartAdapter();
        //settvTitle(getString(R.string.cart));
        //showBackButton();
        // hideSearchNotification();
        getCartData();
        //setBottomBar("cart", binding.svHome);
        //customerId = getPreferences().getString(RequestParamUtils.ID, "");

        return binding.getRoot();
    }
    public void setCartAdapter() {
        Log.e("TAG", "CartAdapter: " + "Called");

        cartAdapter = new CartAdapter(requireActivity(), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvCart.setLayoutManager(mLayoutManager);
        binding.rvCart.setAdapter(cartAdapter);
        cartAdapter.isFromBuyNow(0);
        binding.rvCart.setNestedScrollingEnabled(false);
      /*  ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvCart);*/
    }
    @SuppressLint("SetTextI18n")
    public void getCartData() {
        Log.e("TAG", "getCartData: " + "get Cart Data");
        cartList = dbHelper.getFromCart(0);
        if (cartList.size() > 0) {
            for (int i = 0; i < cartList.size(); i++) {

             /*   try {
                    CategoryList categoryListRider = new Gson().fromJson(product, new TypeToken<CategoryList>() {
                    }.getType());
                    cartList.get(i).setCategoryList(categoryListRider);
                } catch (Exception e) {
                    Log.e("Gson Exception", "in Recent Product Get" + e.getMessage());
                }*/
            }
            cartAdapter.addAll(cartList);
            setTotalCount();
        } else {
           // Log.e(TAG, "getCartData: Empty: " + "Called");
            isEmptyLayout(true);
        }
    }
    @SuppressLint("SetTextI18n")
    public void setTotalCount() {
        Log.e("TAG", "setTotalCount: " + "Setted Total Count");
        binding.tvTotalItem.setText(cartAdapter.getList().size() + " " + "Items");
        binding.tvNoOfItems.setText(cartAdapter.getList().size() + " " + "Items");

        for (int i = 0; i < cartAdapter.getList().size(); i++) {
            /*if (cartAdapter.getList().get(i).getCategoryList().priceHtml != null) {
                String price = Html.fromHtml(cartAdapter.getList().get(i).getCategoryList().priceHtml).toString();
                if (Consts.CURRENCYSYMBOL == null && !price.equals("")) {
                    Consts.CURRENCYSYMBOL = price.charAt(i) + "";
                    break;
                }
            }*/
        }

        float amount = 0;
        for (int i = 0; i < cartAdapter.getList().size(); i++) {
            try {
                ModelProduct product=new Gson().fromJson(cartAdapter.getList().get(i).getProduct(),ModelProduct.class);
                amount = amount + (Float.parseFloat(getPrice(String.valueOf(product.getSale_price()))) * cartAdapter.getList().get(i).getQuantity());
            } catch (Exception e) {
                Log.e("Exception = ", e.getMessage());
            }

        }

        switch (Consts.CURRENCYSYMBOLPOSTION) {
            case "left":
                binding.tvAmount.setText(Consts.CURRENCYSYMBOL + Consts.setDecimal((double) amount) + "");
                binding.tvTotalAmount.setText(Consts.CURRENCYSYMBOL + Consts.setDecimal((double) amount) + "");
                break;
            case "right":
                binding.tvAmount.setText(Consts.setDecimal((double) amount) + Consts.CURRENCYSYMBOL + "");
                binding.tvTotalAmount.setText(Consts.setDecimal((double) amount) + Consts.CURRENCYSYMBOL + "");
                break;
            case "left_space":
                binding.tvAmount.setText(Consts.CURRENCYSYMBOL + " " + Consts.setDecimal((double) amount) + "");
                binding.tvTotalAmount.setText(Consts.CURRENCYSYMBOL + " " + Consts.setDecimal((double) amount) + "");
                break;
            case "right_space":
                binding.tvAmount.setText(Consts.setDecimal((double) amount) + " " + Consts.CURRENCYSYMBOL + "");
                binding.tvTotalAmount.setText(Consts.setDecimal((double) amount) + " " + Consts.CURRENCYSYMBOL + "");
                break;
        }
    }
    public void isEmptyLayout(boolean isEmpty) {
        Log.e("TAG", "isEmptyLayout: " + "Called");
        if (isEmpty) {
            Log.e("TAG", "isEmptyLayout: isEmpty:  " + "Called");
            binding.iEmpty.llEmpty.setVisibility(View.VISIBLE);
            binding.llMain.setVisibility(View.GONE);

        } else {
            Log.e("TAG", "isEmptyLayout: IsCart:  " + "Called");
            binding.iEmpty.llEmpty.setVisibility(View.GONE);
            binding.llMain.setVisibility(View.VISIBLE);
        }
    }
    public String getPrice(String price) {
        price = price.replace("\\s+", "");
        if (!Consts.THOUSANDSSEPRETER.equals(".")) {
            price = price.replace(Consts.THOUSANDSSEPRETER, "");
        }
        return price;
    }

    @Override
    public void onItemClick(int position, String value, int outpos) {
        Log.e("TAG", "onItemClick: " + "Called");
        switch (value) {
            case RequestParamUtils.delete:
                if (cartAdapter.getList().size() == 0) {
                    isEmptyLayout(true);
                } else {
                    setTotalCount();
                }
             /*     TextView tvBottomCartCount = findViewById(R.id.tvBottomCartCount);
                if (tvBottomCartCount != null) {
                    if (new MainDB(requireActivity()).getFromCart(0).size() > 0) {
                        tvBottomCartCount.setText(String.valueOf(new MainDB(requireActivity()).getFromCart(0).size()));
                        tvBottomCartCount.setVisibility(View.VISIBLE);
                    } else {
                        tvBottomCartCount.setVisibility(View.GONE);
                    }
                }*/
                break;
            case RequestParamUtils.increment:
            case RequestParamUtils.decrement:
                setTotalCount();
                break;
            case RequestParamUtils.detail:
                dbHelper.deleteFromBuyNow(outpos + "");
                break;
        }
    }

    public void setClickEvent() {
        binding.tvContinue.setOnClickListener(v -> {
            binding.llPlaceOrder.setVisibility(View.VISIBLE);
            binding.tvContinue.setVisibility(View.GONE);
        });
        binding.llPlaceOrder.setOnClickListener(v -> {
            Omaster master=new Omaster(0,binding.userMobile.getText().toString().trim(),0,0,0,0);
            List<Obody> bodyList=new ArrayList<>();


            List<ModelCart> cartList = dbHelper.getFromCart(0);
            if (cartList.size() > 0) {
                try {
                    for (int i = 0; i < cartList.size(); i++) {
                        ModelProduct product=new Gson().fromJson(cartList.get(i).getProduct(),ModelProduct.class);
                        Obody obody=new Obody();
                        obody.setEcomPendingOrderMasterId(0);
                        obody.setId(0);
                        obody.setProductId(product.getId());
                        obody.setName(product.getName());
                        obody.setQty(cartList.get(i).getQuantity());
                        obody.setStock(0);
                        obody.setPrice(0);
                        obody.setSalePrice(Float.parseFloat(String.valueOf(product.getSale_price())));
                        obody.setUrl(product.getPictures().get(0).getUrl());
                        obody.setSku("SKU123");
                        obody.setProductGroupId(1);
                        obody.setProductTypeId(1);
                        obody.setProductCategoryId(1);
                        obody.setProductSubCategoryId(1);
                        obody.setProductSizeId(1);
                        obody.setProductColorId(1);
                        obody.setProductBrandId(1);

                        bodyList.add(obody);
                    }

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
            ORequest request=new ORequest(master,bodyList);
            pd.setTitle("Placing order");
            pd.setMessage("Please wait..");
            pd.show();
            presenterOrder.orderSubmit(request);


        });

    }

    @Override
    public void onPlaceOrderStatus(String msd) {
        if(pd!=null || pd.isShowing())
        {
            pd.dismiss();
        }
        Toast.makeText(requireActivity(), msd, Toast.LENGTH_SHORT).show();
    }
}