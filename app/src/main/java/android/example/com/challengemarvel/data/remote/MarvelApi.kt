package android.example.com.challengemarvel.data.remote

import android.example.com.challengemarvel.model.character.CharacterResponse
import android.example.com.challengemarvel.model.events.EventResponse
import android.example.com.challengemarvel.utils.PRIVATE_API_KEY
import android.example.com.challengemarvel.utils.PUBLIC_API_KEY
import android.example.com.challengemarvel.utils.toMD5Hash
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 15,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = PUBLIC_API_KEY,
        @Query("hash") hash: String = (ts + PRIVATE_API_KEY + PUBLIC_API_KEY).toMD5Hash()
    ): CharacterResponse

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int = 0,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 15,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = PUBLIC_API_KEY,
        @Query("hash") hash: String = (ts + PRIVATE_API_KEY + PUBLIC_API_KEY).toMD5Hash()
    ): CharacterResponse

    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("orderBy") orderBy: String = "-startDate",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 25,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = PUBLIC_API_KEY,
        @Query("hash") hash: String = (ts + PRIVATE_API_KEY + PUBLIC_API_KEY).toMD5Hash()
    ): EventResponse
}