package ar.edu.unahur.obj2.peluqueria.Categorias;

import ar.edu.unahur.obj2.peluqueria.Cliente;

/**
 * Categoria
 */
public interface Categoria {

    abstract public Double aplicarDescuento(double importe);

    default void validarHistorialDelCliente(Cliente unCliente){}
}
