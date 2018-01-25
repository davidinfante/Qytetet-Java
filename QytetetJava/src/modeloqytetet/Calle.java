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
public class Calle extends Casilla {
    protected int numHoteles;
    protected int numCasas;
    protected TituloPropiedad titulo;
    
    //Constructor para casillas que SON tipo calle
    Calle(int numeroCasilla, int coste, TituloPropiedad titulo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        this.titulo = titulo;
        this.tipo = TipoCasilla.CALLE;
        this.numHoteles = 0;
        this.numCasas = 0;
        this.titulo.setCasilla(this);
    }
    
    @Override
    public TipoCasilla getTipo() {
        return tipo;
    }
    
    @Override
    public TituloPropiedad getTitulo() {
        return titulo;
    }
    
    @Override
    TituloPropiedad asignarPropietario(Jugador jugador) {
        titulo.setPropietario(jugador);
        return titulo;
    }
    
    @Override
    int calcularValorHipoteca() {
        return (titulo.getHipotecaBase() + (int)(numCasas * 0.5 * titulo.getHipotecaBase() + numHoteles * titulo.getHipotecaBase()));
    }
    
    @Override
    int cancelarHipoteca() {
        titulo.setHipotecada(false);
        return (int)(calcularValorHipoteca() + (calcularValorHipoteca() * 0.1));
    }
    
    @Override
    int cobrarAlquiler() {
        int costeAlquilerBase = titulo.getAlquilerBase();
        int costeAlquiler = costeAlquilerBase + (int)(numCasas*0.5 + numHoteles*2);
        titulo.cobrarAlquiler(costeAlquiler);
        return costeAlquiler;
    }
    
    @Override
    int edificarCasa() {
        setNumCasas(numCasas+1);
        int costeEdificarCasa = titulo.getPrecioEdificar();
        return costeEdificarCasa;
    }
    
    @Override
    int edificarHotel() {
        setNumHoteles(numHoteles+1);
        int costeEdificarHotel = titulo.getPrecioEdificar();
        return costeEdificarHotel;
    }
    
    @Override
    boolean estaHipotecada() {
        return titulo.getHipotecada();
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
        return numCasas;
    }

    @Override
    int getNumHoteles() {
        return numHoteles;
    }
    
    @Override
    int getPrecioEdificar() {
        return titulo.getPrecioEdificar();
    }
    
    @Override
    public TituloPropiedad getTituloPropiedad() {
        return titulo;
    }
    
    @Override
    int hipotecar() {
        titulo.setHipotecada(true);
        return calcularValorHipoteca();
    }
    
    @Override
    boolean propietarioEncarcelado() {
        return titulo.propietarioEncarcelado();
    }
    
    @Override
    public boolean sePuedeEdificarCasa(int FactorEspeculador) {
        return (numCasas < (FactorEspeculador * 4));
    }
    
    @Override
    public boolean sePuedeEdificarHotel(int FactorEspeculador) {
        return (numHoteles < (FactorEspeculador * 4));
    }
    
    void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }

    void setNumHoteles(int numHoteles) {
        this.numHoteles = numHoteles;
    }
    
    @Override
    boolean soyEdificable() {
        return true;
    }
    
    @Override
    public boolean tengoPropietario() {
        return titulo.tengoPropietario();
    }
      
    @Override
    int venderTitulo() {
        titulo.setPropietario(null);
        setNumHoteles(0);
        setNumCasas(0);
        int precioCompra = coste + (numCasas + numHoteles) * titulo.getPrecioEdificar();
        return (int) (precioCompra + titulo.getFactorRevalorizacion() * precioCompra);
    }
    
    private void setTitulo(TituloPropiedad titulo){
        this.titulo = titulo;
    }
   
    @Override
    public String toString() {
        //toString para la Interfaz Textual
        /*if (tipo == TipoCasilla.CALLE && !tengoPropietario()) return "Casilla{" + numeroCasilla + ", coste=" + coste + '}' + "\ncon título: " + titulo;
        else {
            String propietario = titulo.getPropietario().getNombre();
            return "Casilla{" + numeroCasilla + ", pertenece a " + propietario + ", coste=" + coste + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + '}' + "\ncon título: " + titulo;
        }*/
        //toString para la GUI
        if (tipo == TipoCasilla.CALLE && !tengoPropietario()) return "Casilla " + numeroCasilla + "\ncoste=" + coste + "\ncon título: " + titulo;
        else {
            String propietario = titulo.getPropietario().getNombre();
            return "Casilla:\t" + numeroCasilla + "\npertenece a:\t" + propietario + "\ncoste:\t" + coste + "\nnumHoteles:\t" + numHoteles + "\nnumCasas:\t" + numCasas + "\ntítulo:\t" + titulo;
        }
    }
    
}
