import com.example.a5046ass2_project.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("customsearch/v1")
    suspend fun customSearch(
        @Query("key") API_KEY: String,
        @Query("cx") SEARCH_ID_cx: String,
        @Query("q") keyword: String
    ): Response<SearchResponse>
}
