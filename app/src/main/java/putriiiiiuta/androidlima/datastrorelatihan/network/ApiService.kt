package putriiiiiuta.androidlima.datastrorelatihan.network

interface ApiService {
    @GET("user")
    fun getUser(
        @Query("username") username: String
    ) : Call<List<GetUserItemItem>>
}