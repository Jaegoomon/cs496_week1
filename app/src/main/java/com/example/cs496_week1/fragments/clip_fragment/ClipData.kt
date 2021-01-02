package com.example.cs496_week1.fragments.clip_fragment

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ClipData : RealmObject() {
    var id = 0
    var url: String = ""
    var title: String = ""
    var content: String = ""
    var r = 0
}