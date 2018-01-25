/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package modeloqytetet;

/**
 *
 * @author David Infante Casas
 */
public class Sorpresa {
    private String texto;
    private int valor;
    private TipoSorpresa tipo;

    Sorpresa(String texto, int valor, TipoSorpresa tipo) {
        this.texto = texto;
        this.valor = valor;
        this.tipo = tipo;
    }

    String getTexto() {
        return texto;
    }

    public TipoSorpresa getTipo() {
        return tipo;
    }

    int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        //toString para la Interfaz Textual
        //return "\nSorpresa{" + texto + ", tipo=" + tipo + ", valor=" + valor + '}';
        //toString para la GUI
        return "Sorpresa:\n" + texto + "\ntipo=" + tipo + "\nvalor=" + valor;
    }
        
}
