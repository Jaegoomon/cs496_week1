package com.example.cs496_week1.fragments.clip_fragment

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config1 =
            RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name("mydata.realm")
                .modules(ClipRealmData()).build()
        Realm.setDefaultConfiguration(config1)

        val config2 =
            RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name("tagdata.realm")
                .modules(ClipRealmTagData()).build()
    }
}