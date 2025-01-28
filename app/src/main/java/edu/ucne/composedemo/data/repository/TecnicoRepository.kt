package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.entities.TecnicoEntity


class TecnicoRepository(private val tecnicoDao: TecnicoDao) {
    suspend fun saveTecnico(tecnico: TecnicoEntity) = tecnicoDao.save(tecnico)

    fun getTecnicos() = tecnicoDao.getAll()

    suspend fun deleteTecnico(tecnico: TecnicoEntity){
        tecnicoDao.delete(tecnico)
    }

    suspend fun getTecnico(tecnicoId:Int) = tecnicoDao.find(tecnicoId)

}