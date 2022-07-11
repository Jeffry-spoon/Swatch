package com.jeffcode.swatch.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jeffcode.swatch.R
import com.jeffcode.swatch.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddressBinding
    private lateinit var preferences : SharedPreferences

    private lateinit var totalCost : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!

        loadUserInfo()

        binding.btnCheckout.setOnClickListener {
            validateData(
                binding.etPhone.text.toString(),
                binding.etUsername.text.toString(),
                binding.etPincode.text.toString(),
                binding.etCity.text.toString(),
                binding.etState.text.toString(),
                binding.etVillage.text.toString()

            )
        }
    }

    private fun validateData(number: String, name: String, pinCode : String, city: String, state: String, village: String) {
        if (number.isEmpty() || state.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else {
            storeData(pinCode, city, state, village)
        }
    }

    private fun storeData(pinCode: String, city: String, state: String, village: String) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds",  intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)

            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }


    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.etUsername.setText(it.getString("userName"))
                binding.etPhone.setText(it.getString("userPhoneNumber"))
                binding.etVillage.setText(it.getString("village"))
                binding.etCity.setText(it.getString("city"))
                binding.etPincode.setText(it.getString("pinCode"))
                binding.etState.setText(it.getString("state"))
            }
    }
}