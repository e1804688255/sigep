package com.sifuturo.sigep.aplicacion.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class AppUtil {
    private AppUtil() {}

    public static void copiarPropiedadesNoNulas(Object origen, Object destino) {
        // Obtenemos los nombres de propiedades nulas
        String[] nullProperties = getNullPropertyNames(origen);
        
        // Creamos una lista de campos que JAMÁS deben ser sobrescritos
        Set<String> ignoreFields = new HashSet<>(Set.of("id", "fechaCreacion", "usuarioCreacion"));
        
        // Unimos ambos arrays
        for (String prop : nullProperties) {
            ignoreFields.add(prop);
        }

        BeanUtils.copyProperties(origen, destino, ignoreFields.toArray(new String[0]));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // Si el valor es nulo o es el campo "class" (de Java)
            if (srcValue == null || pd.getName().equals("class")) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}