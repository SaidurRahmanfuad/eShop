package com.saidur.eshop.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saidur.eshop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWish#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWish extends Fragment {

    public FragmentWish() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWish.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWish newInstance(String param1, String param2) {
        FragmentWish fragment = new FragmentWish();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish, container, false);
    }
}