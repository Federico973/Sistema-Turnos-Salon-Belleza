package ar.edu.unahur.obj2.peluqueria.Categorias;


public class Ocasional implements Categoria{

    @Override
    public Double aplicarDescuento(double importe) {
        return importe;
    }

}
