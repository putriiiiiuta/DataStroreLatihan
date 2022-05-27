package putriiiiiuta.androidlima.datastrorelatihan.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager (context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore("user_login")

    suspend fun saveData(username: String) {
        dataStore.edit {
            it[USERNAME] = username
        }
    }

    val username: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    companion object {
        val USERNAME = preferencesKey<String>("user_username")

    }