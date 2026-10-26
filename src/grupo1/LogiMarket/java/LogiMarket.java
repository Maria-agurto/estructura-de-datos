package grupo1.LogiMarket.java;

import grupo1.ui.consola.MenuSeguridad;

public class LogiMarket {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SISTEMA DE GESTIÓN LOGÍSTICA - LOGIMARKET (2026) ");
        System.out.println("==================================================");

        // Inicializar y desplegar el menú de seguridad (Módulo Paolo)
        MenuSeguridad menuSeguridad = new MenuSeguridad();
        menuSeguridad.mostrarMenu();
    }
}