package android.example.com.challengemarvel.model.events

data class EventData(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<ComicEvent>?
) {

}