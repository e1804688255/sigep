package com.sifuturo.sigep.presentacion.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuDto {

    private String label;      // Nombre del menú
    private String routerLink; // Ruta (ej: /home)
    private String icon;       // Icono
    private List<MenuDto> items; // <--- ESTO FALTABA (La lista de hijos)

    // Constructor vacío (necesario a veces para serialización)
    public MenuDto() {
        this.items = new ArrayList<>();
    }

    // Constructor 1: Menú Simple (El que ya tenías)
    public MenuDto(String label, String routerLink, String icon) {
        this.label = label;
        this.routerLink = routerLink;
        this.icon = icon;
        this.items = new ArrayList<>(); // Inicializamos vacía para que no sea null
    }

    // Constructor 2: Menú Padre (ESTE ES EL QUE TE DA EL ERROR)
    // Permite pasarle la lista de hijos directamente
    public MenuDto(String label, String routerLink, String icon, List<MenuDto> items) {
        this.label = label;
        this.routerLink = routerLink;
        this.icon = icon;
        this.items = items;
    }

    // --- Getters y Setters ---
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getRouterLink() { return routerLink; }
    public void setRouterLink(String routerLink) { this.routerLink = routerLink; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public List<MenuDto> getItems() { return items; }
    public void setItems(List<MenuDto> items) { this.items = items; }
}