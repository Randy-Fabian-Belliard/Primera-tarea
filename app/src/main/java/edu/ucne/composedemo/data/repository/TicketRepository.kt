package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.data.local.entities.TicketEntity

class TicketRepository(private val ticketDao: TicketDao) {
    suspend fun saveTicket(ticket: TicketEntity) = ticketDao.save(ticket)

    fun getTickets() = ticketDao.getAll()

    suspend fun deleteTicket(ticket: TicketEntity) {
        ticketDao.delete(ticket)
    }

    suspend fun getTicket(ticketId: Int) = ticketDao.find(ticketId)
}
