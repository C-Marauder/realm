package com.xqy.androidx.realm

import io.realm.RealmObject
import io.realm.RealmQuery


inline fun <reified T : RealmObject> T.insert(crossinline exception: (e:Exception) -> Unit) {
    RealmManager.catchException({
        RealmManager.getRealm().executeTransaction {
            it.insertOrUpdate(this)
        }
    }, {
        exception(it)
    })

}
inline fun <reified T : RealmObject> T.save(crossinline exception: (e:Exception) -> Unit) {
    RealmManager.catchException({
        RealmManager.getRealm().executeTransaction {
            it.copyToRealmOrUpdate(this)
        }
    }, {
        exception(it)
    })

}


inline fun <reified T : RealmObject> T.saveAsync(
    crossinline success: () -> Unit,
    crossinline exception: () -> Unit
) {
    RealmManager.getRealm().executeTransactionAsync({
        it.copyToRealmOrUpdate(this)
    }, {
        success()
    }, {
        exception()
    })


}

fun <T : RealmObject> queryAsync(
    clazz: Class<T>,
    condition: (realmQuery: RealmQuery<out T>) -> Unit,
    success: () -> Unit,
    exception: () -> Unit
) {

    RealmManager.getRealm().executeTransactionAsync({
        it.where(clazz).apply {
            condition(this)
        }
    }, {
        success()
    }, {
        exception()
    })


}


fun <T : RealmObject> deleteAsync(clazz: Class<T>, success: () -> Unit, exception: () -> Unit) {
    RealmManager.getRealm().executeTransactionAsync({
        it.delete(clazz)
    }, {
        success()
    }, {
        exception()
    })


}

inline fun <reified T : RealmObject> T.insertAsync(
    crossinline exception: () -> Unit,
    crossinline success: () -> Unit
) {
    RealmManager.getRealm().executeTransactionAsync({
        it.insertOrUpdate(this)
    }, {
        success()
    }, {
        exception()
    })

}
