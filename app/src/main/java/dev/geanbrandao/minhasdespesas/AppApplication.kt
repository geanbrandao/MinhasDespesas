package dev.geanbrandao.minhasdespesas

import android.app.Application
import dev.geanbrandao.minhasdespesas.di.DatabaseModule
import dev.geanbrandao.minhasdespesas.localpreferences.di.PreferencesModule
import dev.geanbrandao.minhasdespesas.navigation.di.NavigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(
                listOf(
//                    AppModule().module,
                    DatabaseModule().module,
                    PreferencesModule().module,
                    NavigationModule().module,
                )
            )
        }
    }
}