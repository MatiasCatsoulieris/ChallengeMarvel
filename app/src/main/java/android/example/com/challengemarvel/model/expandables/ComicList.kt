package android.example.com.challengemarvel.model.expandables

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicList(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("returned")
    val returned: Int?,
    @SerializedName("items")
    val items: List<ComicSummary>
): Parcelable

@Parcelize
data class ComicSummary (
    @SerializedName("resourceURI")
    val resourceUri: String?,
    @SerializedName("name")
    val name: String?
        ) : Parcelable
