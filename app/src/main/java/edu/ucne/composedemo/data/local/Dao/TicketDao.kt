package edu.ucne.composedemo.data.local.Dao

import androidx.room.*
import edu.ucne.composedemo.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TicketDao {
    //@Insert
    //suspend fun save(ticket: TicketEntity)

    @Query("SELECT * FROM Tickets WHERE ticketId = :id LIMIT 1")
    suspend fun find(id: Int): TicketEntity?

    @Query("SELECT * FROM tickets WHERE ticketId = :ticketId")
    fun findById2(ticketId: Int): Flow<TicketEntity?>

    @Update
    suspend fun update(ticket: TicketEntity)

    @Delete
    suspend fun delete(ticket: TicketEntity)

    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketEntity>>


    @Query("DELETE FROM Tickets WHERE ticketId = :ticketId")
    suspend fun deleteById(ticketId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ticket: TicketEntity)

    @Query("SELECT * FROM Tickets WHERE ticketId = :ticketId")
    fun findById(ticketId: Int): Flow<TicketEntity>



}


