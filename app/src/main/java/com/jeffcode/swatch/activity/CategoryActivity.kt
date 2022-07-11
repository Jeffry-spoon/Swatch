package com.jeffcode.swatch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jeffcode.swatch.R
import com.jeffcode.swatch.adapter.CategoryAdapter
import com.jeffcode.swatch.adapter.CategoryProductAdapter
import com.jeffcode.swatch.adapter.ProductAdapter
import com.jeffcode.swatch.model.AddProductModel
import com.jeffcode.swatch.model.Category

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProduct(intent.getStringExtra("cat"))
    }

    private fun getProduct(category: String?) {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory", category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = CategoryProductAdapter(this, list)

            }
    }


}