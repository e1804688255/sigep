package com.sifuturo.sigep.aplicacion.casosuso.impl;

import org.springframework.stereotype.Service;
import com.sifuturo.sigep.presentacion.dto.SolicitudDiagnosticoDto;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiagnosticoIAService {

	public Map<String, String> generarSugerencia(SolicitudDiagnosticoDto datos) {
		String motivo = datos.getMotivo() != null ? datos.getMotivo().toLowerCase() : "";
		double temp = parseDoubleSeguro(datos.getTemperatura());

		StringBuilder diagnostico = new StringBuilder("Sugerencia basada en datos clínicos:\n");
		StringBuilder tratamiento = new StringBuilder("Protocolo sugerido:\n");

		// --- LÓGICA SIMULADA DE IA (REEMPLAZAR POR LLAMADA A CHATGPT SI TIENES KEY)
		// ---

		boolean posibleInfeccion = false;

		// 1. Análisis de Fiebre
		if (temp > 38.0) {
			diagnostico.append("- Síndrome febril activo.\n");
			tratamiento.append("- Paracetamol 500mg c/8h si hay fiebre o dolor.\n");
			tratamiento.append("- Medios físicos para bajar temperatura.\n");
			posibleInfeccion = true;
		} else if (temp > 37.0) {
			diagnostico.append("- Febrícula.\n");
		}

		// 2. Análisis por Motivo (Palabras clave)
		if (motivo.contains("garganta") || motivo.contains("dolor al tragar")) {
			diagnostico.append("- Posible Faringoamigdalitis aguda.\n");
			tratamiento.append("- Gargarismos con agua y sal.\n");
			if (posibleInfeccion)
				tratamiento.append("- Evaluar uso de antibióticos (Amoxicilina) según criterio médico.\n");
		} else if (motivo.contains("cabeza") || motivo.contains("migraña")) {
			diagnostico.append("- Cefalea tensional o Migraña.\n");
			tratamiento.append("- Reposo en habitación oscura.\n- Ketorolaco o Ibuprofeno.\n");
		} else if (motivo.contains("estomago") || motivo.contains("vomito")) {
			diagnostico.append("- Gastroenteritis probable.\n");
			tratamiento.append("- Hidratación oral con suero.\n- Dieta blanda astringente.\n");
		} else if (motivo.contains("respirar") || motivo.contains("pecho")) {
			diagnostico.append("⚠️ ALERTA: Posible afección respiratoria/cardíaca. Evaluar saturación.\n");
		} else {
			diagnostico.append("- Cuadro clínico a determinar por evaluación física.\n");
			tratamiento.append("- Tratamiento sintomático según evolución.\n");
		}

		// Respuesta estructurada
		Map<String, String> respuesta = new HashMap<>();
		respuesta.put("diagnostico", diagnostico.toString());
		respuesta.put("tratamiento", tratamiento.toString());

		return respuesta;
	}

	private double parseDoubleSeguro(String valor) {
		try {
			if (valor == null)
				return 0.0;
			return Double.parseDouble(valor.replace(",", "."));
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
}