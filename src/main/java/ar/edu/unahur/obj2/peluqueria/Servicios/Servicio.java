package ar.edu.unahur.obj2.peluqueria.Servicios;

import ar.edu.unahur.obj2.peluqueria.Cliente;
import ar.edu.unahur.obj2.peluqueria.Turno;
import ar.edu.unahur.obj2.peluqueria.Exepciones.CantidadInvalidaException;
import ar.edu.unahur.obj2.peluqueria.Exepciones.PrecioNegativoException;

public abstract class Servicio {

    private final double precioBase;
    private int cuposDisponibles;
    private int turnosConfirmados;

    public Servicio(double precioBase, int cuposDisponibles) {
        if (precioBase < 0) {
            throw new PrecioNegativoException("El precio base no puede ser negativo: " + precioBase);
        }
        this.precioBase = precioBase;
        this.cuposDisponibles = cuposDisponibles;
    }

    // Template Method: el algoritmo es fijo, las subclases solo rellenan los pasos variables
    public final String confirmar(Cliente cliente, Integer sesiones) {
        validarSesiones(sesiones);
        Turno turno = new Turno(cliente, this, sesiones, calcularImporte(cliente, sesiones), sesiones);
        cliente.agregarTurno(turno);
        cuposDisponibles -= sesiones;
        turnosConfirmados++;
        accionPosterior();
        return generarNumeroDeOrden() + turnosConfirmados;
    }

    private void validarSesiones(int sesiones) {
        if (sesiones <= 0) {
            throw new CantidadInvalidaException("La cantidad de sesiones debe ser mayor a cero: " + sesiones);
        }
    }

    private double calcularImporte(Cliente cliente, int sesiones) {
        double subtotal = precioBase * sesiones;
        return cliente.getCategoria().aplicarDescuento(subtotal)
                + RecargoInsumos.getInstancia().getValor();
    }

    protected abstract String generarNumeroDeOrden();

    protected String accionPosterior() {
        return null;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public int getTurnosConfirmados() {
        return turnosConfirmados;
    }
}