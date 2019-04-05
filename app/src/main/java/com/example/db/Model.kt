package com.example.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User:RealmObject(){
    @PrimaryKey
    var name:String?=null
    var age:String?=null
}