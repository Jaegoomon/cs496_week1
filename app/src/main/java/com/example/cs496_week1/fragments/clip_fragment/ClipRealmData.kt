package com.example.cs496_week1.fragments.clip_fragment

import io.realm.RealmObject

open class ClipRealmData : RealmObject() {
    var id = 0
    var url: String = ""
    var title: String = ""
    var content: String = ""
}