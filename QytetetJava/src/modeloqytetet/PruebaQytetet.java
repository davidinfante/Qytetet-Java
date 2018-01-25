/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package modeloqytetet;
import java.util.ArrayList;
import InterfazTextualQytetet.ControladorQytetet;

/**
 *
 * @author David Infante Casas
 */
public class PruebaQytetet {
    private static ArrayList<Sorpresa> mazo = new ArrayList();
    private static Tablero tablero = new Tablero();
    
    //Inicialización de las cartas sorpresa que van en el mazo
    private static void inicializarSorpresas() {
        mazo.add(new Sorpresa ("Aquel tío que llevas 13 años sin ver te ha mandado dinero, recibes 1000 euros", 1000, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Te toca pagar la matrícula de la universidad de tus hijos, pagas 2000 euros", -1500, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Se descubre que le estafaste al chino de tu barrio 1000 euros en golosinas, vas a que el Juez te juzgue", 10, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("No llegas a fin de mes con el sueldo de dependiente del McDonalds, pasa por la salida", 0, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Vas a visitar a un amigo de la infancia a la cárcel", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Mucha gente de todo el mundo viene de viaje y se alojan en tus casas y hoteles, recibes 200 euros por cada hotel o casa", 200, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("¡Ha llegado el momento de pagar recibos! Paga 300 euros por cada hotel o casa", -300, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("Es tu cumpleaños y cada jugador te envía 400 euros", 400, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Te sientes generoso, pagas 200 euros a cada jugador", -200, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Tu abogado presenta nuevas pruebas que demuestran tu inocencia, sales de la cárcel", 0, TipoSorpresa.SALIRCARCEL));
    }

    //Return de las cartas del mazo sorpresa con valor mayor que 0
    private static ArrayList<Sorpresa> getCartasValorMayor0() {
        ArrayList<Sorpresa> valor_mayor_que_0 = new ArrayList();
        for (Sorpresa sorpresa : mazo) {
            if (sorpresa.getValor() > 0) {
                valor_mayor_que_0.add(sorpresa);
            }
        }
        return valor_mayor_que_0;
    }
    
    //Return de las cartas del mazo sorpresa con tipo IRACASILLA
    private static ArrayList<Sorpresa> getCartasTipoIrACasilla() {
        ArrayList<Sorpresa> tipo_iracasilla = new ArrayList();
        for (Sorpresa sorpresa : mazo) {
            if (sorpresa.getTipo() == TipoSorpresa.IRACASILLA) {
                tipo_iracasilla.add(sorpresa);
            }
        }
        return tipo_iracasilla;
    }
    
    //Return de las cartas del mazo sorpresa con tipo pasado como argumento
    private static ArrayList<Sorpresa> getCartasTipo(TipoSorpresa tipo_carta) {
        ArrayList<Sorpresa> tipo_argumento = new ArrayList();
        for (Sorpresa sorpresa : mazo) {
            if (sorpresa.getTipo() == tipo_carta) {
                tipo_argumento.add(sorpresa);
            }
        }
        return tipo_argumento;
    }
    
    
    //Método main
    //Quitar el comentario del main para usar la interfaz textual,
    //los del dado en Qytetet.java y comentar los del dado que no están comentados
    public static void main(String[] args) {
        
        /*inicializarSorpresas();
        
        //Prueba para visualizar las cartas sorpresa
        for (Sorpresa sorpresa : mazo) {
            System.out.println(sorpresa.toString());
        }
        
        //Probar los métodos de visualización de cartas sorpresa
        ArrayList<Sorpresa> mayor_0 = new ArrayList();
        mayor_0 = getCartasValorMayor0();
        System.out.println(mayor_0);
        ArrayList<Sorpresa> ira_casilla = new ArrayList();
        ira_casilla = getCartasTipoIrACasilla();
        System.out.println(ira_casilla);
        ArrayList<Sorpresa> carta_tipo = new ArrayList();
        carta_tipo = getCartasTipo(TipoSorpresa.SALIRCARCEL);
        System.out.println(carta_tipo);
        
        //Probar que las casillas del tablero se han creado correctamente
        System.out.println(tablero.toString());*/
        
        /*Qytetet qytetet = Qytetet.getInstance();
        System.out.println(qytetet.toString());*/
        
        ControladorQytetet game = new ControladorQytetet();
        game.main();   
    }
    
}//class QytetetJava
