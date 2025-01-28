package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "Tickets",
   /* foreignKeys = [
        ForeignKey(entity = ClienteEntity::class, parentColumns = ["clienteId"], childColumns = ["clienteId"]),
        ForeignKey(entity = TecnicoEntity::class, parentColumns = ["tecnicoId"], childColumns = ["tecnicoId"]),
        ForeignKey(entity = PrioridadEntity::class, parentColumns = ["prioridadId"], childColumns = ["prioridadId"])
    ]*/
)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Int? = null,
    val fecha: String = "",
    val clienteId: Int,
    val tecnicoId: Int,
    val prioridadId: Int,
    val asunto: String = "",
    val descripcion: String = ""
)
