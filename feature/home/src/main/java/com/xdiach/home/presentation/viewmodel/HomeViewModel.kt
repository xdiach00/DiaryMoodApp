package com.xdiach.home.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.xdiach.home.model.Entry
import com.xdiach.mongo.database.ImageToDeleteDao
import com.xdiach.mongo.database.entity.ImageToDeleteEntity
import com.xdiach.mongo.repository.Diaries
import com.xdiach.mongo.repository.MongoDB
import com.xdiach.util.connectivity.ConnectivityObserver
import com.xdiach.util.connectivity.NetworkConnectivityObserver
import com.xdiach.util.model.Diary
import com.xdiach.util.model.Mood
import com.xdiach.util.model.RequestState
import com.xdiach.util.toInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val connectivity: NetworkConnectivityObserver,
    private val imageToDeleteDao: ImageToDeleteDao
) : ViewModel() {
    private lateinit var allDiariesJob: Job
    private lateinit var filteredDiariesJob: Job

    var diaries: MutableState<Diaries> = mutableStateOf(RequestState.Idle)
    private var network by mutableStateOf(ConnectivityObserver.Status.Unavailable)
    var dateIsSelected by mutableStateOf(false)
        private set

    init {
        getDiaries()
        viewModelScope.launch {
            connectivity.observe().collect {
                network = it
            }
        }
    }

    fun getDiaries(zonedDateTime: ZonedDateTime? = null) {
        dateIsSelected = zonedDateTime != null
        diaries.value = RequestState.Loading
        if (dateIsSelected && zonedDateTime != null) {
            observeFilteredDiaries(zonedDateTime = zonedDateTime)
        } else {
            observeAllDiaries()
        }
    }

    private fun observeAllDiaries() {
        allDiariesJob = viewModelScope.launch {
            if (::filteredDiariesJob.isInitialized) {
                filteredDiariesJob.cancelAndJoin()
            }
            MongoDB.getAllDiaries().collect { result ->
                diaries.value = result
            }
        }
    }

    private fun observeFilteredDiaries(zonedDateTime: ZonedDateTime) {
        filteredDiariesJob = viewModelScope.launch {
            if (::allDiariesJob.isInitialized) {
                allDiariesJob.cancelAndJoin()
            }
            MongoDB.getFilteredDiaries(zonedDateTime).collect { result ->
                diaries.value = result
            }
        }
    }

    fun generateStats(diaries: Map<LocalDate, List<Diary>>): List<Entry> {
        for (diary in diaries) Log.d("STATS:", "$diary")
        val stats = mutableListOf<Entry>()
        var index = 0
        for ((date, diaryList) in diaries.toSortedMap()) {
            var totalPower = 0
            for (diary in diaryList) {
                totalPower += Mood.valueOf(diary.mood).power
            }
            stats.add(Entry(date, index.toFloat(), totalPower.toFloat()))
            index++
        }
        return stats
    }

    fun deleteAllDiaries(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (network == ConnectivityObserver.Status.Available) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val imagesDirectory = "images/$userId"
            val storage = FirebaseStorage.getInstance().reference
            storage.child(imagesDirectory)
                .listAll()
                .addOnSuccessListener {
                    it.items.forEach { ref ->
                        val imagePath = "images/$userId/${ref.name}"
                        storage.child(imagePath).delete()
                            .addOnFailureListener {
                                viewModelScope.launch(Dispatchers.IO) {
                                    imageToDeleteDao.addImageToDelete(
                                        ImageToDeleteEntity(
                                            remoteImagePath = imagePath
                                        )
                                    )
                                }
                            }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = MongoDB.deleteAllDiaries()
                        if (result is RequestState.Success) {
                            withContext(Dispatchers.Main) {
                                onSuccess()
                            }
                        } else if (result is RequestState.Error) {
                            withContext(Dispatchers.Main) {
                                onError(result.error)
                            }
                        }
                    }
                }.addOnFailureListener { onError(it) }
        } else {
            onError(Exception("No Internet connection"))
        }
    }
}
