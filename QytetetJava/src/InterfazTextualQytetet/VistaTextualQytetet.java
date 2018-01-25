/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package InterfazTextualQytetet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import modeloqytetet.Casilla;
import modeloqytetet.Jugador;
import modeloqytetet.Sorpresa;
import modeloqytetet.Qytetet;

/**
 *
 * @author David Infante Casas
 */
public class VistaTextualQytetet {
    private static final Scanner in = new Scanner (System.in);
    
    public int menuGestionInmobiliaria(){ //ejemplo de menu    
        this.mostrar("\nElige la gestion inmobiliaria que deseas hacer");
        Map<Integer, String> menuGI = new TreeMap();
        menuGI.put(0, "Siguiente Jugador"); 
        menuGI.put(1, "Edificar casa");
        menuGI.put(2, "Edificar Hotel"); 
        menuGI.put(3, "Vender propiedad ");  	
        menuGI.put(4, "Hipotecar Propiedad"); 
        menuGI.put(5, "Cancelar Hipoteca");
        int salida=this.seleccionMenu(menuGI); // Metodo para controlar la elecci�n correcta en el menu
        return salida;
    }

    public int menuElegirPropiedad(ArrayList<String> listaPropiedades) {  //numero y nombre de propiedades            
        Map<Integer, String> menuEP = new TreeMap();
        int numeroOpcion=0;
        for(String prop: listaPropiedades) {
            menuEP.put(numeroOpcion, prop); //opcion de menu, numero y nombre de propiedad
            numeroOpcion=numeroOpcion+1;
        }
        menuEP.put(numeroOpcion, "Cancelar");
        numeroOpcion+=1;
        int salida=this.seleccionMenu(menuEP); // M�todo para controlar la elecci�n correcta en el men� 
        return salida;
    }   

    private int seleccionMenu(Map<Integer,String> menu) {
    //M�todo para controlar la elecci�n correcta de una opci�n en el men� que recibe como argumento   
        boolean valido = true; 
        int numero;
        String lectura;
        do { // Hasta que se hace una selecci�n valida
          for(Map.Entry<Integer, String> fila : menu.entrySet()) {
                numero = fila.getKey();
                String texto = fila.getValue();
                this.mostrar(numero + " : " + texto);  // n�mero de opci�n y texto
          }
          this.mostrar("Elige una opción: ");
          lectura = in.nextLine();  //lectura de teclado
          valido=this.comprobarOpcion(lectura, 0, menu.size()-1); //m�todo para comprobar la elecci�n correcta
        } while (!valido);
        return Integer.parseInt(lectura);
    }

    public ArrayList<String> obtenerNombreJugadores() { //m�todo para pedir el nombre de los jugadores
        boolean valido = true; 
        String lectura;
        ArrayList<String> nombres = new ArrayList();
        do{ //repetir mientras que el usuario no escriba un n�mero correcto 
            this.mostrar("Escribe el número de jugadores: (de 2 a 4):");
            lectura = in.nextLine();  //lectura de teclado
            valido=this.comprobarOpcion(lectura, 2, 4); //m�todo para comprobar la elecci�n correcta
        }while (!valido);

        for (int i = 1; i <= Integer.parseInt(lectura); i++) { //solicitud del nombre de cada jugador
          this.mostrar("Nombre del jugador " + i + ": ");
          nombres.add (in.nextLine());
        }
        return nombres;
    }

    private boolean comprobarOpcion(String lectura, int min, int max){ 
    //m�todo para comprobar que se introduce un entero correcto, usado por seleccion_menu
         boolean valido=true;   
         int opcion;
         try {
              opcion =Integer.parseInt(lectura);
              if (opcion<min || opcion>max) { // No es un entero entre los v�lidos
                   this.mostrar("el numero debe estar entre min y max");
                    valido = false;}

          } catch (NumberFormatException e) { // No se ha introducido un entero
                  this.mostrar("debes introducir un numero");
                  valido = false;  
          }
          if (!valido) {
            this.mostrar("Seleccion erronea. Intentalo de nuevo.");
          }
          return valido;
    }
    
    public void pause() {
        System.out.println("\nPulsa intro para continuar...: ");
        in.nextLine();
    }
    
    public void endTurnLine() {
        System.out.println("******************************************************");
    }
    
    public int menuSalirCarcel() {
        int metodo = 0;
        String lectura;
        boolean valido = true;
        System.out.println("Estás encarcelado.");
        System.out.println("\nElige una opción:");
        System.out.println("\t(1) - Pagar libertad.");
        System.out.println("\t(2) - Tirar dado.");
        
        do {
            lectura = in.nextLine();
            if (comprobarOpcion(lectura, 1, 2)) {
                metodo = Integer.parseInt(lectura);
                valido = false;
            }
            else System.out.println("\nError de entrada inténtalo de nuevo.\n");
        } while(valido);
        
        return metodo;
    }
    
    public boolean elegirQuieroComprar(Casilla casilla){
        int metodo = 0;
        String lectura;
        boolean valido = true;
        System.out.println("\nHas caído en la casilla:");
        System.out.println(casilla.toString());
        System.out.println("\nElige una opción:");
        System.out.println("\t(1) - Adquirir propiedad.");
        System.out.println("\t(2) - No adquirir.");
        
        do {
            lectura = in.nextLine();
            if (comprobarOpcion(lectura, 1, 2)) {
                metodo = Integer.parseInt(lectura);
                valido = false;
            }
            else System.out.println("\nError de entrada inténtalo de nuevo.\n");
        } while(valido);
        
        if (metodo == 1) return true;
        else return false;
    }
    
    public void hasSalidoCarcel(boolean salgoCarcel) {
        if (salgoCarcel) System.out.println("Has salido de la cárcel.");
        else System.out.println("No has podido salir de la cárcel.");
    }
    
    public void informacionCasilla(Casilla casilla, Jugador jugador) {
        switch (casilla.getTipo()){
            case SALIDA:
                System.out.println("SALIDA : Has pasado por la salida se te suman 1000 euros.");
            break;
            case CALLE:
                System.out.println("CALLE : ");
                if (casilla.tengoPropietario()) System.out.println("Caes sobre la " + casilla.toString());
            break;
            case SORPRESA:
                System.out.println("SORPRESA : Coges una carta del mazo.");
            break;
            case JUEZ:
                System.out.println("JUEZ : ");
            break;
            case CARCEL:
                System.out.println("CARCEL : Has caído en la cárcel.");
                if (jugador.getEncarcelado()) System.out.println("El juez te manda a la cárcel");
                else System.out.println("Solo estás de visita");
            break;
            case IMPUESTO:
                System.out.println("IMPUESTO : Caes en la casilla impuesto, te toca pagar.");
            break;
            case PARKING:
                System.out.println("PARKING : Estás en el parking, disfruta de tu descanso.");
            break;
        }
    }
    
    public void informacionSorpresa(Sorpresa sorpresa) {
        System.out.println("Obtienes la carta: " + sorpresa.toString());
    }
    
    public void informacionJugador(Jugador jugador) {
        System.out.println("\nResumen del jugador: " + jugador.toString());
    }
    
    public void saldoJugador(Jugador jugador) {
        System.out.println("Resumen del jugador: " + jugador.getNombre() + " saldo: " + jugador.getSaldo());
    }
    
    public void puedoConstruir(int opcion, boolean puedo) {
        //opcion: 1 casas, 2 hoteles, 3 vender, 4 hipotecar, 5 cancelar hipoteca
        if(opcion == 1 && puedo) System.out.println("\nCasa edificada");
        else if(opcion == 1 && !puedo) System.out.println("\nCasa no edificada, no hay saldo suficiente");
        else if(opcion == 2 && puedo) System.out.println("\nHotel edificado");
        else if(opcion == 2 && !puedo) System.out.println("\nHotel no edificado, no hay saldo suficiente");
        else if(opcion == 3 && puedo) System.out.println("\nPropiedad vendida");
        else if(opcion == 3 && !puedo) System.out.println("\nPropiedad no vendida, ¿esta hipotecada?");
        else if(opcion == 4 && puedo) System.out.println("\nPropiedad hipotecada");
        else if(opcion == 4 && !puedo) System.out.println("\nERROR Propiedad no hipotecada");
        else if(opcion == 5 && puedo) System.out.println("\nHipoteca cancelada");
        else if(opcion == 5 && !puedo) System.out.println("\nHipoteca no cancelada, no hay saldo suficiente");
    }
    
    public void inicioTurno(Jugador jugador) {
        System.out.println("\nEs el turno de: " + jugador.getNombre() + ", con saldo: " + jugador.getSaldo() + ", que está en la casilla: " + jugador.getCasillaActual());
        if (jugador.getEncarcelado()) System.out.println("Y está encarcelado.\n");
        else System.out.println("Y no está encarcelado.\n");
    }

    public void finDelJuego(HashMap<String, Integer> ranking, Qytetet juego) {
        System.out.println("Has caido en bancarrota, fin del juego.");
        System.out.println("\nRANKING:\n");
        for (Jugador j : juego.getJugadores()) {
            System.out.println(j.getNombre() + " - " + ranking.get(j.getNombre()));
        }
    }
    
    public void mostrar(String texto){ //m�todo que muestra en pantalla el string que recibe como argumento
        System.out.println(texto);
    }
 
}