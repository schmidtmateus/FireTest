package com.example.mschmidt.firetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var firebaseAuth: FirebaseAuth? = null
    lateinit var ivProfilePicture: ImageView
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView
    lateinit var tvUserId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        ivProfilePicture = findViewById<View>(R.id.iv_profile) as ImageView
        tvName = findViewById<View>(R.id.tv_name) as TextView
        tvEmail = findViewById<View>(R.id.tv_email) as TextView
        tvUserId = findViewById<View>(R.id.tv_id)as TextView
        val user = firebaseAuth?.currentUser
        Log.i(TAG, "User account ID ${user?.uid}")
        Log.i(TAG, "Display Name : ${user?.displayName}")
        Log.i(TAG, "Email : ${user?.email}")
        Log.i(TAG, "Photo URL : ${user?.photoUrl}")
        Log.i(TAG, "Provider ID : ${user?.providerId}")
        tvName.text = user?.displayName
        tvEmail.text = user?.email
        tvUserId.text = user?.uid
        Picasso.with(this@MainActivity)
                .load(user?.photoUrl)
                .into(ivProfilePicture)

    }

}
