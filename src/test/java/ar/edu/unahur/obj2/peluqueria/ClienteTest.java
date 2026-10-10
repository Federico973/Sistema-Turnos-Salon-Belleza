package ar.edu.unahur.obj2.peluqueria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.peluqueria.Categorias.Categoria;
import ar.edu.unahur.obj2.peluqueria.Categorias.Frecuente;
import ar.edu.unahur.obj2.peluqueria.Categorias.Ocasional;
import ar.edu.unahur.obj2.peluqueria.Categorias.Vip;
import ar.edu.unahur.obj2.peluqueria.Exepciones.CategoriaNoAutorizadaException;
import ar.edu.unahur.obj2.peluqueria.Exepciones.ClienteSinTurnosException;
import ar.edu.unahur.obj2.peluqueria.Servicios.CorteDeCabello;
import ar.edu.unahur.obj2.peluqueria.Servicios.RecargoInsumos;

class ClienteTest {

    private static final double DELTA = 0.001;

    @BeforeEach
    void reiniciarRecargo() {
        RecargoInsumos.getInstancia().setValor(0);
    }

    @Test
    void dadoClienteSinTurnos_cuandoPideHistorial_entoncesLanzaClienteSinTurnosException() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        assertThrows(ClienteSinTurnosException.class, cliente::getHistorial);
    }

    @Test
    void dadoClienteConTurno_cuandoPideHistorial_entoncesDevuelveSusTurnos() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        new CorteDeCabello(1000, 5).confirmar(cliente, 1);
        int cantidad = cliente.getHistorial().size();
        assertEquals(1, cantidad);
    }

    @Test
    void dadoClienteSinTurnos_cuandoSeLeAsignaVip_entoncesLanzaCategoriaNoAutorizadaException() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        assertThrows(CategoriaNoAutorizadaException.class, () -> cliente.setCategoria(new Vip()));
    }

    @Test
    void dadoClienteSinTurnos_cuandoFallaLaAsignacionDeVip_entoncesConservaSuCategoriaAnterior() {
        Categoria ocasional = new Ocasional();
        Cliente cliente = new Cliente("Ana", ocasional);
        assertThrows(CategoriaNoAutorizadaException.class, () -> cliente.setCategoria(new Vip()));
        assertSame(ocasional, cliente.getCategoria());
    }

    @Test
    void dadoClienteNuevo_cuandoSeCreaComoVip_entoncesLanzaCategoriaNoAutorizadaException() {
        assertThrows(CategoriaNoAutorizadaException.class, () -> new Cliente("Ana", new Vip()));
    }

    @Test
    void dadoClienteConTurnos_cuandoSeLeAsignaVip_entoncesLaCategoriaCambia() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        new CorteDeCabello(1000, 5).confirmar(cliente, 1);
        Categoria vip = new Vip();
        cliente.setCategoria(vip);
        assertSame(vip, cliente.getCategoria());
    }

    @Test
    void dadoClienteConHistorial_cuandoSeRecategoriza_entoncesConservaElHistorial() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        new CorteDeCabello(1000, 5).confirmar(cliente, 1);
        cliente.setCategoria(new Frecuente());
        assertEquals(1, cliente.cantidadTurnos());
        assertEquals("Ana", cliente.getNombre());
    }

    @Test
    void dadoClienteSinTurnos_cuandoConsultaCostoTotal_entoncesEsCero() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        double total = cliente.costoTotal();
        assertEquals(0, total, DELTA);
    }

    @Test
    void dadoClienteConVariosTurnos_cuandoConsultaCostoTotal_entoncesSumaLosImportes() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 10);
        corte.confirmar(cliente, 1); // 1000
        corte.confirmar(cliente, 2); // 2000
        double total = cliente.costoTotal();
        assertEquals(3000, total, DELTA);
    }

    @Test
    void dadoHistorial_cuandoIntentoModificarloDesdeAfuera_entoncesLanzaUnsupportedOperationException() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        new CorteDeCabello(1000, 5).confirmar(cliente, 1);
        assertThrows(UnsupportedOperationException.class, () -> cliente.getHistorial().clear());
    }
}
