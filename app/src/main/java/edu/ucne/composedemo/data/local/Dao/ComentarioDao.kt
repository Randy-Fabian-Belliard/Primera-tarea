package edu.ucne.composedemo.data.local.Dao

import androidx.room.*
import edu.ucne.composedemo.data.local.entities.ComentarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioDao {

    // Insertar un nuevo comentario
  //  @Insert(onConflict = OnConflictStrategy.IGNORE)
   // suspend fun insert(comentario: ComentarioEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comentario: ComentarioEntity)

    // Actualizar un comentario existente
    @Update
    suspend fun update(comentario: ComentarioEntity)

    // Eliminar un comentario
    @Delete
    suspend fun delete(comentario: ComentarioEntity)

    // Obtener todos los comentarios
    @Query("SELECT * FROM Comentarios")
    fun getAll(): Flow<List<ComentarioEntity>>

    // Obtener comentarios por ticketId
    @Query("SELECT * FROM Comentarios WHERE ticketId = :ticketId")
    fun getByTicketId(ticketId: Int): Flow<List<ComentarioEntity>>

    // Obtener comentarios por tecnicoId
    @Query("SELECT * FROM Comentarios WHERE tecnicoId = :tecnicoId")
    fun getByTecnicoId(tecnicoId: String): Flow<List<ComentarioEntity>>

    // Obtener un comentario por su ID
    @Query("SELECT * FROM Comentarios WHERE comentarioId = :comentarioId LIMIT 1")
    suspend fun findById(comentarioId: Int): ComentarioEntity?

   // @Query("DELETE FROM Comentarios WHERE ticketId = :ticketId")
   //  fun deleteByTicketId(ticketId: Int)

    @Query("DELETE FROM Comentarios WHERE ticketId = :ticketId")
    suspend fun deleteByTicketId(ticketId: Int)

}
