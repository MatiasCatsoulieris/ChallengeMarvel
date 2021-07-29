package android.example.com.challengemarvel.repositories

import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.data.remote.CharacterPagingSource
import android.example.com.challengemarvel.data.remote.MarvelApi
import android.example.com.challengemarvel.model.character.Character
import android.example.com.challengemarvel.model.events.ComicEvent
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class MainRepoImpl(private val api: MarvelApi) : MainRepo {

    override suspend fun getCharacters(): Flow<Resource<List<Character>>> =
        flow<Resource<List<Character>>> {
            emit(Resource.Loading())
            try {
                val result = api.getCharacters()
                result.data?.results?.let {
                    emit(Resource.Success(it))
                }

            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getCharacterById(id: Int): Flow<Resource<List<Character>>> =
        flow<Resource<List<Character>>> {
            emit(Resource.Loading())
            try {
                val result = api.getCharacterById(id)
                result.data?.results?.let {
                    emit(Resource.Success(it))
                }

            } catch (e: Exception) {

                emit(Resource.Failure(e))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getEvents(): Flow<Resource<List<ComicEvent>>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = api.getEvents()
                result.data?.results?.let { eventList ->
                    val filteredList = eventList.filter {
                        it.startDate != null
                    }
                    emit(Resource.Success(filteredList))
                }
            }catch (e: Exception) {
                emit(Resource.Failure(e))
            }

        }.flowOn(Dispatchers.IO)

    override fun getPagingCharacter(): Flow<PagingData<Character>> =
        Pager(PagingConfig(pageSize = 15, enablePlaceholders = false)) {
            CharacterPagingSource(api)
        }.flow.flowOn(Dispatchers.IO)


}
