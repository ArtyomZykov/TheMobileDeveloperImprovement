package com.example.weathertracker.shared.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathertracker.shared.data.db.WeatherDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val roomDatabaseModule: Module = module {
    single<RoomDatabase.Builder<WeatherDatabase>> {
        getDatabaseBuilder(get())
    }
}

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<WeatherDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(WeatherDatabase.DATABASE_NAME)
    return Room.databaseBuilder<WeatherDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
