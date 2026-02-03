package com.sifuturo.sigep.aplicacion.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class AppUtil {
	private AppUtil() {}

	/**
	 * Copia las propiedades de origen a destino, IGNORANDO las que sean nulas. Esto
	 * permite una actualización tipo PATCH.
	 */
	public static void copiarPropiedadesNoNulas(Object origen, Object destino) {
		BeanUtils.copyProperties(origen, destino, getNullPropertyNames(origen));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}