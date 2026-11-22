package interfaz;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import modelo.Producto;
import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;
import negocio.GestorInventario;

/**
 * Interfaz de consola del Minimarket "El Punto".
 *
 * Muestra el menú, lee los datos del usuario y los valida antes de enviarlos
 * a GestorInventario. No contiene reglas del negocio: solo captura, valida
 * y muestra resultados. Ningún error de digitación (campo vacío, texto en un
 * campo numérico, número negativo, fecha inválida) debe cerrar el programa.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final GestorInventario gestor = new GestorInventario();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción", 0, 5);
            switch (opcion) {
                case 1 -> registrarPerecedero();
                case 2 -> registrarNoPerecedero();
                case 3 -> listarInventario();
                case 4 -> actualizarStock();
                case 5 -> verAlertas();
                case 0 -> System.out.println("Hasta pronto.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MINIMARKET \"EL PUNTO\" - INVENTARIO =====");
        System.out.println("1. Registrar producto perecedero");
        System.out.println("2. Registrar producto no perecedero");
        System.out.println("3. Listar inventario");
        System.out.println("4. Actualizar stock (entrada o salida)");
        System.out.println("5. Ver alertas");
        System.out.println("0. Salir");
    }

    // ---------------- Opciones del menú ----------------

    /** RF1: registra un producto perecedero. */
    private static void registrarPerecedero() {
        System.out.println("\n--- Registrar producto perecedero ---");
        String codigo = leerCodigoNuevo();
        String nombre = leerTexto("Nombre");
        String categoria = leerTexto("Categoría");
        double precioCompra = leerDecimal("Precio de compra");
        double precioVenta = leerDecimal("Precio de venta");
        int cantidad = leerEntero("Cantidad", 0, 1_000_000);
        int stockMinimo = leerEntero("Stock mínimo", 0, 1_000_000);
        LocalDate caducidad = leerFecha("Fecha de caducidad (AAAA-MM-DD)");
        boolean frio = leerSiNo("¿Requiere refrigeración? (s/n)");

        Producto p = new ProductoPerecedero(codigo, nombre, categoria, precioCompra,
                precioVenta, cantidad, stockMinimo, caducidad, frio);
        confirmarRegistro(p);
    }

    /** RF1: registra un producto no perecedero. */
    private static void registrarNoPerecedero() {
        System.out.println("\n--- Registrar producto no perecedero ---");
        String codigo = leerCodigoNuevo();
        String nombre = leerTexto("Nombre");
        String categoria = leerTexto("Categoría");
        double precioCompra = leerDecimal("Precio de compra");
        double precioVenta = leerDecimal("Precio de venta");
        int cantidad = leerEntero("Cantidad", 0, 1_000_000);
        int stockMinimo = leerEntero("Stock mínimo", 0, 1_000_000);
        String pasillo = leerTexto("Pasillo / ubicación");
        int garantia = leerEntero("Meses de garantía (0 si no aplica)", 0, 120);

        Producto p = new ProductoNoPerecedero(codigo, nombre, categoria, precioCompra,
                precioVenta, cantidad, stockMinimo, pasillo, garantia);
        confirmarRegistro(p);
    }

    private static void confirmarRegistro(Producto p) {
        if (gestor.registrarProducto(p)) {
            System.out.println("Producto registrado correctamente.");
        } else {
            System.out.println("Error: no se pudo registrar (código repetido).");
        }
    }

    /** RF4: lista el inventario y muestra el valor total invertido. */
    private static void listarInventario() {
        List<Producto> lista = gestor.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("\n--- Inventario ---");
        for (Producto p : lista) {
            String extra = "";
            if (p instanceof ProductoPerecedero perecedero) {
                String frio = perecedero.isRequiereRefrigeracion() ? "Con refrigeración" : "Sin refrigeración";
                extra = " | Caduca: " + perecedero.getFechaCaducidad() + " | " + frio;
            } else if (p instanceof ProductoNoPerecedero noPerecedero) {
                extra = " | Ubicación: " + noPerecedero.getPasilloUbicacion()
                        + " | Garantía: " + noPerecedero.getMesesGarantia() + " meses";
            }

            System.out.println("[" + p.getCodigo() + "] " + p.getNombre()
                    + " (" + p.getCategoria() + ") | Stock: " + p.getCantidadStock()
                    + " | Compra: $" + String.format(Locale.US, "%.2f", p.getPrecioCompra())
                    + " | Venta: $" + String.format(Locale.US, "%.2f", p.getPrecioVenta())
                    + extra
                    + " | Estado: " + p.verificarEstado()); // polimorfismo
        }
        System.out.println(String.format(Locale.US,
                "\nValor total del inventario: $%.2f", gestor.calcularValorInventario()));
    }

    /** RF2: entrada (cantidad positiva) o salida (cantidad negativa) de stock. */
    private static void actualizarStock() {
        System.out.println("\n--- Actualizar stock ---");
        String codigo = leerTexto("Código del producto");
        Producto p = gestor.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("Error: no existe un producto con el código " + codigo + ".");
            return;
        }
        System.out.println("Producto: " + p.getNombre() + " | Stock actual: " + p.getCantidadStock());
        int cantidad;
        do {
            cantidad = leerEntero("Cantidad (positiva = entrada, negativa = salida)",
                    -1_000_000, 1_000_000);
            if (cantidad == 0) {
                System.out.println("Error: la cantidad no puede ser 0.");
            }
        } while (cantidad == 0);

        try {
            gestor.actualizarStock(codigo, cantidad);
            System.out.println("Stock actualizado. Nuevo stock: " + p.getCantidadStock());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + " El stock se mantiene en "
                    + p.getCantidadStock() + ".");
        }
    }

    /** RF3: muestra las alertas de caducidad y de stock bajo. */
    private static void verAlertas() {
        List<String> alertas = gestor.generarAlertas();
        if (alertas.isEmpty()) {
            System.out.println("Sin alertas: todos los productos están en estado OK.");
            return;
        }
        System.out.println("\n--- Alertas ---");
        for (String a : alertas) {
            System.out.println("- " + a);
        }
    }

    // ---------------- Lectura con validación ----------------

    /** Texto obligatorio (rechaza vacío). */
    private static String leerTexto(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String t = sc.nextLine().trim();
            if (!t.isEmpty()) {
                return t;
            }
            System.out.println("Error: el campo \"" + campo + "\" es obligatorio.");
        }
    }

    /** Código obligatorio que todavía no exista en el inventario. */
    private static String leerCodigoNuevo() {
        while (true) {
            String codigo = leerTexto("Código");
            if (gestor.buscarPorCodigo(codigo) == null) {
                return codigo;
            }
            System.out.println("Error: ya existe un producto con el código " + codigo + ".");
        }
    }

    /** Entero entre min y max (rechaza vacío, texto, decimales y fuera de rango). */
    private static int leerEntero(String campo, int min, int max) {
        while (true) {
            System.out.print(campo + ": ");
            String t = sc.nextLine().trim();
            if (t.isEmpty()) {
                System.out.println("Error: el campo \"" + campo + "\" es obligatorio.");
                continue;
            }
            try {
                int n = Integer.parseInt(t);
                if (n < min || n > max) {
                    System.out.println("Error: \"" + campo + "\" debe estar entre " + min + " y " + max + ".");
                } else {
                    return n;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + campo + "\" debe ser un número entero.");
            }
        }
    }

    /** Decimal mayor o igual a 0 (acepta punto o coma; rechaza vacío, texto y negativos). */
    private static double leerDecimal(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String t = sc.nextLine().trim().replace(',', '.');
            if (t.isEmpty()) {
                System.out.println("Error: el campo \"" + campo + "\" es obligatorio.");
                continue;
            }
            try {
                double d = Double.parseDouble(t);
                if (Double.isNaN(d) || Double.isInfinite(d)) {
                    System.out.println("Error: \"" + campo + "\" debe ser un número válido.");
                } else if (d < 0) {
                    System.out.println("Error: \"" + campo + "\" no puede ser negativo.");
                } else {
                    return d;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + campo + "\" debe ser numérico (ej. 0.50).");
            }
        }
    }

    /** Fecha en formato AAAA-MM-DD. */
    private static LocalDate leerFecha(String campo) {
        while (true) {
            String t = leerTexto(campo);
            try {
                return LocalDate.parse(t);
            } catch (DateTimeParseException e) {
                System.out.println("Error: fecha inválida. Use el formato AAAA-MM-DD (ej. 2026-12-05).");
            }
        }
    }

    /** Responde s/n. */
    private static boolean leerSiNo(String campo) {
        while (true) {
            String t = leerTexto(campo).toLowerCase();
            if (t.equals("s") || t.equals("si") || t.equals("sí")) {
                return true;
            }
            if (t.equals("n") || t.equals("no")) {
                return false;
            }
            System.out.println("Error: responda con 's' o 'n'.");
        }
    }
}