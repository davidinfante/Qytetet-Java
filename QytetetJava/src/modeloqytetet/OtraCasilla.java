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
public class OtraCasilla extends Casilla {
    
    //Constructor para casillas que NO son de tipo calle
    OtraCasilla(int numeroCasilla, TipoCasilla tipo) {
        this.numeroCasilla = numeroCasilla;
        this.tipo = tipo;
        if (tipo == TipoCasilla.IMPUESTO) this.coste = 1000;
        else this.coste = 0;
    }
    
    @Override
    boolean estaHipotecada() {
        return false;
    }
    
    @Override
    public TipoCasilla getTipo() {
        return tipo;
    }
    
    @Override
    public TituloPropiedad getTitulo() {
        return null;
    }
    
    @Override
    public int getCoste() {
        return coste;
    }
    
    @Override
    public int getNumeroCasilla() {
        return numeroCasilla;
    }
    
    @Override
    int getNumCasas() {
        return 0;
    }

    @Override
    int getNumHoteles() {
        return 0;
    }
    
    @Override
    int getPrecioEdificar() {
        return 0;
    }
    
    @Override
    int calcularValorHipoteca() {
        return 0;
    }
    
    @Override
    int cancelarHipoteca() {
        return 0;
    }
    
    @Override
    int cobrarAlquiler() {
        return 0;
    }
    
    @Override
    int edificarCasa() {
        return 0;
    }
    
    @Override
    int edificarHotel() {
        return 0;
    }
    
    @Override
    int hipotecar() {
        return 0;
    }
    
    @Override
    boolean propietarioEncarcelado() {
        return false;
    }
    
    @Override
    TituloPropiedad asignarPropietario(Jugador jugador) {
        return null;
    }
    
    @Override
    boolean soyEdificable() {
        return (tipo == TipoCasilla.CALLE);
    }
    
    @Override
    public boolean sePuedeEdificarCasa(int FactorEspeculador) {
        return false;
    }
    
    @Override
    public boolean sePuedeEdificarHotel(int FactorEspeculador) {
        return false;
    }
    
    @Override
    public TituloPropiedad getTituloPropiedad() {
        return null;
    }
    
    @Override
    public boolean tengoPropietario() {
        return false;
    }
    
    @Override
    int venderTitulo() {
        return 0;
    }
    
    @Override
    public String toString() {
        //toString para la Interfaz Textual
        //return "Casilla{" + numeroCasilla + ", " + tipo + '}';
        //toString para la GUI
        return "Casilla:\t" + numeroCasilla + "\nTipo:\t" + tipo;
    }
    
}
