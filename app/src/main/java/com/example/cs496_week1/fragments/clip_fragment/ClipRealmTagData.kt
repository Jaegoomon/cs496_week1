package com.example.cs496_week1.fragments.clip_fragment

import io.realm.RealmObject

class ClipRealmTagData : RealmObject() {
    var tag: String = ""
    var color: String = ""
}