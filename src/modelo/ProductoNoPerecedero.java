package modelo;

public class ProductoNoPerecedero extends Producto {
    private String pasilloUbicacion;
    private int mesesGarantia;

    public ProductoNoPerecedero(String codigo, String nombre, String categoria,
                                double precioCompra, double precioVenta,
                                int cantidadStock, int stockMinimo,
                                String pasilloUbicacion, int mesesGarantia) {
        super(codigo, nombre, categoria, precioCompra, precioVenta, cantidadStock, stockMinimo);
        setPasilloUbicacion(pasilloUbicacion);
        setMesesGarantia(mesesGarantia);
    }

    @Override
    public String mostrarDetalle() {
        return super.mostrarDetalle() + " | Ubicación: " + pasilloUbicacion +
                " | Garantía: " + mesesGarantia + " meses";
    }

    public String getPasilloUbicacion() { return pasilloUbicacion; }
    public void setPasilloUbicacion(String pasilloUbicacion) {
        if (pasilloUbicacion != null && !pasilloUbicacion.trim().isEmpty()) {
            this.pasilloUbicacion = pasilloUbicacion.trim();
        }
    }

    public int getMesesGarantia() { return mesesGarantia; }
    public void setMesesGarantia(int mesesGarantia) {
        if (mesesGarantia >= 0) {
            this.mesesGarantia = mesesGarantia;
        }
    }
}
