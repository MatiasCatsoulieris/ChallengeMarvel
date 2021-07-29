package android.example.com.challengemarvel.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
): Parcelable
