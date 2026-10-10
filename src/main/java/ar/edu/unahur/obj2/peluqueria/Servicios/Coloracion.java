package ar.edu.unahur.obj2.peluqueria.Servicios;


public class Coloracion extends Servicio{

    private static final Integer DiasDeRetoque = 30;

    public Coloracion(Integer precioBase, Integer cantCupos) {
        super(precioBase, cantCupos);
    }


    @Override
    public String generarNumeroDeOrden() {
        return "TRN-COL-" + System.nanoTime();
    }

    @Override
    protected String accionPosterior() {
        return "quedan" + DiasDeRetoque;
    }
}
