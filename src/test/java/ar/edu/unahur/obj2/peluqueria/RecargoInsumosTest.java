package ar.edu.unahur.obj2.peluqueria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.peluqueria.Servicios.RecargoInsumos;

class RecargoInsumosTest {

    private static final double DELTA = 0.001;

    @BeforeEach
    void reiniciar() {
        RecargoInsumos.getInstancia().setValor(0);
    }

    @Test
    void dadoElSingleton_cuandoPidoLaInstanciaDosVeces_entoncesEsLaMismaReferencia() {
        RecargoInsumos insumo1 = RecargoInsumos.getInstancia();
        RecargoInsumos insumo2 = RecargoInsumos.getInstancia();
        assertSame(insumo1, insumo2);
    }

    @Test
    void dadoUnNuevoValor_cuandoLoActualizo_entoncesCualquierReferenciaLoVe() {
        RecargoInsumos insumo = RecargoInsumos.getInstancia();
        insumo.setValor(250);
        assertEquals(250, RecargoInsumos.getInstancia().getValor(), DELTA);
    }

    @Test
    void dadoRecargoRecienReiniciado_cuandoConsultoElValor_entoncesEsCero() {
        double insumo = RecargoInsumos.getInstancia().getValor();
        assertEquals(0, insumo, DELTA);
    }
}
