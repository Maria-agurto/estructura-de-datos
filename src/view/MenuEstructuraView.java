package view;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Módulo 1 (Infraestructura, Red Eléctrica, Seguridad y Cuentas).
 * Casos de uso: CU01, CU02, CU03, CU12, CU13, CU14, CU15, CU16, CU17, CU18, CU19, CU20 y CU21.
 */
public class MenuEstructuraView extends JFrame {

    private final CardLayout cards = new CardLayout();
    private final JPanel contenido = new JPanel(cards);
    private final JLabel estado =
            new JLabel("Los botones y campos no modifican ningún dato.");

    public MenuEstructuraView() {
        super("Auditor Energético B2B - Infraestructura, Red Eléctrica y Cuentas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(940, 720);
        setLocationRelativeTo(null);

        setJMenuBar(crearBarraMenu());

        contenido.add(crearBienvenida(), "inicio");
        contenido.add(crearRegistro(), "registro");
        contenido.add(crearLogin(), "login");
        contenido.add(crearPerfil(), "perfil");
        contenido.add(crearUsuarios(), "usuarios");
        contenido.add(crearMapeo(), "mapeo");
        contenido.add(crearArbol(), "arbol");
        contenido.add(crearCarga(), "carga");
        contenido.add(crearAuditoriaRed(), "auditoria");

        estado.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        add(contenido, BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);
        cards.show(contenido, "inicio");
    }

    // ------------------------------------------------------------------
    // MENÚ
    // ------------------------------------------------------------------
    private JMenuBar crearBarraMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu seguridad = new JMenu("Seguridad y Cuentas");
        seguridad.add(item("Registrarse en el Sistema (CU12)", "registro"));
        seguridad.add(item("Iniciar Sesión y Recuperación (CU13, CU14)", "login"));
        seguridad.add(item("Gestionar Perfil y Datos de la Vivienda (CU15)", "perfil"));
        seguridad.addSeparator();
        seguridad.add(item("Gestionar Usuarios y Roles (CU16) - Administrador", "usuarios"));

        JMenu red = new JMenu("Red Eléctrica y Auditoría");
        red.add(item("Registrar Mapeo Jerárquico y Nodos (CU01, CU17, CU18)", "mapeo"));
        red.add(item("Visualizar Árbol Eléctrico (CU19)", "arbol"));
        red.add(item("Calcular Carga Total y Sobrecargas (CU02, CU03)", "carga"));
        red.add(item("Auditoría y Artefactos Vampiro (CU20, CU21)", "auditoria"));

        JMenu sistema = new JMenu("Sistema");
        JMenuItem cerrar = new JMenuItem("Cerrar módulo");
        cerrar.addActionListener(e -> dispose());
        sistema.add(cerrar);

        barra.add(seguridad);
        barra.add(red);
        barra.add(sistema);
        return barra;
    }

    private JMenuItem item(String texto, String tarjeta) {
        JMenuItem it = new JMenuItem(texto);
        it.addActionListener(e -> {
            cards.show(contenido, tarjeta);
            estado.setText("Vista de demostración: " + texto);
        });
        return it;
    }

    // ------------------------------------------------------------------
    // PANTALLAS
    // ------------------------------------------------------------------
    private JPanel crearBienvenida() {
        JPanel p = new JPanel(new GridBagLayout());
        p.add(new JLabel("<html><center><h2>Infraestructura, Red Eléctrica, Seguridad y Cuentas</h2>"
                + "Selecciona una opción del menú superior.</center></html>"));
        return p;
    }

    /** CU12 */
    private JPanel crearRegistro() {
        JPanel form = formulario(
                new String[]{"Nombre completo:", "Correo electrónico:", "Contraseña:",
                        "Confirmar contraseña:", "Rol asignado:"},
                new JComponent[]{campo("Ej. Juan Pérez"), campo("correo@domain.com"),
                        new JPasswordField(), new JPasswordField(),
                        new JComboBox<>(new String[]{"Cliente / Residencial", "Administrador B2B / Asesor"})});

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(ayuda("<b>¿Qué permite?</b> Registrar una nueva cuenta de usuario en el sistema ElectroTrack "
                + "asignando credenciales seguras y un rol específico para restringir funciones."), BorderLayout.NORTH);
        cuerpo.add(form, BorderLayout.CENTER);
        cuerpo.add(botones(boton("Registrar cuenta")), BorderLayout.SOUTH);
        return panelConTitulo("Registrarse en el Sistema (CU12)", cuerpo);
    }

    /** CU13, CU14 */
    private JPanel crearLogin() {
        JPanel formLogin = formulario(
                new String[]{"Correo electrónico:", "Contraseña:"},
                new JComponent[]{campo("correo@domain.com"), new JPasswordField()});

        JPanel panelLogin = new JPanel(new BorderLayout(0, 8));
        panelLogin.add(formLogin, BorderLayout.CENTER);
        panelLogin.add(botones(boton("Iniciar Sesión (CU13)"), boton("Recuperar Contraseña (CU14)")), BorderLayout.SOUTH);

        JPanel cuerpo = new JPanel(new BorderLayout(0, 12));
        cuerpo.add(ayuda("<b>Autenticación (CU13):</b> Valida las credenciales del usuario y habilita las opciones "
                + "correspondientes a su rol.<br><b>Recuperación (CU14):</b> Permite restablecer la contraseña en caso de olvido."), BorderLayout.NORTH);
        cuerpo.add(panelLogin, BorderLayout.CENTER);
        return panelConTitulo("Iniciar Sesión y Recuperación de Credenciales", cuerpo);
    }

    /** CU15 */
    private JPanel crearPerfil() {
        JPanel form = formulario(
                new String[]{"Nombres y Apellidos:", "Teléfono / Contacto:",
                        "Dirección de la Vivienda:", "Tipo de Suministro:", "Tarifa Aplicable Osinergmin:"},
                new JComponent[]{campo("Paolo Luyo"), campo("987654321"),
                        campo("Sector 6, Grupo 5, Villa El Salvador"),
                        new JComboBox<>(new String[]{"Monofásico BT5B", "Trifásico BT5A"}),
                        new JComboBox<>(new String[]{"Residencial (BT5B)", "General"})});

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(ayuda("Actualiza tus datos personales y la información técnica de tu vivienda para el cálculo tarifario."), BorderLayout.NORTH);
        cuerpo.add(form, BorderLayout.CENTER);
        cuerpo.add(botones(boton("Guardar cambios de perfil")), BorderLayout.SOUTH);
        return panelConTitulo("Gestionar Perfil y Datos de la Vivienda (CU15)", cuerpo);
    }

    /** CU16 */
    private JPanel crearUsuarios() {
        DefaultTableModel modelo = modelo(
                new String[]{"ID", "Usuario", "Correo", "Rol", "Estado"},
                new Object[][]{
                        {1, "Peter", "peter@unmsm.edu.pe", "Cliente", "Habilitado"},
                        {2, "Mizi", "mizi@unmsm.edu.pe", "Cliente", "Habilitado"},
                        {3, "Admin Tapia", "admin@electrotrack.pe", "Administrador B2B", "Habilitado"}});

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(ayuda("<b>Panel de Administrador:</b> Permite habilitar, deshabilitar y asignar roles a los usuarios registrados en el sistema."), BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        cuerpo.add(botones(boton("Habilitar / Deshabilitar seleccionado"), boton("Cambiar Rol")), BorderLayout.SOUTH);
        return panelConTitulo("Gestionar Usuarios y Roles - Administrador (CU16)", cuerpo);
    }

    /** CU01, CU17, CU18 */
    private JPanel crearMapeo() {
        JPanel form = formulario(
                new String[]{"Tipo de Nodo:", "Nombre del Elemento:", "Potencia (W) / Capacidad:", "Nodo Padre Asociado:"},
                new JComponent[]{
                        new JComboBox<>(new String[]{"Medidor Principal", "Tablero General", "Circuito / Llave Termomagnética", "Electrodoméstico (CU17)"}),
                        campo("Ej. Circuito Sala / Refrigeradora"),
                        campo("Ej. 2500 W (para llaves) o 250 W (artefactos)"),
                        new JComboBox<>(new String[]{"Medidor General [Raíz]", "Tablero 1", "Circuito 1: Tomacorrientes"})
                });

        JPanel acciones = botones(
                boton("Registrar Mapeo (CU01)"),
                boton("Registrar Artefacto (CU17)"),
                boton("Modificar Nodo (CU18)"),
                boton("Eliminar Nodo (CU18)"));

        JPanel arriba = new JPanel(new BorderLayout(0, 8));
        arriba.add(ayuda("<b>Estructura en Árbol N-ario (CU01):</b> Modela la red eléctrica desde el medidor hasta cada electrodoméstico.<br>"
                + "<b>CU17:</b> Registra potencia, antigüedad, horas de uso y tipo.<br>"
                + "<b>CU18:</b> Permite editar o eliminar nodos existentes recalculando la carga."), BorderLayout.NORTH);
        arriba.add(form, BorderLayout.CENTER);
        arriba.add(acciones, BorderLayout.SOUTH);

        DefaultTableModel modelo = modelo(
                new String[]{"ID Nodo", "Jerarquía", "Elemento", "Potencia / Capacidad", "Tipo"},
                new Object[][]{
                        {"N-01", "Raíz", "Medidor Principal", "10000 W", "Medidor"},
                        {"N-02", "Hijo de N-01", "Tablero General", "8000 W", "Tablero"},
                        {"N-03", "Hijo de N-02", "Circuito 1: Cocina y Terma", "4000 W", "Circuito"},
                        {"N-04", "Hijo de N-03", "Refrigeradora LG", "250 W", "Continuo"}});

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(arriba, BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        return panelConTitulo("Registrar Mapeo Jerárquico de Red (CU01, CU17, CU18)", cuerpo);
    }

    /** CU19 */
    private JPanel crearArbol() {
        String estructuraArbol =
                "[-] Medidor Principal (Capacidad máx: 10000W - 50A)\n"
                        + "    `-- [+] Tablero General (Capacidad: 8000W)\n"
                        + "          |-- [+] Circuito 1: Iluminación y Tomacorrientes (Capacidad: 3000W)\n"
                        + "          |     |-- Foco LED Sala (15W - Continuo)\n"
                        + "          |     `-- Televisor LED 55\" (120W - Vampiro)\n"
                        + "          `-- [+] Circuito 2: Cocina y Línea Blanca (Capacidad: 5000W)\n"
                        + "                |-- Refrigeradora No Frost (250W - Continuo)\n"
                        + "                `-- Terma Eléctrica 50L (1500W - Programable)\n";

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(ayuda("Representación visual jerárquica del Árbol N-ario de la red eléctrica de la vivienda."), BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(areaTexto(estructuraArbol)), BorderLayout.CENTER);
        cuerpo.add(botones(boton("Actualizar vista de árbol")), BorderLayout.SOUTH);
        return panelConTitulo("Visualizar Árbol Eléctrico (CU19)", cuerpo);
    }

    /** CU02, CU03 */
    private JPanel crearCarga() {
        String resultadoCarga =
                "Ejecutando recorrido recursivo DFS/BFS sobre el Árbol N-ario...\n\n"
                        + "--------------------------------------------------------\n"
                        + "RESUMEN DE CARGA Y AMPERAJE CONCURRENTE\n"
                        + "--------------------------------------------------------\n"
                        + " - Carga total conectada en la vivienda: 1,885.00 W\n"
                        + " - Amperaje total estimado: 8.57 A (a 220V)\n\n"
                        + "DIAGNÓSTICO POR CIRCUITO (CU03):\n"
                        + " [OK] Circuito 1 (Iluminación): 135.00W / Umbral: 3000W (Carga segura)\n"
                        + " [OK] Circuito 2 (Cocina/Línea Blanca): 1,750.00W / Umbral: 5000W (Carga segura)\n\n"
                        + "ESTADO GENERAL: La instalación soporta la carga concurrente actual.\n"
                        + "No se detectan sobrecargas en las llaves termomagnéticas.";

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(ayuda("<b>CU02 (Cálculo de Carga Total):</b> Recorre el árbol N-ario para sumar la potencia y amperaje.<br>"
                + "<b>CU03 (Detección de Sobrecarga):</b> Compara la carga concurrente frente al umbral térmico de cada interruptor termomagnético."), BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(areaTexto(resultadoCarga)), BorderLayout.CENTER);
        cuerpo.add(botones(boton("Ejecutar cálculo de carga y auditoría de sobrecarga")), BorderLayout.SOUTH);
        return panelConTitulo("Calcular Carga Total y Detectar Sobrecarga (CU02, CU03)", cuerpo);
    }

    /** CU20, CU21 */
    private JPanel crearAuditoriaRed() {
        DefaultTableModel modelo = modelo(
                new String[]{"Artefacto", "Tipo", "Potencia (W)", "Horas/Día", "Consumo Mes (kWh)", "Costo S/", "Estado"},
                new Object[][]{
                        {"Terma Eléctrica", "Programable", 1500.0, 2.0, 90.00, "S/ 58.50", "Alto Consumo"},
                        {"Refrigeradora", "Continuo", 250.0, 24.0, 180.00, "S/ 117.00", "Normal"},
                        {"Decodificador TV", "Vampiro", 15.0, 24.0, 10.80, "S/ 7.02", "Consumo Anómalo (Vampiro)"},
                        {"Televisor LED", "Vampiro", 120.0, 4.0, 14.40, "S/ 9.36", "Normal"}});

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(ayuda("<b>CU20 (Auditoría por Artefacto):</b> Calcula el consumo y costo mensual generando un ranking.<br>"
                + "<b>CU21 (Identificación de Vampiros/Ineficientes):</b> Detecta aparatos con consumo en modo espera o anómalo y los marca como candidatos a reemplazo."), BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        cuerpo.add(botones(boton("Generar ranking de consumo"), boton("Marcar candidatos a reemplazo")), BorderLayout.SOUTH);
        return panelConTitulo("Auditoría de Consumo e Identificación de Ineficientes (CU20, CU21)", cuerpo);
    }

    // ------------------------------------------------------------------
    // UTILIDADES DE DIBUJO
    // ------------------------------------------------------------------
    private JButton boton(String texto) {
        JButton b = new JButton(texto);
        b.addActionListener(e ->
                estado.setText("Vista de demostración: «" + texto + "» no modifica ningún dato."));
        return b;
    }

    private static JPanel botones(JComponent... componentes) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JComponent c : componentes) p.add(c);
        return p;
    }

    private static JTextField campo(String placeholder) {
        JTextField t = new JTextField();
        t.putClientProperty("JTextField.placeholderText", placeholder);
        return t;
    }

    private static JTextArea areaTexto(String texto) {
        JTextArea a = new JTextArea(texto);
        a.setEditable(false);
        a.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        a.setCaretPosition(0);
        return a;
    }

    private static DefaultTableModel modelo(String[] columnas, Object[][] filas) {
        return new DefaultTableModel(filas, columnas) {
            @Override public boolean isCellEditable(int fila, int columna) { return false; }
        };
    }

    private static JPanel panelConTitulo(String titulo, JComponent cuerpo) {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JLabel t = new JLabel(titulo);
        t.setFont(t.getFont().deriveFont(Font.BOLD, 18f));
        p.add(t, BorderLayout.NORTH);
        p.add(cuerpo, BorderLayout.CENTER);
        return p;
    }

    private static JPanel formulario(String[] etiquetas, JComponent[] campos) {
        JPanel grid = new JPanel(new GridLayout(etiquetas.length, 2, 8, 8));
        for (int i = 0; i < etiquetas.length; i++) {
            grid.add(new JLabel(etiquetas[i]));
            grid.add(campos[i]);
        }
        grid.setPreferredSize(new Dimension(640, etiquetas.length * 36));
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izquierda.add(grid);
        return izquierda;
    }

    private static JLabel ayuda(String html) {
        JLabel l = new JLabel("<html><body style='width: 780px'>" + html + "</body></html>");
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return l;
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> new MenuEstructuraView().setVisible(true));
    }
}
