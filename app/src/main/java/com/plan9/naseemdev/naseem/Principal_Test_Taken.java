package com.plan9.naseemdev.naseem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Model.Session;

/**
 * Created by Muhammad Taimoor on 8/3/2017.
 */

public class Principal_Test_Taken extends Fragment {
    private Session session;
    public Principal_Test_Taken(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_principal_test_taken, container, false);
        session = new Session(getActivity().getApplicationContext());
        return rootView;
    }
}
