package com.example.cs496_week1.fragments.clip_fragment

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ClipRealmData : RealmObject() {
    @PrimaryKey
    var id = 0

    var url: String = ""
    var title: String = ""
    var content: String = ""
    var tag: String = ""
    var tag_color: Int = 0
}