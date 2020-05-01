package com.alpaca.android.grocer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DeliverPageActivity : AppCompatActivity() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var orderSummary: TextView
    private lateinit var deliverConfirm: Button
    private var ordersList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliver_page)
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            goBack()
        }
        val switchToggle:Switch= findViewById(R.id.view_switch)
        switchToggle.setOnClickListener {
            switchView()
        }
        orderSummary = findViewById(R.id.order_box)
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        viewOrders(currentUser)

        deliverConfirm = findViewById(R.id.delivery_accept)
        deliverConfirm.visibility = View.GONE
        deliverConfirm.setOnClickListener {
            conf()
        }
        orderSummary.setOnClickListener {
            deliverConfirm.visibility = View.VISIBLE
            val tt = "Deliver to "+orderSummary.text.toString().substringBefore(' ')+"?"
            deliverConfirm.text = tt
        }

        var numDel = 0
        val goBack:ImageButton = findViewById(R.id.last_delivery)
        val goForward:ImageButton = findViewById(R.id.next_delivery)
        goBack.setOnClickListener {
            deliverConfirm.visibility=View.GONE
            if (numDel==0) numDel=ordersList.size-1
            else numDel--
            nextDel(numDel)
        }
        goForward.setOnClickListener {
            deliverConfirm.visibility=View.GONE
            if (ordersList.size-1==numDel) numDel =0
            else numDel++
            nextDel(numDel)
        }

    }
    private fun conf() {
        Toast.makeText(this,"Delivery Processed, Recipient has been notified!",Toast.LENGTH_LONG).show()
    }
    private fun nextDel(ind:Int) {
        orderSummary.text = ordersList[ind]
    }
    private fun viewOrders(currentUser:String) {
        database.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var message:String
                for (users in dataSnapshot.children) {
                    if (users.key.toString() != currentUser) {
                        val dataSnap1:DataSnapshot = users.child("orders")
                        for (orders in dataSnap1.children) {
                            message = users.child("userInfo").child("firstName").value.toString() +
                                    " at " + users.child("userInfo").child("address").value.toString() + " needs " +
                                    orders.child("maxPrice").value.toString() + " "+ orders.child("itemName").value.toString() +"!"
                            ordersList.add(message)
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun switchView() {
        Toast.makeText(this,"View Switched",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goBack() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
