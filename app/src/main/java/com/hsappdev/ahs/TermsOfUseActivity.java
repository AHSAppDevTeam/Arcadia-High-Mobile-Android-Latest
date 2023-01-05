package com.hsappdev.ahs;


import android.os.Bundle;
import android.widget.TextView;

import com.hsappdev.ahs.ui.reusable.BackNavigationActivity;
import com.hsappdev.ahs.util.ScreenUtil;

public class TermsOfUseActivity extends BackNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        TextView terms = findViewById(R.id.terms_text);
        ScreenUtil.setHTMLStringToTextView(getResources().getString(R.string.terms), terms);
    }
}