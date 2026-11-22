package negocio;

import modelo.Producto;
import modelo.ProductoPerecedero;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    private List<Producto> productos = new ArrayList<>();

    public boolean registrarProducto(Producto p) {
        if (p == null || buscarPorCodigo(p.getCodigo()) != null) {
            return false;
        }
        return productos.add(p);
    }

    public boolean eliminarProducto(String codigo) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) {
            return false;
        }
        return productos.remove(p);
    }

    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        if (nombre == null) return resultados;
        String filtro = nombre.trim().toLowerCase();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(filtro)) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    public void actualizarStock(String codigo, int cambio) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) {
            throw new IllegalArgumentException("No existe un producto con el código: " + codigo);
        }
        int nuevoStock = p.getCantidadStock() + cambio;
        if (nuevoStock < 0) {
            throw new IllegalArgumentException(
                    "Stock insuficiente. Disponible: " + p.getCantidadStock() + ", salida solicitada: " + Math.abs(cambio)
            );
        }
        p.setCantidadStock(nuevoStock);
    }

    public List<String> generarAlertas() {
        List<String> alertas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (Producto p : productos) {
            if (p.tieneStockBajo()) {
                alertas.add("STOCK BAJO: " + p.getNombre() + " (" + p.getCantidadStock() + "/" + p.getStockMinimo() + ")");
            }
            if (p instanceof ProductoPerecedero perecedero) {
                long dias = ChronoUnit.DAYS.between(hoy, perecedero.getFechaCaducidad());
                if (dias < 0) {
                    alertas.add("VENCIDO (" + Math.abs(dias) + " días): " + p.getNombre());
                } else if (dias <= 7) {
                    alertas.add("PRÓXIMO A VENCER (" + dias + " días): " + p.getNombre());
                }
            }
        }
        return alertas;
    }

    public double calcularValorInventario() {
        double total = 0;
        for (Producto p : productos) {
            total += p.calcularValorInventario();
        }
        return total;
    }

    public double calcularGananciaEstimada() {
        double ganancia = 0;
        for (Producto p : productos) {
            ganancia += p.calcularMargenUnitario() * p.getCantidadStock();
        }
        return ganancia;
    }

    public List<Producto> listarProductos() {
        return productos;
    }
}