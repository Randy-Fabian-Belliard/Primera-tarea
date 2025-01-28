package edu.ucne.composedemo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.Dao.ClienteDao
import edu.ucne.composedemo.data.local.Dao.ComentarioDao
import edu.ucne.composedemo.data.local.Dao.PrioridadDao
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.ComentarioEntity
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity

/*
@Database(
    entities = [TecnicoEntity::class, ClienteEntity::class, PrioridadEntity::class, TicketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TecnicoDb : RoomDatabase() {

    abstract fun tecnicoDao(): TecnicoDao
    abstract fun clienteDao(): ClienteDao
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao


    companion object {
        @Volatile
        private var INSTANCE: TecnicoDb? = null

        fun getDatabase(context: Context): TecnicoDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TecnicoDb::class.java,
                    "tecnico_db" // Cambia esto si deseas otro nombre para la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
*/


@Database(
    entities = [TecnicoEntity::class, ClienteEntity::class, PrioridadEntity::class, TicketEntity::class, ComentarioEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TecnicoDb : RoomDatabase() {

    abstract fun tecnicoDao(): TecnicoDao
    abstract fun clienteDao(): ClienteDao
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
    abstract fun ComentarioDao ():ComentarioDao

    companion object {
        @Volatile
        private var INSTANCE: TecnicoDb? = null

        fun getDatabase(context: Context): TecnicoDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TecnicoDb::class.java,
                    "tecnicoss_db" // Cambia esto si deseas otro nombre para la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}













/*
package edu.ucne.composedemo.data.local.database


import androidx.room.*
import edu.ucne.composedemo.data.local.Dao.ClienteDao
import edu.ucne.composedemo.data.local.Dao.PrioridadDao
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity


@Database(
    entities = [TecnicoEntity::class, ClienteEntity::class, PrioridadEntity::class, TicketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TecnicoDb : RoomDatabase() {
    abstract fun tecnicoDao(): TecnicoDao
    abstract fun clienteDao(): ClienteDao
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}
*/