package android.example.com.challengemarvel.model.character

import com.google.gson.annotations.SerializedName



data class CharacterResponse (
    @SerializedName("code")
val code: Int?,
    @SerializedName("status")
val status: String?,
    @SerializedName("data")
val data: CharacterData?,
    @SerializedName("etag")
val etag: String?
        ){
}

