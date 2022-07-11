package com.jeffcode.swatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jeffcode.swatch.R
import com.jeffcode.swatch.databinding.ActivityRegisterBinding
import com.jeffcode.swatch.model.UserModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            validateUser()
        }

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateUser() {
        if(binding.etPhone.text!!.isEmpty()) {
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }else {
            storeData()
        }
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString("number", binding.etPhone.text.toString())
        editor.putString("name",binding.etUser.text.toString())
        editor.apply()


        val data = UserModel(userName = binding.etUser.text.toString(), userPhoneNumber = binding.etPhone.text.toString())

        Firebase.firestore.collection("users").document(binding.etPhone.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()

            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    // 3:35:13
    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}