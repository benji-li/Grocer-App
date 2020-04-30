package com.alpaca.android.grocer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class HomepageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val logOutButton: Button = findViewById(R.id.logout_button)
        logOutButton.setOnClickListener {
            logOut()
        }

        val profileButton: ImageButton = findViewById(R.id.profileButton)
        profileButton.setOnClickListener {
            editProfile()
        }
    }

    private fun logOut() {
        auth.signOut()
        Toast.makeText(this,"Successfully signed out!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun editProfile() {
        val intent = Intent(this,EditProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}
