package grupo1.persistencia;

import grupo1.estructuras.arbol.ArbolRojoNegro;
import grupo1.modelo.Producto;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ArchivoProductos {

    //Lee data/productos.txt e inserta cada producto valido en el arbol.
    public static ArrayList<String> cargar(ArbolRojoNegro arbol) {
        ArrayList<String> errores = new ArrayList<>();
        Path path = Path.of("data/productos.txt");

        // Archivo inexistente
        if (!Files.exists(path)) {
            errores.add("No se encontró data/productos.txt. Se inicia con el catálogo vacío.");
            return errores;
        }
        // el archivo se cierra solo al terminar el bloque.
        try (BufferedReader lector = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();

                // Se ignoran las lineas vacias
                if (linea.isEmpty()) {
                    continue;
                }
                // Cada linea va en su propio try/catch: una linea mala no detiene la carga
                try {
                    Producto producto = convertirLinea(linea);
                    arbol.insertar(producto);
                } catch (IllegalArgumentException e) {
                    errores.add("Línea " + numeroLinea + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            errores.add("No se pudo leer el archivo: " + e.getMessage());
        }
        return errores;
    }

    private static Producto convertirLinea(String linea) {
        String[] campos = linea.split("\\|", -1);

        if (campos.length != 6) {
            throw new IllegalArgumentException("se esperaban 6 campos y hay " + campos.length);
        }
        try {
            int id = Integer.parseInt(campos[0].trim());
            String nombre = campos[1].trim();
            String categoria = campos[2].trim();
            double precio = Double.parseDouble(campos[3].trim().replace(',', '.'));
            int stock = Integer.parseInt(campos[4].trim());
            
            // El constructor valida id, precio, stock, nombre y categoria.
            return new Producto(id, nombre, categoria, precio, stock);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("el id, el precio o el stock no es un número válido");
        }
    }
}