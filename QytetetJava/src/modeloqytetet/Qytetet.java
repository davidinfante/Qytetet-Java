/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package modeloqytetet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author David Infante Casas
 */
public class Qytetet {
    public static int MAX_JUGADORES = 4;
    static int MAX_CARTAS = 12;
    static int MAX_CASILLAS = 20;
    static int PRECIO_LIBERTAD = 200;
    public static int SALDO_SALIDA = 1000;
    //private Dado dado;
    //este dado y todas sus apariciones en este código
    //son para que funcione la InterfazTextual, el dado
    //usado es para que funcione la GUI
    private Tablero tablero;
    private ArrayList<Sorpresa> mazo;
    private Sorpresa cartaActual;
    private int index_mazo;
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual;
    
    private Qytetet() {
        index_mazo = 0;
        inicializarTablero();
        //dado = Dado.getInstance();
    }
    
    public static Qytetet getInstance() {
        return QytetetHolder.INSTANCE;
    }
    
    private static class QytetetHolder {

        private static final Qytetet INSTANCE = new Qytetet();
    }
    
    public boolean aplicarSorpresa() {
        boolean tienePropietario = false;
        switch (cartaActual.getTipo()) {
            case PAGARCOBRAR:
                jugadorActual.modificarSaldo(cartaActual.getValor());
            break;
            case IRACASILLA:
                boolean esCarcel = tablero.esCasillaCarcel(cartaActual.getValor());
                if (esCarcel) {
                    encarcelarJugador();
                }
                else {
                    Casilla nuevaCasilla = tablero.obtenerCasillaNumero(cartaActual.getValor());
                    tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
                }
            break;
            case PORCASAHOTEL:
                jugadorActual.pagarCobrarPorCasaYHotel(cartaActual.getValor());
            break;
            case PORJUGADOR:
                for (Jugador jugador : jugadores) {
                    if (jugador != jugadorActual) {
                        jugador.modificarSaldo(cartaActual.getValor());
                        jugadorActual.modificarSaldo(-cartaActual.getValor());
                    }
                }
            break;
            case CONVERTIRME:
                for (int i = 0; i < jugadores.size(); ++i) {
                    if (jugadores.get(i).equals(jugadorActual)) {
                        Especulador especulador = jugadorActual.convertirme(cartaActual.getValor());
                        jugadores.set(i, especulador);
                        jugadorActual = jugadores.get(i);
                    }
                }
            break;
        }

        if (cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL) {
            jugadorActual.setCartaLibertad(cartaActual);
        }
        else {
            mazo.add(cartaActual);
        }
        cartaActual.toString();
        return tienePropietario;
    }
    
    public boolean cancelarHipoteca(Casilla casilla) {
        if (casilla.getTitulo().getPropietario() == jugadorActual) {
            boolean sePuedeCancelar = casilla.estaHipotecada();
            if (sePuedeCancelar) {
                boolean puedoCancelar = jugadorActual.puedoPagarHipoteca(casilla);
                if (puedoCancelar) {
                    int cantidadAPagar = casilla.cancelarHipoteca();
                    jugadorActual.modificarSaldo(-cantidadAPagar);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hipotecarPropiedad(Casilla casilla) {
        if (casilla.soyEdificable()) {
            boolean sePuedeHipotecar = !casilla.estaHipotecada();
            if (sePuedeHipotecar) {
                boolean puedoHipotecar = jugadorActual.puedoHipotecar(casilla);
                if (puedoHipotecar) {
                    int cantidadRecibida = casilla.hipotecar();
                    jugadorActual.modificarSaldo(cantidadRecibida);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean comprarTituloPropiedad() {
        return jugadorActual.comprarTitulo();
    }
    
    public boolean edificarCasa(Casilla casilla) {
        if (casilla.soyEdificable()) {
            boolean sePuedeEdificar = casilla.sePuedeEdificarCasa(jugadorActual.FactorEspeculador);
            if (sePuedeEdificar) {
                boolean puedoEdificar = jugadorActual.puedoEdificarCasa(casilla);
                if (puedoEdificar && jugadorActual.tengoSaldo(casilla.getTitulo().getPrecioEdificar())) {
                    int costeEdificarCasa = casilla.edificarCasa();
                    jugadorActual.modificarSaldo(-costeEdificarCasa);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean edificarHotel(Casilla casilla) {
        if (casilla.soyEdificable()) {
            boolean sePuedeEdificar = casilla.sePuedeEdificarHotel(jugadorActual.FactorEspeculador);
            if (sePuedeEdificar) {
                boolean puedoEdificar = jugadorActual.puedoEdificarHotel(casilla);
                if (puedoEdificar) {
                    int costeEdificarHotel = casilla.edificarHotel();
                    jugadorActual.modificarSaldo(-costeEdificarHotel);
                    return true;
                }
            }
        }
        return false;
    }
    
    public Sorpresa getCartaActual() {
        return cartaActual;
    }
    
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    public Jugador getJugadorActual() {
        return jugadorActual;
    }
    
    public void inicializarJuego(ArrayList<String> nombres) {
        inicializarJugadores(nombres);
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) {
        boolean libre = false;
        if (metodo == MetodoSalirCarcel.TIRANDODADO) {
            //int valorDado = dado.tirar();
            GUIQytetet.Dado dado = GUIQytetet.Dado.getInstance();
            int valorDado = dado.nextNumber();
            System.out.println("Sacas un: " + valorDado);
            libre = valorDado >= 5;
        }
        else if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD) {
            boolean tengoSaldo = jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
            libre = tengoSaldo;
        }
        
        if (libre) {
            jugadorActual.setEncarcelado(false);
        }
        return libre;
    }
    
    public boolean jugar() {
        //int valorDado = dado.tirar();
        GUIQytetet.Dado dado = GUIQytetet.Dado.getInstance();
        int valorDado = dado.nextNumber();
        System.out.println("Tiras el dado y sacas un " + valorDado);
        Casilla casillaPosicion = jugadorActual.getCasillaActual();
        Casilla nuevaCasilla = tablero.obtenerNuevaCasilla(casillaPosicion, valorDado);
        boolean tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
        if (!nuevaCasilla.soyEdificable()) {
            if (nuevaCasilla.getTipo() == TipoCasilla.JUEZ) {
                encarcelarJugador();
            }
            else if (nuevaCasilla.getTipo() == TipoCasilla.SORPRESA) {
                cartaActual = mazo.get(index_mazo % mazo.size());
                index_mazo += 1;
            }
        }
        return tienePropietario;
    }

    public HashMap<String, Integer> obtenerRanking() {
        HashMap<String, Integer> ranking = new HashMap();
        for (Jugador jugador: jugadores) {
            int capital = jugador.getSaldo();
            ranking.put(jugador.getNombre(), capital);
        }
        return ranking;
    }
    
    public ArrayList<Casilla> propiedadesHipotecadasJugador(boolean hipotecadas) {
        ArrayList<Casilla> cas_hipo = new ArrayList();
        
        if (hipotecadas) {
            for (int i = 0; i < MAX_CASILLAS; ++i) {
                if (tablero.obtenerCasillaNumero(i).estaHipotecada())
                    cas_hipo.add(tablero.obtenerCasillaNumero(i));
            }
        }
        
        if (!hipotecadas) {
            for (int i = 0; i < MAX_CASILLAS; ++i) {
                if (!tablero.obtenerCasillaNumero(i).estaHipotecada())
                    cas_hipo.add(tablero.obtenerCasillaNumero(i));
            }
        }
        
        return cas_hipo;
    }
    
    public void siguienteJugador() {
        boolean no_repetir = false;
        for (int i = 0; i < jugadores.size(); ++i) {
            if ((jugadores.get(i) == jugadorActual) && (i != jugadores.size()-1) && !no_repetir) {
                jugadorActual = jugadores.get(i+1);
                no_repetir = true;
            }
            if ((jugadores.get(i) == jugadorActual) && (i == jugadores.size()-1) && !no_repetir) {
                jugadorActual = jugadores.get(0);
                no_repetir = true;
            }
        }
    }

    public boolean venderPropiedad(Casilla casilla) {
        if (casilla.soyEdificable()) {
            boolean puedoVender = jugadorActual.puedoVenderPropiedad(casilla);
            if (puedoVender) {
                jugadorActual.venderPropiedad(casilla);
                return true;
            }
        }
        return false;
    }
    
    private void encarcelarJugador() {
        if (!jugadorActual.tengoCartaLibertad()) {
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
        }
        else {
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
        }
    }

    private void inicializarCartasSorpresa() {
        mazo = new ArrayList();
        mazo.add(new Sorpresa ("¡Te conviertes en Especulador con una fianza de 3000!", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("¡Te conviertes en Especulador con una fianza de 5000!", 5000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("Aquel tío que llevas 13 años sin ver te ha mandado dinero, recibes 1000 euros", 1000, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Te toca pagar la matrícula de la universidad de tus hijos, pagas 1500 euros", -1500, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Quedas con un amigo en el parking para charlar", 13, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("No llegas a fin de mes con el sueldo de dependiente del McDonalds, pasa por la salida", 0, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Se descubre que le estafaste al chino de tu barrio 1000 euros en golosinas, vas a que el Juez te juzgue", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Mucha gente de todo el mundo viene de viaje y se alojan en tus casas y hoteles, recibes 200 euros por cada hotel o casa", 200, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("¡Ha llegado el momento de pagar recibos! Paga 300 euros por cada hotel o casa", -300, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("Es tu cumpleaños y cada jugador te envía 400 euros", 400, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Te sientes generoso, pagas 200 euros a cada jugador", -200, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Tu abogado presenta nuevas pruebas que demuestran tu inocencia, sales de la cárcel", 0, TipoSorpresa.SALIRCARCEL));
    }
    
    private void inicializarJugadores(ArrayList<String> nombres) {
        jugadores = new ArrayList();
        if (nombres.size() <= MAX_JUGADORES)
            for (String s : nombres) {
                Jugador aux = new Jugador(s);
                jugadores.add(aux);
            }
    }
    
    private void inicializarTablero() {
        tablero = new Tablero();
    }

    private void salidaJugadores() {
        for (Jugador j : jugadores)
            j.setCasillaActual(tablero.obtenerCasillaNumero(0));
        
        int randomNum = ThreadLocalRandom.current().nextInt(0, jugadores.size());
        jugadorActual = jugadores.get(randomNum);
    }
    
    @Override
    public String toString() {
        return tablero.toString() + mazo.toString() + jugadores.toString();
    }
    
}
