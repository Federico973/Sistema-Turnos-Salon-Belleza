package ar.edu.unahur.obj2.peluqueria;

import ar.edu.unahur.obj2.peluqueria.Servicios.Servicio;

/**
 * Turno
 */
public class Turno {

    private Cliente cliente;
    private Servicio servicio;
    private Integer cantSesiones;
    private Double importeTotal;

    public Turno(Cliente cliente, Servicio servicio, Integer cantSesiones, Double importeTotal, Integer recargoFijoPorInsumos){
        this.cliente = cliente;
        this.servicio = servicio;
        this.cantSesiones = cantSesiones;
        this.importeTotal = importeTotal;
    }

    public Double getImporteTotal(){
        return importeTotal;
    }


    public Cliente getCliente() {
        return cliente;
    }


    public Servicio getServicio() {
        return servicio;
    }


    public Integer getCantSesiones() {
        return cantSesiones;
    }


    
}
