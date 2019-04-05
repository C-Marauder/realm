package com.example.db

import android.app.Application
import android.util.Log
import com.xqy.androidx.realm.RealmManager
import java.security.SecureRandom

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        RealmManager.init(this){
            val key = ByteArray(64)
            SecureRandom().nextBytes(key)
            Log.e("===", Utils.bytesToHex(key))
            it.encryptionKey(key)
                .build()

        }
    }
}