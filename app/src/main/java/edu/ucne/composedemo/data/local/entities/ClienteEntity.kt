package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "Clientes")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true)
    val clienteId: Int? = null,
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = ""
)