package ar.edu.unahur.obj2.peluqueria.Categorias;

import ar.edu.unahur.obj2.peluqueria.Cliente;
import ar.edu.unahur.obj2.peluqueria.Exepciones.CategoriaNoAutorizadaException;

public class Vip implements Categoria{

    @Override
    public Double aplicarDescuento(double importe) {
        return importe * 0.8;
    }

    @Override
    public void validarHistorialDelCliente(Cliente cliente) {
        if (cliente.cantidadTurnos() == 0) {
            throw new CategoriaNoAutorizadaException(
                    "No se puede asignar la categoría VIP a un cliente sin turnos previos");
        }

    }
}
