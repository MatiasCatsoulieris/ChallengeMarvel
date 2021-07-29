package android.example.com.challengemarvel.model.events

import com.google.gson.annotations.SerializedName

data class EventResponse (
    @SerializedName("code")
    val code: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val data: EventData?,
    @SerializedName("etag")
    val etag: String?
){
}