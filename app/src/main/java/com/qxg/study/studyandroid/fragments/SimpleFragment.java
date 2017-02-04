package com.qxg.study.studyandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxg.study.studyandroid.R;

/**
 * Created by qxg on 17-2-4.
 */

public class SimpleFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple,container,false);
        return view;
    }
}
