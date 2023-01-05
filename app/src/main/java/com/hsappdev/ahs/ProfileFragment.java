package com.hsappdev.ahs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private View aboutUsButton;
    private View termsButton;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        aboutUsButton = view.findViewById(R.id.profile_settings_aboutus_label);
        aboutUsButton.setOnClickListener(aboutUsV -> {
            Intent intent = new Intent(aboutUsV.getContext(), AboutActivity.class);
            if(getActivity() != null) getActivity().startActivity(intent);
        });

        termsButton = view.findViewById(R.id.profile_settings_terms_label);
        termsButton.setOnClickListener(termsV -> {
            Intent intent = new Intent(termsV.getContext(), TermsOfUseActivity.class);
            if(getActivity() != null) getActivity().startActivity(intent);
        });

        return view;
    }
}