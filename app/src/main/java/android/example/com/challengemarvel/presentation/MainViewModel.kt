package android.example.com.challengemarvel.presentation


import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.model.character.Character
import android.example.com.challengemarvel.model.events.ComicEvent
import android.example.com.challengemarvel.repositories.MainRepo
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect



class MainViewModel(private val repo: MainRepo) : ViewModel() {

    init {
        getCharactersList()
    }

    var characterCurrentData: Flow<PagingData<Character>>? = null

    private fun getCharactersList(): Flow<PagingData<Character>> {
        val newResult = repo.getPagingCharacter().cachedIn(viewModelScope)
        characterCurrentData = newResult
        return newResult
    }

    fun getEvents() = liveData<Resource<List<ComicEvent>>> {
        repo.getEvents().collect {
            emit(it)
        }
    }

    fun getCharacterById(id: Int) = liveData<Resource<List<Character>>> {
        repo.getCharacterById(id).collect {
            emit(it)
        }
    }


}



