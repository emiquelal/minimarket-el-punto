package modelo;

public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int cantidadStock;
    private int stockMinimo;

    public Producto(String codigo, String nombre, String categoria,
                    double precioCompra, double precioVenta,
                    int cantidadStock, int stockMinimo) {
        setCodigo(codigo);
        setNombre(nombre);
        setCategoria(categoria);
        setPrecioCompra(precioCompra);
        setPrecioVenta(precioVenta);
        setCantidadStock(cantidadStock);
        setStockMinimo(stockMinimo);
    }

    public String mostrarDetalle() {
        return codigo + " | " + nombre + " (" + categoria + ") | Stock: " + cantidadStock +
                " | Compra: $" + precioCompra + " | Venta: $" + precioVenta;
    }

    public double calcularCapital() {
        return precioCompra * cantidadStock;
    }

    public double calcularMargen() {
        return precioVenta - precioCompra;
    }

    public boolean stockBajo() {
        return cantidadStock <= stockMinimo;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()) {
            this.codigo = codigo.trim();
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.trim().isEmpty()) {
            this.categoria = categoria.trim();
        }
    }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) {
        if (precioCompra >= 0) {
            this.precioCompra = precioCompra;
        }
    }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) {
        if (precioVenta >= 0) {
            this.precioVenta = precioVenta;
        }
    }

    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) {
        if (cantidadStock >= 0) {
            this.cantidadStock = cantidadStock;
        }
    }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo >= 0) {
            this.stockMinimo = stockMinimo;
        }
    }
}