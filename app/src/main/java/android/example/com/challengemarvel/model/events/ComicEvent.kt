package android.example.com.challengemarvel.model.events

import android.example.com.challengemarvel.model.Image
import android.example.com.challengemarvel.model.expandables.ComicList
import com.google.gson.annotations.SerializedName
import java.util.*


data class ComicEvent (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("start")
    val startDate: Date?,
    @SerializedName("thumbnail")
    val thumbnail: Image?,
    @SerializedName("comics")
    val comics: ComicList,
    var isExpanded: Boolean = false
        )
