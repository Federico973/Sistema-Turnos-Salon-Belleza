package ar.edu.unahur.obj2.peluqueria.Categorias;


public class Frecuente implements Categoria{

    @Override
    public Double aplicarDescuento(double importe) {
        return importe * 0.9;
    }

}
