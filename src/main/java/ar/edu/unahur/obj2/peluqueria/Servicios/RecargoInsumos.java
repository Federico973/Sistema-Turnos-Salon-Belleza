package ar.edu.unahur.obj2.peluqueria.Servicios;

public class RecargoInsumos {

    private static RecargoInsumos instancia;
    private double valor;

    private RecargoInsumos() {
    }

    public static RecargoInsumos getInstancia() {
        if (instancia == null) {
            instancia = new RecargoInsumos();
        }
        return instancia;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
