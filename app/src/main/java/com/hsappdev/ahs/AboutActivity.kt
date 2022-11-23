package com.hsappdev.ahs

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hsappdev.ahs.db.DatabaseConstants
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

        // hideSystemBars()

        val animationDrawable = findViewById<View>(R.id.about_us_gradient).background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()


        var programmers : TextView = findViewById(R.id.programmers_list)
        var graphicDesigners : TextView = findViewById(R.id.graphic_designers_list)
        var contentEditors : TextView = findViewById(R.id.content_editors_list)
        var previousMembers : TextView = findViewById(R.id.old_member_list)
        var contacts : TextView = findViewById(R.id.contacts_list)

        ScreenUtil.setHTMLStringToTextView(resources.getString(R.string.contacts_list), contacts)

        val ref = FirebaseDatabase.getInstance(FirebaseApp.getInstance(DatabaseConstants.FIREBASE_REALTIME_DB))
                .reference
                .child(getString(R.string.db_credits))
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var programmers_list: String? = ""
                var graphic_designers_list: String? = ""
                var content_editors_list: String? = ""
                var previous_members_list: String? = ""
                for (person in snapshot.children) {
                    val name = person.child("name").getValue(String::class.java)
                    val isRetired = person.child("retired").getValue(Boolean::class.java)!!!!
                    val role = person.child("role").getValue(String::class.java)
                    val hasUrl = person.child("url").exists()
                    var url: String? = ""
                    var list: String? = ""
                    if (hasUrl) {
                        url = person.child("url").getValue(String::class.java)
                    }
                    list += "<br/>"
                    if (hasUrl) {
                        list += "<a href=\""
                        list += url
                        list += "\">"
                    }
                    list += name
                    if (hasUrl) {
                        list += "</a>"
                    }
                    if (isRetired) {
                        previous_members_list += list
                    } else if (role!!.contains("programmer")) {
                        programmers_list += list
                    } else if (role == "designer") {
                        graphic_designers_list += list
                    } else if (role == "editor") {
                        content_editors_list += list
                    }
                }
                ScreenUtil.setHTMLStringToTextView(programmers_list.toString().substring(5), programmers)
                ScreenUtil.setHTMLStringToTextView(graphic_designers_list.toString().substring(5), graphicDesigners)
                ScreenUtil.setHTMLStringToTextView(content_editors_list.toString().substring(5), contentEditors)
                ScreenUtil.setHTMLStringToTextView(previous_members_list.toString().substring(5), previousMembers)
            }

            override fun onCancelled(error: DatabaseError) {}
        })


    }

    private fun hideSystemBars() {
        val windowsInsetsController : WindowInsetsControllerCompat = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowsInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowsInsetsController.hide(WindowInsetsCompat.Type.systemBars())

    }


}