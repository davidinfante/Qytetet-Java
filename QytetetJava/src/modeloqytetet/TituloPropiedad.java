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
public class TituloPropiedad {
    private String nombre;
    private boolean hipotecada;
    private int alquilerBase;
    private double factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private Calle casilla = null;
    private Jugador propietario = null;

    TituloPropiedad(String nombre, int alquilerBase, double factorRevalorizacion, int hipotecaBase, int precioEdificar) {
        this.nombre = nombre;
        this.hipotecada = false;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
    }

    void cobrarAlquiler(int coste) {
        propietario.modificarSaldo(coste);
    }
    
    Casilla getCasilla() {
        return casilla;
    }
    
    public Jugador getPropietario() {
        return propietario;
    }
    
    int getAlquilerBase() {
        return alquilerBase;
    }
    
    double getFactorRevalorizacion() {
        return factorRevalorizacion;
    }
    
    int getHipotecaBase() {
        return hipotecaBase;
    }
    
    boolean getHipotecada() {
        return hipotecada;
    }
    
    public String getNombre() {
        return nombre;
    }

    int getPrecioEdificar() {
        return precioEdificar;
    }
    
    boolean propietarioEncarcelado() {
        return propietario.getEncarcelado();
    }

    void setCasilla(Calle casilla) {
        this.casilla = casilla;
    }

    void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
    
    void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    boolean tengoPropietario() {
        return propietario != null;
    }
    
    @Override
    public String toString() {
        //toString para la Interfaz Textual
        //if (tengoPropietario()) return "TituloPropiedad{" + nombre + ", hipotecada=" + hipotecada + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", precioEdificar=" + precioEdificar + ", numCasas=" + casilla.getNumCasas() + ", numHoteles=" + casilla.getNumHoteles()+ '}';
        //else return "TituloPropiedad{" + nombre + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", precioEdificar=" + precioEdificar + '}';
        //toString para la GUI
        if (tengoPropietario()) return "\n- " + nombre + "\nhipotecada:\t" + hipotecada + "\nalquilerBase:\t" + alquilerBase + "\nfactReval:\t" + factorRevalorizacion + "\nhipotecaBase:\t" + hipotecaBase + "\nprecioEdificar:\t" + precioEdificar + "\nnumCasas:\t" + casilla.getNumCasas() + "\nnumHoteles:\t" + casilla.getNumHoteles();
        else return "\n- " + nombre + "\nalquilerBase:\t" + alquilerBase + "\nfactReval:\t" + factorRevalorizacion + "\nhipotecaBase:\t" + hipotecaBase + "\nprecioEdificar:\t" + precioEdificar;
    }

}
