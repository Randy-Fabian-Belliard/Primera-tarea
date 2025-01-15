package edu.ucne.composedemo.data.local.Dao

import androidx.room.*
import edu.ucne.composedemo.MainActivity
import kotlinx.coroutines.flow.Flow
import edu.ucne.composedemo.data.local.entities.TecnicoEntity

@Dao
interface TecnicoDao {
    @Upsert
    suspend fun save(tecnico: TecnicoEntity)

    @Query("SELECT * FROM Tecnicos WHERE tecnicoId = :id LIMIT 1")
    suspend fun find(id: Int): TecnicoEntity?

    @Delete
    suspend fun delete(tecnico: TecnicoEntity)

    @Query("SELECT * FROM Tecnicos")
    fun getAll(): Flow<List<TecnicoEntity>>
}