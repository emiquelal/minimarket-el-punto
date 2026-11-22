package negocio;

import modelo.Producto;
import modelo.ProductoPerecedero;

import java.time.LocalDate;
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

    public boolean actualizarStock(String codigo, int cambio) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) return false;
        int nuevoStock = p.getCantidadStock() + cambio;
        if (nuevoStock < 0) return false;
        p.setCantidadStock(nuevoStock);
        return true;
    }

    public List<String> generarAlertas(LocalDate fechaActual) {
        List<String> alertas = new ArrayList<>();
        LocalDate fechaLimite = fechaActual.plusDays(3);

        for (Producto p : productos) {
            if (p.stockBajo()) {
                alertas.add("STOCK BAJO: " + p.getNombre() + " [" + p.getCodigo() + "] -> "
                        + p.getCantidadStock() + " / mín " + p.getStockMinimo());
            }
            if (p instanceof ProductoPerecedero perecedero) {
                LocalDate caduca = perecedero.getFechaCaducidad();
                if (caduca != null) {
                    if (caduca.isBefore(fechaActual)) {
                        alertas.add("VENCIDO: " + p.getNombre() + " [" + p.getCodigo() + "] -> " + caduca);
                    } else if (!caduca.isAfter(fechaLimite)) {
                        alertas.add("POR VENCER: " + p.getNombre() + " [" + p.getCodigo() + "] -> " + caduca);
                    }
                }
            }
        }
        return alertas;
    }

    public double calcularCapitalTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.calcularCapital();
        }
        return total;
    }

    public double calcularGananciaEstimada() {
        double ganancia = 0;
        for (Producto p : productos) {
            ganancia += p.calcularMargen() * p.getCantidadStock();
        }
        return ganancia;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}