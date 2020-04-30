package com.alpaca.android.grocer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        //check if user is already logged in
        checkLogin()

        val registerButton:Button = findViewById(R.id.register_button)
        val loginButton:Button = findViewById(R.id.login_button)
        val email:EditText = findViewById(R.id.email_field)
        val password:EditText = findViewById(R.id.password_field)

        registerButton.setOnClickListener {
            register(email.text.toString(),password.text.toString())
        }
        loginButton.setOnClickListener {
            login(email.text.toString(),password.text.toString())
        }
    }

    private fun register(email:String,password:String) {
        if (email != "" && password != "") {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Successfully Registered an Account!",
                            Toast.LENGTH_SHORT
                        ).show()
                        toHomePage()
                    } else {
                        Toast.makeText(this, "Registration Failed, try again!", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }
    }

    private fun login(email:String,password:String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                Toast.makeText(this,"You have been logged in!", Toast.LENGTH_SHORT).show()
                toHomePage()
            } else {
                Toast.makeText(this,"Account not recognized!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLogin() {
        if(auth.currentUser != null){
            Toast.makeText(this, "Logged in Successfully!", Toast.LENGTH_SHORT).show()
            toHomePage()
        }
    }

    private fun toHomePage() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }

}

