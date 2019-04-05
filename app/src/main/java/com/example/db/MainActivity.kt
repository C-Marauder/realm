package com.example.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xqy.androidx.realm.save

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        User().apply {
            name = "xiaoMing"
            age ="12"
        }.save {
            Log.e("===",it.localizedMessage)
        }
        User().apply {
            name = "勒布朗"
            age ="12"
        }.save {
            Log.e("===",it.localizedMessage)
        }
        User().apply {
            name = "欧文"
            age ="12"
        }.save {
            Log.e("===",it.localizedMessage)
        }

    }
}
