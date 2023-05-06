package com.example.a5046ass2_project.Retrofit

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a5046ass2_project.databinding.ActivityRetrofitKotlinBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class RetrofitKotlin : AppCompatActivity() {
    private lateinit var binding: ActivityRetrofitKotlinBinding
    private var keyword: String = ""
    private val API_KEY = "AIzaSyCWyrwov7lMVMuh06hu107A0vymfu4ElUs"
    private val SEARCH_ID_cx = "450053c8d632e4abd"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitKotlinBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        val retrofitInterface = RetrofitClient.getRetrofitService()
        binding.searchBtn.setOnClickListener {
            keyword = binding.enterProperty.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                val response: Response<SearchResponse> =
                    retrofitInterface.customSearch(
                        API_KEY,
                        SEARCH_ID_cx,
                        keyword
                    )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val list = response.body()!!.items
                        val result: String = list[0].snippet
                        binding.propertyResult.text = result
                    } else {
                        Log.i("Error ", "Response failed")
                    }
                }
            }
        }
    }


}
