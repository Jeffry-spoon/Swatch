package com.jeffcode.swatch.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jeffcode.swatch.activity.AddressActivity
import com.jeffcode.swatch.adapter.CartAdapter
import com.jeffcode.swatch.databinding.FragmentCartragmentBinding
import com.jeffcode.swatch.roomdb.AppDatabase
import com.jeffcode.swatch.roomdb.ProductModel
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class Cartragment : Fragment() {

    private lateinit var binding: FragmentCartragmentBinding
    private lateinit var list : ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartragmentBinding.inflate(layoutInflater)

        val preferences = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()) {
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)

            list.clear()
            for (data in it) {
                list.add(data.productId)
            }

            totalCost(it)
        }

        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for ( item in data!!) {
            total += item.productSp!!.toInt()
        }

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("IDR"))

        val harga = format.format(total)
        binding.textView12.text = "Total item in cart is ${data.size}"
        binding.textView13.text = "Total Cost : $harga"

        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("productIds", list)
            b.putString("totalCost", total.toString())
            intent.putExtras(b)
            startActivity(intent)

        }
    }


}
