package com.example.cs496_week1.fragments.clip_fragment

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config =
            RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name("mydata.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}