package com.xdiach.diarymoodapp.data.repository

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.xdiach.diarymoodapp.R
import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.ui.UiText
import com.xdiach.diarymoodapp.util.Constants.APP_ID
import com.xdiach.diarymoodapp.util.RequestState
import com.xdiach.diarymoodapp.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.time.ZoneId

object MongoDB : MongoRepository {
    private val app = App.Companion.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user = user,
                schema = setOf(Diary::class),
            )
                .initialSubscriptions { sub ->
                    add(
                        query = sub.query("ownerId == $0", user.id),
                        name = "Personal Diaries",
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getAllDiaries(): Flow<Diaries> {
        return if (user != null) {
            try {
                realm.query<Diary>(query = "ownerId == $0", user.id)
                    .sort(property = "date", sortOrder = Sort.DESCENDING)
                    .asFlow()
                    .map { result ->
                        RequestState.Success(
                            data = result.list.groupBy {
                                it.date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                        )
                    }
            } catch (e: Exception) {
                flow { emit(RequestState.Error(e)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }
}

private class UserNotAuthenticatedException :
    Exception("User is not logged in!")
