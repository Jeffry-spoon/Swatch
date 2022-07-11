package com.jeffcode.swatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jeffcode.swatch.R
import com.jeffcode.swatch.databinding.ActivityInputPhoneBinding

class InputPhoneActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInputPhoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnGetOtp.setOnClickListener {
//            validateUser()
//        }
    }

//    private fun validateUser() {
//        if(binding.inputMobile.text!!.isEmpty()) {
//            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
//        }else {
//            storeData()
//        }
//    }
//
//    private fun storeData() {
//        val builder = AlertDialog.Builder(this)
//            .setTitle("Loading")
//            .setMessage("Please Wait")
//            .setCancelable(false)
//            .create()
//        builder.show()
//
//        val data = hashMapOf<String, Any>()
//        data["name"] = bindign
//        data ["phone"] = binding.inputMobile.text.toString()
//
//        Firebase.firestore.collection("users").document(binding.inputMobile.text.toString())
//            .set()
//    }
}