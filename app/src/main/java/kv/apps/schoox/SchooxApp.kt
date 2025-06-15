package kv.apps.schoox

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kv.apps.schoox.data.local.initializer.DatabaseInitializer
import javax.inject.Inject

@HiltAndroidApp
class SchooxApp : Application() {
    @Inject lateinit var dbInitializer: DatabaseInitializer

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            dbInitializer.initialize()
        }
    }
}