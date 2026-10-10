package ar.edu.unahur.obj2.peluqueria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.peluqueria.Categorias.Frecuente;
import ar.edu.unahur.obj2.peluqueria.Categorias.Ocasional;
import ar.edu.unahur.obj2.peluqueria.Categorias.Vip;
import ar.edu.unahur.obj2.peluqueria.Exepciones.CantidadInvalidaException;
import ar.edu.unahur.obj2.peluqueria.Exepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.peluqueria.Servicios.Coloracion;
import ar.edu.unahur.obj2.peluqueria.Servicios.CorteDeCabello;
import ar.edu.unahur.obj2.peluqueria.Servicios.RecargoInsumos;
import ar.edu.unahur.obj2.peluqueria.Servicios.TratamientoCapilar;

class ServicioTest {

    private static final double DELTA = 0.001;

    @BeforeEach
    void reiniciarRecargo() {
        RecargoInsumos.getInstancia().setValor(0);
    }

    @Test
    void dadoPrecioNegativo_cuandoCreoServicio_entoncesLanzaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class, () -> new CorteDeCabello(-1, 5));
    }


    @Test
    void dadoPrecioCero_cuandoCreoServicio_entoncesSeCreaCorrectamente() {
        CorteDeCabello corte = new CorteDeCabello(0, 5);
        assertEquals(0, corte.getPrecioBase(), DELTA);
    }

    @Test
    void dadaCantidadCero_cuandoConfirmoTurno_entoncesLanzaCantidadInvalidaException() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        assertThrows(CantidadInvalidaException.class, () -> corte.confirmar(cliente, 0));
    }

    @Test
    void dadaCantidadNegativa_cuandoConfirmoTurno_entoncesLanzaCantidadInvalidaException() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        assertThrows(CantidadInvalidaException.class, () -> corte.confirmar(cliente, -2));
    }

    @Test
    void dadaCantidadInvalida_cuandoConfirmoTurno_entoncesNoSeDescuentanCuposNiSeAsientaTurno() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        assertThrows(CantidadInvalidaException.class, () -> corte.confirmar(cliente, 0));
        assertEquals(5, corte.getCuposDisponibles());
        assertEquals(0, cliente.cantidadTurnos());
        assertEquals(0, corte.getTurnosConfirmados());
    }

    @Test
    void dadoClienteOcasional_cuandoConfirmoTurno_entoncesNoHayDescuento() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        corte.confirmar(cliente, 2);
        assertEquals(2000, cliente.costoTotal(), DELTA);
    }

    @Test
    void dadoClienteFrecuente_cuandoConfirmoTurno_entoncesAplicaDescuentoDel10() {
        Cliente cliente = new Cliente("Ana", new Frecuente());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        corte.confirmar(cliente, 2);
        assertEquals(1800, cliente.costoTotal(), DELTA);
    }

    @Test
    void dadoClienteVip_cuandoConfirmoTurno_entoncesAplicaDescuentoDel20() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 10);
        corte.confirmar(cliente, 1); // ahora tiene historial: puede ser VIP
        cliente.setCategoria(new Vip());
        corte.confirmar(cliente, 2);
        assertEquals(2600, cliente.costoTotal(), DELTA);
    }

    @Test
    void dadoRecargoDe100_cuandoConfirmoTurno_entoncesSeSumaAlImporte() {
        RecargoInsumos.getInstancia().setValor(100);
        Cliente cliente = new Cliente("Ana", new Frecuente());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        corte.confirmar(cliente, 2);
        assertEquals(1900, cliente.costoTotal(), DELTA);
    }

    @Test
    void dadoRecargoActualizado_cuandoConfirmoTurnoPosterior_entoncesUsaElNuevoValor() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        RecargoInsumos.getInstancia().setValor(100);
        corte.confirmar(cliente, 1); // 1100
        RecargoInsumos.getInstancia().setValor(300);
        corte.confirmar(cliente, 1); // 1300
        assertEquals(2400, cliente.costoTotal(), DELTA);
    }

    @Test
    void dadoRecargoActualizado_cuandoConfirmoTurnoPosterior_entoncesElTurnoAnteriorNoCambia() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        RecargoInsumos.getInstancia().setValor(100);
        corte.confirmar(cliente, 1);
        RecargoInsumos.getInstancia().setValor(500);
        assertEquals(1100, cliente.getHistorial().get(0).getImporteTotal(), DELTA);
    }

    @Test
    void dadoElMismoRecargo_cuandoConfirmoTurnosEnDistintosServicios_entoncesAplicaATodos() {
        RecargoInsumos.getInstancia().setValor(50);
        Cliente cliente = new Cliente("Ana", new Ocasional());
        new CorteDeCabello(1000, 5).confirmar(cliente, 1);     
        new Coloracion(3000, 5).confirmar(cliente, 1);          
        new TratamientoCapilar(2000, 5).confirmar(cliente, 1); 
        assertEquals(6150, cliente.costoTotal(), DELTA);
    }

    @Test
    void dadoServicioCon10Cupos_cuandoConfirmoTurnoDe3Sesiones_entoncesQuedan7Cupos() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        TratamientoCapilar tratamiento = new TratamientoCapilar(2000, 10);
        tratamiento.confirmar(cliente, 3);
        assertEquals(7, tratamiento.getCuposDisponibles());
    }

    @Test
    void dadoCorte_cuandoConfirmoPrimerTurno_entoncesComprobanteEmpiezaConTRNCOR() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        String comprobante = corte.confirmar(cliente, 1);
        assertTrue(comprobante.startsWith("TRN-COR-"));
    }

    @Test
    void dadoCorte_cuandoConfirmoDosTurnos_entoncesLosComprobantesSonDistintos() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        CorteDeCabello corte = new CorteDeCabello(1000, 5);
        String primero = corte.confirmar(cliente, 1);
        String segundo = corte.confirmar(cliente, 1);
        assertNotEquals(primero, segundo);
    }

    @Test
    void dadaColoracion_cuandoConfirmoTurno_entoncesComprobanteEmpiezaConTRNCOL() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        Coloracion coloracion = new Coloracion(3000, 5);
        String comprobante = coloracion.confirmar(cliente, 1);
        assertTrue(comprobante.startsWith("TRN-COL-"));
    }

    @Test
    void dadoTratamiento_cuandoConfirmoTurno_entoncesComprobanteEmpiezaConTRNTRA() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        TratamientoCapilar tratamiento = new TratamientoCapilar(2000, 5);
        String comprobante = tratamiento.confirmar(cliente, 1);
        assertTrue(comprobante.startsWith("TRN-TRA-"));
    }

    @Test
    void dadoTurnoConfirmado_cuandoConsultoElHistorial_entoncesRegistraClienteServicioSesionesEImporte() {
        Cliente cliente = new Cliente("Ana", new Ocasional());
        TratamientoCapilar tratamiento = new TratamientoCapilar(2000, 5);
        tratamiento.confirmar(cliente, 3);
        Turno turno = cliente.getHistorial().get(0);
        assertSame(cliente, turno.getCliente());
        assertSame(tratamiento, turno.getServicio());
        assertEquals(3, turno.getCantSesiones());
        assertEquals(6000, turno.getImporteTotal(), DELTA);
    }

}
