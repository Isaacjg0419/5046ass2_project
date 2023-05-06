package com.example.a5046ass2_project.Retrofit

import com.example.a5046ass2_project.Retrofit.Items
import com.google.gson.annotations.SerializedName


class SearchResponse {
    @SerializedName("items")
    var items: List<Items> = ArrayList()

}