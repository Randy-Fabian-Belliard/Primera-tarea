package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.Dao.PrioridadDao
import edu.ucne.composedemo.data.local.entities.PrioridadEntity

class PrioridadRepository(private val prioridadDao: PrioridadDao) {
    suspend fun savePrioridad(prioridad: PrioridadEntity) = prioridadDao.save(prioridad)

    fun getPrioridades() = prioridadDao.getAll()

    suspend fun deletePrioridad(prioridad: PrioridadEntity) {
        prioridadDao.delete(prioridad)
    }

    suspend fun getPrioridad(prioridadId: Int) = prioridadDao.find(prioridadId)
}
