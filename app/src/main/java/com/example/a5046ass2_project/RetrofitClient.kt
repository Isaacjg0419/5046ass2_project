import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class RetrofitClient {

    companion object RetrofitManager {
//        get response of url from google search api
        private val BASE_URL = "https://www.googleapis.com/"
        fun getRetrofitService(): RetrofitInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitInterface::class.java)
        }
    }
}
