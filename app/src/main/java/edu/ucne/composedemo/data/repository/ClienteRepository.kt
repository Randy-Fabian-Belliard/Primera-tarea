package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.Dao.ClienteDao
import edu.ucne.composedemo.data.local.entities.ClienteEntity

class ClienteRepository(private val clienteDao: ClienteDao) {
    suspend fun saveCliente(cliente: ClienteEntity) = clienteDao.save(cliente)

    fun getClientes() = clienteDao.getAll()

    suspend fun deleteCliente(cliente: ClienteEntity) {
        clienteDao.delete(cliente)
    }

    suspend fun getCliente(clienteId: Int) = clienteDao.find(clienteId)
}
