package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Tecnicos")

    data class TecnicoEntity(
        @PrimaryKey(autoGenerate = true)
        val tecnicoId: Int? = null,
        val nombres: String = "",
        val sueldo: Float? = null
    )