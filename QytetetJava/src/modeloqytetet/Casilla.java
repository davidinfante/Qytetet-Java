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
public abstract class Casilla {
    protected int numeroCasilla;
    protected int coste;
    protected TipoCasilla tipo;

    public abstract TipoCasilla getTipo();
    
    public abstract TituloPropiedad getTitulo();
    
    abstract TituloPropiedad asignarPropietario(Jugador jugador);
    
    abstract int calcularValorHipoteca();
    
    abstract int cancelarHipoteca();
    
    abstract int cobrarAlquiler();
    
    abstract int edificarCasa();
    
    abstract int edificarHotel();
    
    abstract boolean estaHipotecada();;
    
    abstract public int getCoste();
    
    //int getCosteHipoteca() {}
    
    public abstract int getNumeroCasilla();
    
    abstract int getNumCasas();

    abstract int getNumHoteles();

    abstract int getPrecioEdificar();
    
    public abstract TituloPropiedad getTituloPropiedad();
    
    abstract int hipotecar();
    
    //int precioTotalComprar() {}
    
    abstract boolean propietarioEncarcelado();
    
    public abstract boolean sePuedeEdificarCasa(int FactorEspeculador);
    
    public abstract boolean sePuedeEdificarHotel(int FactorEspeculador);

    //abstract void setNumHoteles(int numHoteles);

    abstract boolean soyEdificable();
        
    public abstract boolean tengoPropietario();
        
    abstract int venderTitulo();
    
    //private void asignarTituloPropiedad() {}
    
    @Override
    public abstract String toString();
}
