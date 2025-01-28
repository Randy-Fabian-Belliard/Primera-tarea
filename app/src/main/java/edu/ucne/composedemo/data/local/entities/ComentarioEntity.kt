package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "Comentarios",
    foreignKeys = [
        ForeignKey(entity = TicketEntity::class, parentColumns = ["ticketId"], childColumns = ["ticketId"]),
        ForeignKey(entity = TecnicoEntity::class, parentColumns = ["tecnicoId"], childColumns = ["tecnicoId"])
    ]
)
data class ComentarioEntity(
    @PrimaryKey(autoGenerate = true) val comentarioId: Int = 0,
    val ticketId: Int,
    val tecnicoId: Int,
    val mensaje: String,
    val fecha: String
)