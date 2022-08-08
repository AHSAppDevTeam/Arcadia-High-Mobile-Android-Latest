package com.hsappdev.ahs.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.hsappdev.ahs.R

open class BackNavigationActivity : AppCompatActivity() {
    private lateinit var backNavigateButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        backNavigateButton = findViewById(R.id.backNavigateButton);
        setUpBackNavigation()
    }

    private fun setUpBackNavigation(){
        backNavigateButton.setOnClickListener {
//            a -> (reference as I learn kotlin)
            finish()
        }
    }

}