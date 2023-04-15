package com.xdiach.diarymoodapp.data.repository

import com.xdiach.diarymoodapp.model.Diary
import com.xdiach.diarymoodapp.util.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object MongoDB : MongoRepository {
    private val app = App.Companion.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

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
}