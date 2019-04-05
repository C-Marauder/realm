package com.xqy.androidx.realm

import android.app.Application
import io.realm.*

object RealmManager {
    private lateinit var realmConfig: RealmConfiguration
    fun getRealm(): Realm {
        return if (RealmManager::realmConfig.isInitialized) {
            Realm.getInstance(realmConfig)
        } else {
            Realm.getDefaultInstance()
        }
    }

    fun init(application: Application, useDefault: Boolean = true, createRealmConfig:(config:RealmConfiguration.Builder)->RealmConfiguration?) {
        Realm.init(application)
        val realmConfig =  createRealmConfig(RealmConfiguration.Builder())
        realmConfig?.let {
            Realm.deleteRealm(realmConfig)
            if (useDefault) {
                Realm.setDefaultConfiguration(it)

            } else {
                RealmManager.realmConfig = it
            }



        }
    }

    fun catchException(execute: () -> Unit, handle: (e:Exception) -> Unit) {
        try {
            execute()
        } catch (e: Exception) {
            handle(e)
        } finally {
            getRealm().close()
        }
    }




    fun <T : RealmObject> saveAsync(
        clazz: Class<T>,
        init: (t: T) -> Unit,
        exception: () -> Unit,
        success: () -> Unit
    ) {

        getRealm().executeTransactionAsync({
            init(it.createObject(clazz))
        }, {
            success()
        }, {
            exception()
        })


    }






    fun <T : RealmObject> query(
        clazz: Class<T>,
        condition: (realmQuery: RealmQuery<out T>) -> Unit,
        exception: () -> Unit
    ) {
        catchException({
            getRealm().executeTransaction {
                it.where(clazz).apply {
                    condition(this)
                }
            }

        }, {
            exception()
        })


    }






}