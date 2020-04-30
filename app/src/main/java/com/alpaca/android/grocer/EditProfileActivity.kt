package com.alpaca.android.grocer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditProfileActivity : AppCompatActivity() {
    private val database:DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        val saveButton: Button = findViewById(R.id.save_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)
        val firstNameField: EditText = findViewById(R.id.firstname_field)
        val lastNameField: EditText = findViewById(R.id.lastname_field)

        cancelButton.setOnClickListener {
            backToHome()
        }
        saveButton.setOnClickListener {
            writeName(currentUser,firstNameField.text.toString(),lastNameField.text.toString())
        }

        val myRef = database.child(currentUser).child("userInfo")
        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val fName = dataSnapshot.child("firstName").value.toString()
                if (fName != "null") firstNameField.setText(fName)
                val lName = dataSnapshot.child("lastName").value.toString()
                if (lName !="null") lastNameField.setText(lName)
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }
    private fun writeName(userId:String, firstName:String,lastName:String) {
        val user = database.child(userId).child("userInfo")
        val fName = user.child("firstName")
        val lName = user.child("lastName")
        fName.setValue(firstName)
        lName.setValue(lastName)
        Toast.makeText(this,"Name updated!",Toast.LENGTH_SHORT).show()
        backToHome()
    }
    private fun backToHome() {
        val intent = Intent(this,HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }


}
