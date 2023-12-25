package com.saidur.eshop.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.eshop.R;
import com.saidur.eshop.databinding.FragmentCartBinding;
import com.saidur.eshop.databinding.FragmentHomeBinding;
import com.saidur.eshop.db.MainDB;
import com.saidur.eshop.model.ModelCart;

import java.util.List;


public class FragmentCart extends Fragment {

    FragmentCartBinding binding;
    CartAdapter cartAdapter;
    List<ModelCart> cartList;

    public FragmentCart() {
        // Required empty public constructor
    }


    public static FragmentCart newInstance(String param1, String param2) {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MainDB dbmain;

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
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        dbmain = new MainDB(requireActivity());
        binding.iEmpty.llEmpty.setVisibility(View.GONE);


        setClickEvent();
        //setToolbarTheme();
        // setThemeColor();
        // setScreenLayoutDirection();
        getIntentData();
        setCartAdapter();
        //settvTitle(getString(R.string.cart));
        //showBackButton();
        // hideSearchNotification();
        getCartData();
        setBottomBar("cart", binding.svHome);
        customerId = getPreferences().getString(RequestParamUtils.ID, "");

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    public void getCartData() {
        Log.e("TAG", "getCartData: " + "Gotted Cart Data");
        cartList = dbmain.getFromCart(0);
        if (cartList.size() > 0) {
            for (int i = 0; i < cartList.size(); i++) {
                String product = cartList.get(i).getProduct();
                try {
                    CategoryList categoryListRider = new Gson().fromJson(product, new TypeToken<CategoryList>() {
                    }.getType());
                    cartList.get(i).setCategoryList(categoryListRider);
                } catch (Exception e) {
                    Log.e("Gson Exception", "in Recent Product Get" + e.getMessage());
                }
            }
            cartAdapter.addAll(cartList);
            setTotalCount();
        } else {
            Log.e(TAG, "getCartData: Empty: " + "Called");
            isEmptyLayout(true);
        }
    }
}