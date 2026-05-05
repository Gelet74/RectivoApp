package com.example.rectivoapp.modelo

data class SeleccionPedido(
    val puertas: Int? = null,
    val apertura: String? = null,
    val medida: Int? = null,
    val colorEstructura: String? = null,
    val colorPuerta: String? = null,
    val cantidad: Int = 1,
    val precioUnitario: Double = 0.0
) {
    fun generarCodigo(): String {
        val tipoPuerta = when {
            puertas == 1 && apertura == "derecha" -> "80"
            puertas == 1 && apertura == "izquierda" -> "85"
            puertas == 2 -> "90"
            else -> ""
        }
        val codigoMedida = when (medida) {
            40, 80 -> "10"
            50, 100 -> "20"
            else -> ""
        }
        return "PT$tipoPuerta$codigoMedida$colorEstructura$colorPuerta"
    }
}
