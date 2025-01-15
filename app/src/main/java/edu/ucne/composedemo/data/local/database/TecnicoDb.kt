package edu.ucne.composedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.entities.TecnicoEntity

@Database(
        entities = [TecnicoEntity::class],
        version = 1,
        exportSchema = false
    )
    abstract class TecnicoDb : RoomDatabase() {
        abstract fun tecnicoDao(): TecnicoDao
    }