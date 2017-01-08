package com.example.guanzhuli.icart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.guanzhuli.icart.R;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class HomeNewArrivalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_home, container, false);
        return view;
    }
}
