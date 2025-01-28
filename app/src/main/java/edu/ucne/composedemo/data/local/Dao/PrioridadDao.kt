package edu.ucne.composedemo.data.local.Dao

import androidx.room.*
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {

    @Upsert // Inserta o actualiza automáticamente
    suspend fun save(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades WHERE prioridadId = :id LIMIT 1")
    suspend fun find(id: Int): PrioridadEntity?

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<PrioridadEntity>>

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("DELETE FROM Prioridades WHERE prioridadId = :prioridadId")
    suspend fun deleteById(prioridadId: Int)

    @Query("SELECT * FROM Prioridades WHERE prioridadId = :id LIMIT 1")
    suspend fun findById(id: Int): PrioridadEntity?
}
