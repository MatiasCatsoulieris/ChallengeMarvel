package android.example.com.challengemarvel.model.character

import android.example.com.challengemarvel.model.Image
import android.example.com.challengemarvel.model.expandables.ComicList
import com.google.gson.annotations.SerializedName



data class Character(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("thumbnail")
    val thumbnail: Image?,
    @SerializedName("comics")
    val comics: ComicList
)   {}
