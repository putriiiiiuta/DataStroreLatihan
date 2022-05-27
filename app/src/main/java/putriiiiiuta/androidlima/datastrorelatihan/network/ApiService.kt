package putriiiiiuta.androidlima.datastrorelatihan.network

import putriiiiiuta.androidlima.datastrorelatihan.model.GetUserItemItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    fun getUser(
        @Query("username") username: String
    ) : Call<List<GetUserItemItem>>
}