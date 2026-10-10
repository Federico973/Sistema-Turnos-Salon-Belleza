package ar.edu.unahur.obj2.peluqueria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.peluqueria.Categorias.Frecuente;
import ar.edu.unahur.obj2.peluqueria.Categorias.Ocasional;
import ar.edu.unahur.obj2.peluqueria.Categorias.Vip;
import ar.edu.unahur.obj2.peluqueria.Exepciones.CategoriaNoAutorizadaException;
import ar.edu.unahur.obj2.peluqueria.Servicios.CorteDeCabello;

class CategoriaTest {

    private static final double DELTA = 0.001;

    @Test
    void dadaCategoriaOcasional_cuandoAplicoDescuento_entoncesElImporteNoCambia() {
        Ocasional categoria = new Ocasional();
        double resultado = categoria.aplicarDescuento(1000);
        assertEquals(1000, resultado, DELTA);
    }

    @Test
    void dadaCategoriaFrecuente_cuandoAplicoDescuento_entoncesDevuelveElImporteConElDiezPorCientoMenos() {
        Frecuente categoria = new Frecuente();
        double resultado = categoria.aplicarDescuento(1000);
        assertEquals(900, resultado, DELTA);
    }

    @Test
    void dadaCategoriaVip_cuandoAplicoDescuento_entoncesDevuelveElImporteConElVeintePorCientoMenos() {
        Vip categoria = new Vip();
        double resultado = categoria.aplicarDescuento(1000);
        assertEquals(800, resultado, DELTA);
    }

    @Test
    void dadaCategoriaVipYClienteSinTurnos_cuandoValidoElHistorial_entoncesLanzaCategoriaNoAutorizadaException() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        assertThrows(CategoriaNoAutorizadaException.class, () -> new Vip().validarHistorialDelCliente(cliente));
    }

    @Test
    void dadaCategoriaVipYClienteConTurnos_cuandoValidoElHistorial_entoncesNoLanzaExcepcion() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        new CorteDeCabello(1000, 5).confirmar(cliente, 1);
        assertDoesNotThrow(() -> new Vip().validarHistorialDelCliente(cliente));
    }

    @Test
    void dadasCategoriasOcasionalYFrecuente_cuandoValidoElHistorialDeUnClienteSinTurnos_entoncesNoLanzanExcepcion() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        assertDoesNotThrow(() -> new Ocasional().validarHistorialDelCliente(cliente));
        assertDoesNotThrow(() -> new Frecuente().validarHistorialDelCliente(cliente));
    }
}
