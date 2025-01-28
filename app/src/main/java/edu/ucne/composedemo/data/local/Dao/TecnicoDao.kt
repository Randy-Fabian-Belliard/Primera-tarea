package edu.ucne.composedemo.data.local.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.ucne.composedemo.MainActivity
import kotlinx.coroutines.flow.Flow
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity


@Dao
interface TecnicoDao {
    @Upsert
    suspend fun save(tecnico: TecnicoEntity)

    @Query("SELECT * FROM Tecnicos WHERE tecnicoId = :id LIMIT 1")
    suspend fun find(id: Int): TecnicoEntity?


    @Query("SELECT * FROM Tecnicos")
    fun getAll(): Flow<List<TecnicoEntity>>

    @Delete
    suspend fun delete(tecnico: TecnicoEntity)

    @Query("DELETE FROM Tecnicos WHERE tecnicoId = :tecnicoId")
    suspend fun deleteById(tecnicoId: Int)

    @Query("SELECT * FROM Tecnicos WHERE tecnicoId = :id LIMIT 1")
    suspend fun findById(id: Int): TecnicoEntity?
}


