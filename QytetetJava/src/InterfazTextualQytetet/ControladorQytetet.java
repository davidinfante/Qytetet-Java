/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package InterfazTextualQytetet;
import modeloqytetet.Qytetet;
import modeloqytetet.Jugador;
import modeloqytetet.Casilla;
import java.util.ArrayList;
import java.util.HashMap;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.TipoCasilla;
import modeloqytetet.TipoSorpresa;

/**
 *
 * @author David Infante Casas
 */
public class ControladorQytetet {
    private Qytetet juego;
    private Jugador jugador;
    private Casilla casilla;
    private VistaTextualQytetet vista;

    public ControladorQytetet() {
        juego = Qytetet.getInstance();
        vista = new VistaTextualQytetet();
    }
    
    public void inicializacionJuego() {
        juego.inicializarJuego(vista.obtenerNombreJugadores());
        jugador = juego.getJugadorActual();
        casilla = juego.getJugadorActual().getCasillaActual();
        
        System.out.println(juego.toString());
        vista.pause();
    }
    
    public void main() {
        inicializacionJuego();
        desarrolloJuego();
    }
    
    public void desarrolloJuego() {
        boolean endGame = false;
        while(!endGame) {
            vista.endTurnLine();
            boolean turnoJugador = turnoJugador();
            if (turnoJugador) endGame = true;
            vista.informacionJugador(jugador);
            if (!endGame) cambiarTurno();
            if (!endGame) vista.pause();
        }
        finDelJuego();
    }
    
    //****************************************************
    
    public boolean turnoJugador() { //2
        vista.inicioTurno(jugador);
        boolean endGame;
        endGame = movimiento();
        gestionInmobiliaria();
        return endGame;
    }
    
    public boolean movimiento() { //2.1
        boolean libre = movimientoSiCarcel();
        boolean endGame = false;
        if (libre) endGame = movimientoNoCarcel();
        return endGame;//true->fin false->continua
    }
    
    public void gestionInmobiliaria() { //2.2
        if (!jugador.getEncarcelado() && !jugador.bancarrota() && jugador.tengoPropiedades()) {
            int propiedad_elegida;
            ArrayList<String> props = new ArrayList();
            int opcion;
            Casilla casilla_elegida = null;

            do {
                opcion = vista.menuGestionInmobiliaria();
                switch (opcion) {
                    case 1:
                        props = jugador.getPropiedades(1);
                        propiedad_elegida = vista.menuElegirPropiedad(props);
                        if (propiedad_elegida == props.size()) break;
                        casilla_elegida = jugador.searchCasilla(props.get(propiedad_elegida));
                        vista.puedoConstruir(1, juego.edificarCasa(casilla_elegida));
                    break;
                    case 2:
                        props = jugador.getPropiedades(2);
                        propiedad_elegida = vista.menuElegirPropiedad(props);
                        if (propiedad_elegida == props.size()) break;
                        casilla_elegida = jugador.searchCasilla(props.get(propiedad_elegida));
                        vista.puedoConstruir(2, juego.edificarHotel(casilla_elegida));
                    break;
                    case 3:
                        props = jugador.getPropiedades(3);
                        propiedad_elegida = vista.menuElegirPropiedad(props);
                        if (propiedad_elegida == props.size()) break;
                        casilla_elegida = jugador.searchCasilla(props.get(propiedad_elegida));
                        vista.puedoConstruir(3, juego.venderPropiedad(casilla_elegida));
                    break;
                    case 4:
                        props = jugador.getPropiedades(4);
                        propiedad_elegida = vista.menuElegirPropiedad(props);
                        if (propiedad_elegida == props.size()) break;
                        casilla_elegida = jugador.searchCasilla(props.get(propiedad_elegida));
                        vista.puedoConstruir(4, juego.hipotecarPropiedad(casilla_elegida));
                    break;
                    case 5:
                        props = jugador.getPropiedades(5);
                        propiedad_elegida = vista.menuElegirPropiedad(props);
                        if (propiedad_elegida == props.size()) break;
                        casilla_elegida = jugador.searchCasilla(props.get(propiedad_elegida));
                        vista.puedoConstruir(5, juego.cancelarHipoteca(casilla_elegida));
                    break;
                }
                vista.saldoJugador(jugador);
            } while (opcion != 0);
        }
    }
    
    public boolean movimientoSiCarcel() { //2.1.A
        if (jugador.getEncarcelado()) {
            int opcion = vista.menuSalirCarcel();
            boolean salgoCarcel;
            if (opcion == 1) salgoCarcel = juego.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
            else salgoCarcel = juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
            vista.hasSalidoCarcel(salgoCarcel);
            return salgoCarcel;//Si he salido o no true->si, false->no
        }
        return true;//No estaba en la cárcel
    }
    
    public boolean movimientoNoCarcel() { //2.1.B
        if (!jugador.getEncarcelado()) {
            if (juego.jugar()) {
                casilla = jugador.getCasillaActual();
                vista.informacionCasilla(casilla, jugador);
                if (casilla.getTipo() == TipoCasilla.SORPRESA) {
                    vista.informacionSorpresa(juego.getCartaActual());
                    juego.aplicarSorpresa();
                    if (juego.getCartaActual().getTipo() == TipoSorpresa.CONVERTIRME) jugador = juego.getJugadorActual();
                }
                if (casilla.getTipo() == TipoCasilla.CALLE && !casilla.tengoPropietario() && jugador.tengoSaldo(casilla.getCoste())) {
                    if (vista.elegirQuieroComprar(casilla)) {
                        jugador.comprarTitulo();
                    }
                }
                if (jugador.bancarrota()) return true;//Se acaba el juego
            }
        }
        return false;//Continua el juego
    }
    
    
    public void cambiarTurno() { //3
        juego.siguienteJugador();
        jugador = juego.getJugadorActual();
    }
    
    public void finDelJuego() { //4
        vista.endTurnLine();
        HashMap<String, Integer> ranking = juego.obtenerRanking();
        vista.finDelJuego(ranking, juego);
        vista.pause();
        System.exit(0);
    }
    
    //****************************************************
    
    private Casilla elegirPropiedad(ArrayList<Casilla> propiedades) {
        //este metodo toma una lista de propiedades y genera una lista de strings, con el numero y nombre de las propiedades
        //luego llama a la vista para que el usuario pueda elegir.
        vista.mostrar("\tCasilla\tTitulo");
        int seleccion;
        ArrayList<String> listaPropiedades= new ArrayList();
        for (Casilla casilla: propiedades) {
            listaPropiedades.add( "\t"+casilla.getNumeroCasilla()+"\t"+casilla.getTituloPropiedad().getNombre()); 
        }
        seleccion = vista.menuElegirPropiedad(listaPropiedades);  
        return propiedades.get(seleccion);
    }

}
