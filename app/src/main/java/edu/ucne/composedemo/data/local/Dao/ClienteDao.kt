package edu.ucne.composedemo.data.local.Dao

import androidx.room.*
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.ComentarioEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Upsert
    suspend fun save(cliente: ClienteEntity)

    @Query("SELECT * FROM Clientes WHERE clienteId = :id LIMIT 1")
    suspend fun find(id: Int): ClienteEntity?

    @Query("SELECT * FROM Clientes")
    fun getAll(): Flow<List<ClienteEntity>>

    @Delete
    suspend fun delete(cliente: ClienteEntity)

    @Query("DELETE FROM Clientes WHERE clienteId = :clienteId")
    suspend fun deleteById(clienteId: Int)

    @Query("SELECT * FROM Clientes WHERE clienteId = :id LIMIT 1")
    suspend fun findById(id: Int): ClienteEntity?

    @Query("SELECT * FROM comentarios WHERE ticketId = :ticketId")
    fun getByTicketId(ticketId: Int): Flow<List<ComentarioEntity>>
}
