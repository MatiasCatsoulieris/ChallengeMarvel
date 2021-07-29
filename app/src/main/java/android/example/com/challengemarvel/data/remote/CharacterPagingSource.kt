package android.example.com.challengemarvel.data.remote

import android.example.com.challengemarvel.model.character.Character
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

//Manages the pagination from the characters endpoint

class CharacterPagingSource(private val api: MarvelApi) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPageNumber = params.key ?: 0
            val offset = nextPageNumber * 15
            val response = api.getCharacters(offset = offset)

            LoadResult.Page(
                data = response.data?.results!!,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (response.data.results.size == 15) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}