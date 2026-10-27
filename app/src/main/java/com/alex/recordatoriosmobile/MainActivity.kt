
package com.alex.recordatoriosmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width

enum class Prioridad {
    BAJA, MEDIA, ALTA
}

sealed class EstadoRecordatorio {
    data object Pendiente : EstadoRecordatorio()
    data object Completado : EstadoRecordatorio()
    data object Pospuesto : EstadoRecordatorio()
}

data class Categoria(
    val nombre: String,
    val color: Color
)

data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String? = null,
    val categoria: String,
    val prioridad: Prioridad,
    val estado: EstadoRecordatorio,
    val ubicacion: String? = null
)

private val coloresCategoria = mapOf(
    "Estudio" to Color(0xFF1976D2),
    "Trabajo" to Color(0xFF8E24AA),
    "Personal" to Color(0xFF00897B),
    "Salud" to Color(0xFFEF6C00)
)

private val fondosCategoria = mapOf(
    "Estudio" to Color(0xFFE3F2FD),
    "Trabajo" to Color(0xFFF3E5F5),
    "Personal" to Color(0xFFE0F2F1),
    "Salud" to Color(0xFFFFF3E0)
)

private val recordatorios = listOf(
    Recordatorio(
        id = 1,
        titulo = "Estudiar Kotlin",
        descripcion = "Repasar clases y colecciones",
        categoria = "Estudio",
        prioridad = Prioridad.ALTA,
        estado = EstadoRecordatorio.Pendiente
    ),
    Recordatorio(
        id = 2,
        titulo = "Entregar actividad mobile",
        descripcion = "Completar la Actividad 1",
        categoria = "Estudio",
        prioridad = Prioridad.ALTA,
        estado = EstadoRecordatorio.Pendiente
    ),
    Recordatorio(
        id = 3,
        titulo = "Revisar tareas del trabajo",
        descripcion = "Organizar las tareas pendientes",
        categoria = "Trabajo",
        prioridad = Prioridad.MEDIA,
        estado = EstadoRecordatorio.Completado
    ),
    Recordatorio(
        id = 4,
        titulo = "Comprar alimentos",
        descripcion = "Pasar por el supermercado",
        categoria = "Personal",
        prioridad = Prioridad.BAJA,
        estado = EstadoRecordatorio.Pendiente,
        ubicacion = "Supermercado"
    ),
    Recordatorio(
        id = 5,
        titulo = "Organizar la semana",
        descripcion = "Preparar las actividades",
        categoria = "Personal",
        prioridad = Prioridad.MEDIA,
        estado = EstadoRecordatorio.Pospuesto
    ),
    Recordatorio(
        id = 6,
        titulo = "Salir a caminar",
        descripcion = "Dedicar tiempo a la actividad física",
        categoria = "Salud",
        prioridad = Prioridad.MEDIA,
        estado = EstadoRecordatorio.Pendiente
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PantallaRecordatorios()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRecordatorios() {

    val pendientes = recordatorios.count {
        it.estado is EstadoRecordatorio.Pendiente
    }

    Scaffold(
        containerColor = Color(0xFFF5F7FF),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Mis recordatorios",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Organizá tu día ✨",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5C6BC0),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FF))
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3949AB)
                ),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "¡Hola! 👋",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Cada tarea te acerca a tu objetivo.",
                            color = Color(0xFFE8EAF6),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(
                        text = "📋",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Resumen(
                    titulo = "TOTAL",
                    cantidad = recordatorios.size,
                    color = Color(0xFF1976D2),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Resumen(
                    titulo = "PENDIENTES",
                    cantidad = pendientes,
                    color = Color(0xFFEF6C00),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Tus tareas 📝",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF283593)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = recordatorios,
                    key = { it.id }
                ) { recordatorio ->
                    TarjetaRecordatorio(recordatorio)
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun Resumen(
    titulo: String,
    cantidad: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = cantidad.toString(),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = titulo,
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TarjetaRecordatorio(recordatorio: Recordatorio) {

    val colorCategoria =
        coloresCategoria[recordatorio.categoria] ?: Color.Gray

    val fondoTarjeta =
        fondosCategoria[recordatorio.categoria] ?: Color(0xFFF5F5F5)

    val emojiCategoria = when (recordatorio.categoria) {
        "Estudio" -> "📚"
        "Trabajo" -> "💼"
        "Personal" -> "✨"
        "Salud" -> "💪"
        else -> "📝"
    }

    val textoEstado = when (recordatorio.estado) {
        is EstadoRecordatorio.Pendiente -> "Pendiente"
        is EstadoRecordatorio.Completado -> "Completado"
        is EstadoRecordatorio.Pospuesto -> "Pospuesto"
    }

    val colorEstado = when (recordatorio.estado) {
        is EstadoRecordatorio.Pendiente -> Color(0xFFEF6C00)
        is EstadoRecordatorio.Completado -> Color(0xFF2E7D32)
        is EstadoRecordatorio.Pospuesto -> Color(0xFF6A1B9A)
    }

    val emojiEstado = when (recordatorio.estado) {
        is EstadoRecordatorio.Pendiente -> "⏳"
        is EstadoRecordatorio.Completado -> "✅"
        is EstadoRecordatorio.Pospuesto -> "⏸️"
    }

    val textoPrioridad = when (recordatorio.prioridad) {
        Prioridad.BAJA -> "Baja"
        Prioridad.MEDIA -> "Media"
        Prioridad.ALTA -> "Alta"
    }

    val colorPrioridad = when (recordatorio.prioridad) {
        Prioridad.BAJA -> Color(0xFF2E7D32)
        Prioridad.MEDIA -> Color(0xFFEF6C00)
        Prioridad.ALTA -> Color(0xFFC62828)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = fondoTarjeta
        ),
        border = BorderStroke(
            width = 1.dp,
            color = colorCategoria.copy(alpha = 0.35f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(7.dp)
                    .height(1.dp)
                    .background(colorCategoria)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$emojiCategoria ${recordatorio.categoria}",
                        color = colorCategoria,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = colorPrioridad.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "Prioridad $textoPrioridad",
                            color = colorPrioridad,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = recordatorio.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )

                recordatorio.descripcion?.let { descripcion ->
                    Text(
                        text = descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF546E7A)
                    )
                }

                HorizontalDivider(
                    color = colorCategoria.copy(alpha = 0.20f)
                )

                Text(
                    text = "$emojiEstado $textoEstado",
                    color = colorEstado,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                recordatorio.ubicacion?.let { ubicacion ->
                    Text(
                        text = "📍 $ubicacion",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF546E7A)
                    )
                }
            }
        }
    }
}
