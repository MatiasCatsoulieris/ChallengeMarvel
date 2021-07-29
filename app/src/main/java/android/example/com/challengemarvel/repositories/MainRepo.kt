package android.example.com.challengemarvel.repositories

import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.model.character.Character
import android.example.com.challengemarvel.model.events.ComicEvent
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MainRepo {

    suspend fun getCharacters(): Flow<Resource<List<Character>>>

    suspend fun getCharacterById(id: Int): Flow<Resource<List<Character>>>

    suspend fun getEvents(): Flow<Resource<List<ComicEvent>>>

    fun getPagingCharacter(): Flow<PagingData<Character>>


}