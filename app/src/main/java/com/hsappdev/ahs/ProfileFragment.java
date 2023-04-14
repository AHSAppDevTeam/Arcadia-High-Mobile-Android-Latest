package com.hsappdev.ahs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private View aboutUsButton;
    private View termsButton;

    private View versionButton;

    private ImageView notifButton;

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


        notifButton = view.findViewById(R.id.notifcation_button);
        notifButton.setOnClickListener(notifV -> {
            Intent intent = new Intent(notifV.getContext(), NotifActivity.class);
            if(getActivity() != null) getActivity().startActivity(intent);
        });

        versionButton = view.findViewById(R.id.profile_settings_appversion_label);
        versionButton.setOnClickListener(new View.OnClickListener() {
            Toast lastToast = null;
            int timesClicked = 0;
            long lastClickTime = 0;
            int timesToHit = 20;
            @Override
            public void onClick(View view) {
                long timeNow = System.currentTimeMillis();
                if(timeNow - lastClickTime  > 1000) {
                    // timed out
                    // reset clicks
                    timesClicked = 1;
                } else {
                    timesClicked++;
                }
                lastClickTime = System.currentTimeMillis();
                if(timesClicked > 5 && timesClicked < timesToHit) {
                    if(lastToast != null) {
                        lastToast.cancel();
                    }
                    lastToast = Toast.makeText(view.getContext(), "Hit it " + (timesToHit-timesClicked) + " more times!", Toast.LENGTH_SHORT);
                    lastToast.show();
                } else if (timesClicked >= timesToHit) {
                    if(lastToast != null) {
                        lastToast.cancel();
                    }
                    lastToast = Toast.makeText(view.getContext(), "Hi there! ", Toast.LENGTH_LONG);
                    lastToast.show();
                }
            }
        });

        return view;
    }
}