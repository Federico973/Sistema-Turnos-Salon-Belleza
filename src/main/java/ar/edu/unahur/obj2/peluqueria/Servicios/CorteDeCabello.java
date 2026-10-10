package ar.edu.unahur.obj2.peluqueria.Servicios;

public class CorteDeCabello extends Servicio{

    public CorteDeCabello(Integer precioBase, Integer cantCupos) {
        super(precioBase, cantCupos);
    }


    @Override
    public String generarNumeroDeOrden() {
        return "TRN-COR-" + System.nanoTime();
    }



}
