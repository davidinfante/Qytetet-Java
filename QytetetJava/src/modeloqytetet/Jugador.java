/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package modeloqytetet;
import java.util.ArrayList;

/**
 *
 * @author David Infante Casas
 */
public class Jugador {
    protected boolean encarcelado = false;
    protected String nombre;
    protected int saldo;
    protected Casilla casillaActual = null;
    protected ArrayList<TituloPropiedad> propiedades;
    protected Sorpresa cartaLibertad = null;
    protected int FactorEspeculador = 1;

    protected Jugador(String nombre) {
        this.nombre = nombre;
        this.saldo = 7500;
        this.propiedades = new ArrayList();
    }
    
    public Jugador(Jugador other) {
        this.encarcelado = other.encarcelado;
        this.nombre = other.nombre;
        this.saldo = other.saldo;
        this.casillaActual = other.casillaActual;
        this.propiedades = other.propiedades;
        this.cartaLibertad = other.cartaLibertad;
        this.FactorEspeculador = other.FactorEspeculador;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getSaldo() {
        return saldo;
    }
    
    public Casilla getCasillaActual() {
        return casillaActual;
    }
    
    public ArrayList<String> getPropiedades(int opcion) {
        ArrayList<String> props = new ArrayList();
        switch (opcion) {                                    
            case 1:
                for (TituloPropiedad tit: propiedades) {
                    if (tit.getCasilla().getNumCasas() < 4 * FactorEspeculador)
                        props.add(tit.getNombre());
                }
                break;
            case 2:
                for (TituloPropiedad tit: propiedades) {
                    if (tit.getCasilla().getNumHoteles() < 4 * FactorEspeculador)
                        props.add(tit.getNombre());
                }
                break;
            case 3:
                for (TituloPropiedad tit: propiedades) {
                    props.add(tit.getNombre());
                }
                break;
            case 4:
                ArrayList<TituloPropiedad> props_not_hipo = obtenerPropiedadesHipotecadas(false);
                for (TituloPropiedad tit: props_not_hipo) {
                    props.add(tit.getNombre());
                }
                break;
            case 5:
                ArrayList<TituloPropiedad> props_hipo = obtenerPropiedadesHipotecadas(true);
                for (TituloPropiedad tit: props_hipo) {
                    props.add(tit.getNombre());
                }
                break;
        }
        return props;
    }
    
    public Casilla searchCasilla(String nombre) {
        Casilla ret = null;
        for (TituloPropiedad tit: propiedades) {
            if (tit.getNombre().equals(nombre)) ret = tit.getCasilla();
        }
        return ret;
    }
    
    public boolean getEncarcelado() {
        return encarcelado;
    }

    public boolean bancarrota() {
        return (saldo < 0);
    }
    
    public boolean tengoPropiedades() {
        return (!propiedades.isEmpty());
    }

    protected boolean actualizarPosicion(Casilla casilla) {
        if (casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla())
            modificarSaldo(Qytetet.SALDO_SALIDA);
        setCasillaActual(casilla);
        if (casilla.soyEdificable()) {
            if (casilla.tengoPropietario()) {
                encarcelado = casilla.propietarioEncarcelado();
                if (!encarcelado) {
                    int costeAlquiler = casilla.cobrarAlquiler();
                    modificarSaldo(-costeAlquiler);
                }
            }
            return true;
        }
        else if (casilla.getTipo() == TipoCasilla.IMPUESTO) {
            int coste = casilla.getCoste();
            pagarImpuestos(coste);
            return true;
        }
        else if (casilla.getTipo() == TipoCasilla.PARKING) {
            return true;
        }
        else if (casilla.getTipo() == TipoCasilla.CARCEL) {
            return true;
        }
        else if (casilla.getTipo() == TipoCasilla.SALIDA) {
            return true;
        }
        else if (casilla.getTipo() == TipoCasilla.JUEZ) {
            return true;
        }
        else if (casilla.getTipo() == TipoCasilla.SORPRESA) {
            return true;
        }
        return false;
    }

    public boolean comprarTitulo() {
        if (casillaActual.soyEdificable()) {
            boolean tengoPropietario = casillaActual.tengoPropietario();
            if (!tengoPropietario) {
                int costeCompra = casillaActual.getCoste();
                if (tengoSaldo(costeCompra)) {
                    TituloPropiedad titulo = casillaActual.asignarPropietario(this);
                    propiedades.add(titulo);
                    modificarSaldo(-costeCompra);
                    return true;
                }
            }
        }
        return false;
    }

    Sorpresa devolverCartaLibertad() {
        Sorpresa libertad_copy = cartaLibertad;
        cartaLibertad = null;
        return libertad_copy;
    }

    protected void irACarcel(Casilla casilla) {
        setCasillaActual(casilla);
        setEncarcelado(true);
    }

    void modificarSaldo(int cantidad) {
        saldo += cantidad;
    }

    int obtenerCapital() {
        int capital = saldo;
        for(TituloPropiedad t : propiedades) {
            if (t.getCasilla().getNumCasas() > 0)
                capital += (t.getCasilla().getNumCasas() * t.getCasilla().getPrecioEdificar());

            if (t.getCasilla().getNumHoteles() > 0)
                capital += (t.getCasilla().getNumHoteles() * t.getCasilla().getPrecioEdificar());

            if (t.getHipotecada())
                capital -= t.getHipotecaBase();
        }
        return capital;
    }

    ArrayList<TituloPropiedad> obtenerPropiedadesHipotecadas(boolean hipotecada) {
        ArrayList<TituloPropiedad> titulos = new ArrayList();
        if (hipotecada) {
            for (TituloPropiedad t : propiedades) {
                if (t.getHipotecada()) titulos.add(t);
            }
        }
        if (!hipotecada) {
            for (TituloPropiedad t : propiedades) {
                if (!t.getHipotecada()) titulos.add(t);
            }
        }       
        
        return titulos;
    }

    void pagarCobrarPorCasaYHotel(int cantidad) {
        int numeroTotal = cuantasCasasHotelesTengo();
        modificarSaldo(numeroTotal * cantidad);
    }

    boolean pagarLibertad(int cantidad) {
        boolean tengoSaldo = tengoSaldo(cantidad);
        if (tengoSaldo) {
            modificarSaldo(-cantidad);
            return true;
        }
        return false;
    }

    boolean puedoEdificarCasa(Casilla casilla) {
        boolean esMia = esDeMiPropiedad(casilla);
        boolean tengoSaldo = false;
        if (esMia) {
            int costeEdificarCasa = casilla.getPrecioEdificar();
            tengoSaldo = tengoSaldo(costeEdificarCasa);
        }
        
        return tengoSaldo;
    }

    boolean puedoEdificarHotel(Casilla casilla) {
        boolean esMia = esDeMiPropiedad(casilla);
        boolean tengoSaldo = false;
        if (esMia) {
            int costeEdificarHotel = casilla.getPrecioEdificar();
            tengoSaldo = tengoSaldo(costeEdificarHotel);
        }
        
        return tengoSaldo;
    }

    boolean puedoHipotecar(Casilla casilla) {
        return esDeMiPropiedad(casilla);
    }

    boolean puedoPagarHipoteca(Casilla casilla) {
        return (saldo >= (int)(casilla.calcularValorHipoteca() + (casilla.calcularValorHipoteca() * 0.1)));
    }

    boolean puedoVenderPropiedad(Casilla casilla) {
        return (esDeMiPropiedad(casilla) && !casilla.estaHipotecada());
    }

    void setCartaLibertad(Sorpresa carta) {
        cartaLibertad = carta;
    }

    void setCasillaActual(Casilla casilla) {
        casillaActual = casilla;
    }

    void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    public boolean tengoCartaLibertad() {
        return cartaLibertad != null;
    }

    void venderPropiedad(Casilla casilla) {
        int precioVenta = casilla.venderTitulo();
        modificarSaldo(precioVenta);
        eliminarDeMisPropiedades(casilla);
    }

    private int cuantasCasasHotelesTengo() {
        int numero_total = 0;
        for (TituloPropiedad t : propiedades) {
            numero_total += t.getCasilla().getNumCasas();
            numero_total += t.getCasilla().getNumHoteles();
        }
        
        return numero_total;
    }

    private void eliminarDeMisPropiedades(Casilla casilla) {
        for (int i = 0; i < propiedades.size(); ++i)
            if (casilla.getTitulo() == propiedades.get(i)) propiedades.remove(i);
    }

    private boolean esDeMiPropiedad(Casilla casilla) {
        for (TituloPropiedad t : propiedades)
            if (casilla.getTitulo() == t) return true;
        
        return false;
    }

    public boolean tengoSaldo(int cantidad) {
        return saldo >= cantidad;
    }

    protected void pagarImpuestos(int cantidad) {
        modificarSaldo(-cantidad);
    }
    
    protected Especulador convertirme(int fianza) {
        Especulador especulador = new Especulador(this, fianza);
        return especulador;
    }

    @Override
    public String toString() {
        //toString para la Interfaz Textual
        //return "\nJugador{" + nombre + ", FactorEspeculador=" + FactorEspeculador + ", encarcelado=" + encarcelado + ", saldo=" + saldo + ", cartaLibertad=" + cartaLibertad + ", casillaActual=" + casillaActual + "\npropiedades=" + propiedades;
        //toString para la GUI
        return "Jugador:\t" + nombre + "\nFactEspec:\t" + FactorEspeculador + "\nencarcelado:\t" + encarcelado + "\nsaldo:\t" + saldo + "\ncartaLibertad:\t" + cartaLibertad + "\npropiedades:" + propiedades;
    }
    
}//class Jugador