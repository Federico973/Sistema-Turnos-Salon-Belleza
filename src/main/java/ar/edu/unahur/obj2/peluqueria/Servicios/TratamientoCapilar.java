package ar.edu.unahur.obj2.peluqueria.Servicios;


public class TratamientoCapilar extends Servicio{

    public TratamientoCapilar(Integer precioBase, Integer cantCupos) {
        super(precioBase, cantCupos);
    }

    @Override
    public String generarNumeroDeOrden() {
        return "TRN-TRA-" + System.nanoTime();
    }

}
