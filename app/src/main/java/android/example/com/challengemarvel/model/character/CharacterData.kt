package android.example.com.challengemarvel.model.character


data class CharacterData(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<Character>?
) {

}