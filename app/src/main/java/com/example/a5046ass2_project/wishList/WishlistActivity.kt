package com.example.a5046ass2_project.wishList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5046ass2_project.map.Property

import com.example.a5046ass2_project.wishList.adapter.PropertyAdapter
import com.example.a5046ass2_project.databinding.FragmentWishlistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.a5046ass2_project.wishList.WishlistManager


class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var wishlistManager: WishlistManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        wishlistManager = WishlistManager

        CoroutineScope(Dispatchers.Main).launch {
            val propertyList = getProperties()
//            wishlistManager.addPropertyList(propertyList)

            propertyAdapter = PropertyAdapter(wishlistManager.getPropertyList())
            binding.recyclerView.adapter = propertyAdapter
        }
        return binding.root
    }

    private suspend fun getProperties(): List<Property> {
        val propertyList = mutableListOf<Property>()

        // 从 Intent 中获取传递的 property
        val property = requireActivity().intent.getParcelableExtra<Property>("property")

        // 如果获取到属性信息，则添加到属性列表中
        if (property != null) {
            WishlistManager.addProperty(property)
//            propertyList.add(property)
        }

        return WishlistManager.getPropertyList()
    }

}











