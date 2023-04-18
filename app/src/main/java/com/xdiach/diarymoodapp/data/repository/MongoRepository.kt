package com.xdiach.diarymoodapp.data.repository

import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.model.RequestState
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<Diaries>
    fun getSelectedDiary(diaryId: ObjectId): Flow<RequestState<Diary>>
    suspend fun insertDiary(diary: Diary): RequestState<Diary>
    suspend fun updateDiary(diary: Diary): RequestState<Diary>
    suspend fun deleteDiary(id: ObjectId): RequestState<Diary>
}
