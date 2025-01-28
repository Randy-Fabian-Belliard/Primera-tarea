package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "Prioridades")
data class PrioridadEntity(
    @PrimaryKey(autoGenerate = true)
    val prioridadId: Int? = null,
    val nivel: String = ""
)