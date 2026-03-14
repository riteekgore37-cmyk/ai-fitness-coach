package com.aifitnesscoach.android.ui.home.ui.plan

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aifitnesscoach.android.network.ApiService
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.allExercises.Data

class ExercisesPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val filterName: String,
    private val filterVal: String
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val page = params.key ?: 0
        return try {
            val response = apiService.getExercises(token, filterName, filterVal, page, params.loadSize)

            if (!response.isSuccessful) {
                return LoadResult.Error(Exception("Server error ${response.code()}"))
            }

            val body = response.body()
                ?: return LoadResult.Error(Exception("Empty response from server"))

            LoadResult.Page(
                data = body.data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (body.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}