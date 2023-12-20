package com.saidur.eshop.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saidur.eshop.R;
import com.saidur.eshop.adapter.BannerViewPagerAdapter;
import com.saidur.eshop.adapter.CategoryAdapter;
import com.saidur.eshop.adapter.ProductAdapter;
import com.saidur.eshop.customview.GridSpacingItemDecoration;
import com.saidur.eshop.databinding.FragmentHomeBinding;
import com.saidur.eshop.interfac.IHome;
import com.saidur.eshop.model.ModelBanner;
import com.saidur.eshop.model.ModelCategory;
import com.saidur.eshop.model.ModelProduct;
import com.saidur.eshop.presentar.PresenterHome;
import com.saidur.eshop.utils.Consts;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements IHome.view {
    FragmentHomeBinding binding;
    PresenterHome presenter;
    SharedPreferences sharedpreferences;
    private ImageView[] dots;
    private int currentPosition;
    private boolean isAutoScroll = false;
    private BannerViewPagerAdapter bannerViewPagerAdapter;
    private CategoryAdapter catAdapter;
    private ProductAdapter productadapter;
    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(String param1, String param2) {
        /*
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return new FragmentHome();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        presenter=new PresenterHome(this, requireActivity());
        setView();
        setCatView();

        presenter.getBanner();
        presenter.getCategory();
        presenter.getProductList();

        return binding.getRoot();
    }

    private void setProductView(List<ModelProduct> result) {
        binding.mainDiv.llmenusTwo.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dynamic_mostpopuler, null);
        RecyclerView rvProducts = view.findViewById(R.id.rvProducts);

        productadapter = new ProductAdapter(requireActivity(),result);
        rvProducts.setHasFixedSize(true);
        rvProducts.setNestedScrollingEnabled(false);
        GridLayoutManager mLayoutManager = new GridLayoutManager(requireActivity(), 2);
        rvProducts.setLayoutManager(mLayoutManager);
        rvProducts.setAdapter(productadapter);
        rvProducts.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        binding.mainDiv.llmenusTwo.addView(view);


    }

    @Override
    public void onViewBanner(List<ModelBanner> result) {
        if (result != null) {
            if (result.size() > 0) {
                bannerViewPagerAdapter.addAll(result);
                if (!isAutoScroll) {
                    addBottomDots(0, bannerViewPagerAdapter.getCount());
                    autoScroll();
                    isAutoScroll = true;
                }
                binding.mainDiv.llBanner.setVisibility(View.VISIBLE);
            } else {
                binding.mainDiv.llBanner.setVisibility(View.GONE);
            }
        } else {
            binding.mainDiv.llBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCategory(List<ModelCategory> result) {
        binding.mainDiv.rvTopCategory.setVisibility(View.VISIBLE);
        if (result != null) {
            List<ModelCategory> mainCategoryList = new ArrayList<>();
            if (result.size() > 0) {
                if (result.size() > 3) {
                    for (int i = 0; i <= 3; i++) {
                        mainCategoryList.add(result.get(i));
                    }
                } else {
                    mainCategoryList.addAll(result);
                }
                ModelCategory mainCategory = new ModelCategory();
                mainCategory.setCategoryName("More");
                mainCategoryList.add(mainCategory);
                catAdapter.addAll(mainCategoryList);
                binding.mainDiv.llTopCategory.setVisibility(View.VISIBLE);

            } else {
                binding.mainDiv.llTopCategory.setVisibility(View.GONE);
            }
        } else {
            binding.mainDiv.llTopCategory.setVisibility(View.GONE);
        }
    }

    public void setCatView(){
        catAdapter = new CategoryAdapter(requireActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.mainDiv.rvTopCategory.setLayoutManager(mLayoutManager);
        binding.mainDiv.rvTopCategory.setAdapter(catAdapter);
        binding.mainDiv.rvTopCategory.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(binding.mainDiv.rvTopCategory, false);
        binding.mainDiv.rvTopCategory.setHasFixedSize(true);
        binding.mainDiv.rvTopCategory.setItemViewCacheSize(20);

        binding.mainDiv.rvTopCategory.setDrawingCacheEnabled(true);
        binding.mainDiv.rvTopCategory.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
    }
    @Override
    public void onViewProductList(List<ModelProduct> result) {
        setProductView(result);
    }

    public SharedPreferences getPreferences() {
        sharedpreferences = requireActivity().getSharedPreferences(Consts.MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences;
    }
    private void setView() {
        bannerViewPagerAdapter = new BannerViewPagerAdapter(requireActivity());
        binding.mainDiv.vpBanner.setAdapter(bannerViewPagerAdapter);
        binding.mainDiv.vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position, bannerViewPagerAdapter.getCount());
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    public void autoScroll() {
        new Handler().postDelayed(() -> new Handler().postDelayed(() -> {
            if (currentPosition == bannerViewPagerAdapter.getCount() - 1) {
                currentPosition = 0;
            } else {
                currentPosition = currentPosition + 1;
            }
            binding.mainDiv.vpBanner.setCurrentItem(currentPosition);
            addBottomDots(currentPosition, bannerViewPagerAdapter.getCount());
            autoScroll();
        }, 6000), 1000);
    }
    private void addBottomDots(int currentPage, int length) {
        binding.mainDiv.layoutDots.removeAllViews();
        dots = new ImageView[length];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(requireActivity());
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_space, null));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 15, 0);
            dots[i].setLayoutParams(lp);
            binding.mainDiv.layoutDots.addView(dots[i]);
        }
        if (dots.length > 0 && dots.length >= currentPage) {
            dots[currentPage].setColorFilter(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
        }
    }

}