package ar.edu.unahur.obj2.peluqueria;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.peluqueria.Categorias.Categoria;
import ar.edu.unahur.obj2.peluqueria.Exepciones.ClienteSinTurnosException;

public class Cliente {

    private final String nombre;
    private Categoria categoria;
    private List<Turno> historialDeTurnos;

    public Cliente(String nombre, Categoria categoria) {
        this.nombre = nombre;
        this.historialDeTurnos = new ArrayList<>();
        this.setCategoria(categoria);
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        categoria.validarHistorialDelCliente(this);
        this.categoria = categoria;
    }

    public List<Turno> getHistorial() {
        if (historialDeTurnos.isEmpty()) {
            throw new ClienteSinTurnosException("El cliente " + nombre + " no posee turnos");
        }
        return historialDeTurnos;
    }

    public Integer cantidadTurnos() {
        return historialDeTurnos.size();
    }

    public Double costoTotal() {
        return historialDeTurnos.stream().mapToDouble(t -> t.getImporteTotal()).sum();
    }
    

    public void agregarTurno(Turno turno) {
        historialDeTurnos.add(turno);
    }

}
