package com.alpaca.android.grocer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class OrderPageActivity : AppCompatActivity() {
    private lateinit var deliveryTip:EditText
    private lateinit var deliveryTipLabel:TextView
    private lateinit var maxPrice:EditText
    private lateinit var maxPriceLabel:TextView
    private lateinit var priceDollarSign:TextView
    private lateinit var itemName:EditText
    private lateinit var itemNameLabel:TextView
    private lateinit var orderLabel:TextView
    private lateinit var priceLabel:TextView
    private lateinit var tipLabel:TextView
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_page)
        deliveryTip = findViewById(R.id.tip_amount)
        deliveryTipLabel = findViewById(R.id.tip_amount_label)
        maxPrice = findViewById(R.id.max_price)
        maxPriceLabel = findViewById(R.id.max_price_label)
        priceDollarSign= findViewById(R.id.maxprice_dollar_sign)
        itemName = findViewById(R.id.item_name)
        itemNameLabel = findViewById(R.id.item_name_label)
        orderLabel = findViewById(R.id.order_appear)
        priceLabel = findViewById(R.id.price_appear)
        tipLabel = findViewById(R.id.tip_appear)
        makeInvisible()

        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        orderList(currentUser)

        val orderButton:Button= findViewById(R.id.new_order)
        orderButton.setOnClickListener {
            makeOrder(orderButton,currentUser)
        }

        val backButton:Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            goBack()
        }
    }

    private fun orderList(currentUser:String) {
        database.child(currentUser).child("orders")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var itemDisplay:String = ""
                    var priceDisplay:String = ""
                    var tipDisplay:String = ""
                    for (snapshot in dataSnapshot.children) {
                        itemDisplay += snapshot.child("itemName").value.toString() + '\n'
                        priceDisplay += snapshot.child("maxPrice").value.toString() + '\n'
                        tipDisplay += snapshot.child("delTip").value.toString() + '\n'
                    }
                    orderLabel.text = itemDisplay
                    priceLabel.text = priceDisplay
                    tipLabel.text = tipDisplay
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
    private fun goBack() {
        val intent = Intent(this,HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun makeOrder(orderButton:Button,userId:String) {
        if (orderButton.text.toString().toLowerCase(Locale.ROOT) == "new order") {
            orderButton.text = "Confirm Order"
            makeVisible()
        }
        else {
            val orderId = getRandomString(16)
            val path = database.child(userId).child("orders").child(orderId)
            path.child("itemName").setValue(itemName.text.toString())
            path.child("maxPrice").setValue(maxPrice.text.toString())
            path.child("delTip").setValue(deliveryTip.text.toString())
            itemName.setText("")
            maxPrice.setText("")
            deliveryTip.setText("")
            orderButton.text = "New Order"
            makeInvisible()
            Toast.makeText(this,"Order has been made!",Toast.LENGTH_SHORT).show()
            orderList(userId)
        }
    }

    private fun getRandomString(length: Int) : String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    private fun makeVisible() {
        deliveryTip.visibility = View.VISIBLE
        deliveryTipLabel.visibility = View.VISIBLE
        maxPrice.visibility = View.VISIBLE
        maxPriceLabel.visibility = View.VISIBLE
        priceDollarSign.visibility=View.VISIBLE
        itemName.visibility=View.VISIBLE
        itemNameLabel.visibility=View.VISIBLE
    }
    private fun makeInvisible() {
        deliveryTip.visibility = View.GONE
        deliveryTipLabel.visibility = View.GONE
        maxPrice.visibility = View.GONE
        maxPriceLabel.visibility = View.GONE
        priceDollarSign.visibility=View.GONE
        itemName.visibility=View.GONE
        itemNameLabel.visibility=View.GONE
    }
}
