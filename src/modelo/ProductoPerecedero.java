package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProductoPerecedero extends Producto {
    private LocalDate fechaCaducidad;
    private boolean requiereRefrigeracion;

    public ProductoPerecedero(String codigo, String nombre, String categoria,
                              double precioCompra, double precioVenta,
                              int cantidadStock, int stockMinimo,
                              LocalDate fechaCaducidad, boolean requiereRefrigeracion) {
        super(codigo, nombre, categoria, precioCompra, precioVenta, cantidadStock, stockMinimo);
        setFechaCaducidad(fechaCaducidad);
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    @Override
    public String verificarEstado() {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), fechaCaducidad);
        if (dias < 0) {
            return "VENCIDO (" + Math.abs(dias) + " días)";
        }
        if (dias <= 7) {
            return "PRÓXIMO A VENCER (" + dias + " días)";
        }
        if (tieneStockBajo()) {
            return "STOCK BAJO";
        }
        return "NORMAL";
    }

    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        if (fechaCaducidad == null) {
            throw new IllegalArgumentException("La fecha de caducidad es obligatoria.");
        }
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isRequiereRefrigeracion() { return requiereRefrigeracion; }
    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }
}