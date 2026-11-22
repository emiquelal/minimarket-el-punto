package modelo;

import java.time.LocalDate;

public class ProductoPerecedero extends Producto {
    private LocalDate fechaCaducidad;
    private boolean requiereRefrigeracion;

    public ProductoPerecedero(String codigo, String nombre, String categoria,
                              double precioCompra, double precioVenta,
                              int cantidadStock, int stockMinimo,
                              LocalDate fechaCaducidad, boolean requiereRefrigeracion) {
        super(codigo, nombre, categoria, precioCompra, precioVenta, cantidadStock, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    @Override
    public String mostrarDetalle() {
        String frio = requiereRefrigeracion ? "Con refrigeración" : "Sin refrigeración";
        return super.mostrarDetalle() + " | Caduca: " + fechaCaducidad + " | " + frio;
    }

    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isRequiereRefrigeracion() { return requiereRefrigeracion; }
    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }
}