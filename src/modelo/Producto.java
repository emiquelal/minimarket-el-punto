package modelo;

public abstract class Producto {
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

    public abstract String verificarEstado();

    public double calcularValorInventario() {
        return precioCompra * cantidadStock;
    }

    public double calcularMargenUnitario() {
        return precioVenta - precioCompra;
    }

    public boolean tieneStockBajo() {
        return cantidadStock <= stockMinimo;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }
        this.categoria = categoria.trim();
    }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) {
        if (precioCompra < 0) {
            throw new IllegalArgumentException("El precio de compra no puede ser negativo.");
        }
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) {
        if (precioVenta < 0) {
            throw new IllegalArgumentException("El precio de venta no puede ser negativo.");
        }
        this.precioVenta = precioVenta;
    }

    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) {
        if (cantidadStock < 0) {
            throw new IllegalArgumentException("La cantidad en stock no puede ser negativa.");
        }
        this.cantidadStock = cantidadStock;
    }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo.");
        }
        this.stockMinimo = stockMinimo;
    }
}