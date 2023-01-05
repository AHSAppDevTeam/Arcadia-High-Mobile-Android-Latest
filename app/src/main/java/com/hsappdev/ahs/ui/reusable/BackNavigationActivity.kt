package com.hsappdev.ahs.ui.reusable

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.hsappdev.ahs.R

/**
 * This class automatically handles the "back button" <br?
 * all activities that extend this class must set android:onClick="backNavigate" on the "back" button
 */
open class BackNavigationActivity : AppCompatActivity() {

    private val TAG = "BackNavigationActivity"
    
    private lateinit var backNavigateButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        backNavigateButton = findViewById(R.id.backNavigateButton)
        backNavigateButton.isClickable = true

//        setUpBackNavigation()
    }

    fun backNavigate(view: View) {
        finish();
    }

//    private fun setUpBackNavigation(){
//        backNavigateButton.setOnClickListener {
//            Log.d(TAG, "setUpBackNavigation: back navigate")
////            a -> (reference as I learn kotlin)
//            finish()
//        }
//    }

}