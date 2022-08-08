package com.hsappdev.ahs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.hsappdev.ahs.ui.BackNavigationActivity
import com.hsappdev.ahs.util.ScreenUtil

class AboutActivity : BackNavigationActivity() {

    var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initView()
    }

    private fun initView() {

        hideSystemBars()

        var programmers : TextView = findViewById(R.id.programmers_list)
        var graphicDesigners : TextView = findViewById(R.id.graphic_designers_list)
        var contentEditors : TextView = findViewById(R.id.content_editors_list)
        var previousMembers : TextView = findViewById(R.id.old_member_list)
        var contacts : TextView = findViewById(R.id.contacts_list)

        ScreenUtil.setHTMLStringToTextView(resources.getString(R.string.contacts_list), contacts)





    }

    private fun hideSystemBars() {
        val windowsInsetsController : WindowInsetsControllerCompat = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowsInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowsInsetsController.hide(WindowInsetsCompat.Type.systemBars())

    }
}