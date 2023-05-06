package com.example.a5046ass2_project

import com.google.gson.annotations.SerializedName


class SearchResponse {
    @SerializedName("items")
    var items: List<Items> = ArrayList()

}