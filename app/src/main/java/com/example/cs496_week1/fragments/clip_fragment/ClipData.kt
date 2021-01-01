package com.example.cs496_week1.fragments.clip_fragment

import io.realm.RealmObject

open class ClipData : RealmObject() {
    var url: String? = null
    var title: String? = null
    var content: String? = null
}