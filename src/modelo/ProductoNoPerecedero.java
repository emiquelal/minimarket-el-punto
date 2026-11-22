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
    public String verificarEstado() {
        if (tieneStockBajo()) {
            return "STOCK BAJO";
        }
        return "NORMAL";
    }

    public String getPasilloUbicacion() { return pasilloUbicacion; }
    public void setPasilloUbicacion(String pasilloUbicacion) {
        if (pasilloUbicacion == null || pasilloUbicacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía.");
        }
        this.pasilloUbicacion = pasilloUbicacion.trim();
    }

    public int getMesesGarantia() { return mesesGarantia; }
    public void setMesesGarantia(int mesesGarantia) {
        if (mesesGarantia < 0) {
            throw new IllegalArgumentException("Los meses de garantía no pueden ser negativos.");
        }
        this.mesesGarantia = mesesGarantia;
    }
}
