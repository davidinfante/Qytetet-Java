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
public class Tablero {
    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    Tablero() {
        inicializar();
    }
    
    boolean esCasillaCarcel(int numeroCasilla) {
        return numeroCasilla == carcel.getNumeroCasilla();
    }

    Casilla getCarcel() {
        return carcel;
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla) {
        return casillas.get(numeroCasilla);
    }

    Casilla obtenerNuevaCasilla(Casilla casilla, int desplazamiento) {
        if ((casilla.getNumeroCasilla() + desplazamiento) > 19) {
            return casillas.get((casilla.getNumeroCasilla() + desplazamiento) - casillas.size());
        }
        else
            return casillas.get(casilla.getNumeroCasilla() + desplazamiento);
    }
    
    private void inicializar(){
        casillas = new ArrayList();
        TituloPropiedad titulo1 = new TituloPropiedad("Villa Raíz", 250, 0.1, 100, 500);
        TituloPropiedad titulo2 = new TituloPropiedad("Campamento de la Resistencia", 500, 0.15, 200, 750);
        TituloPropiedad titulo3 = new TituloPropiedad("Moga", 750, 0.2, 300, 1000);
        TituloPropiedad titulo4 = new TituloPropiedad("Ciudad Delfino", 1000, 0.25, 400, 1250);
        TituloPropiedad titulo5 = new TituloPropiedad("El Yermo", 1250, 0.3, 500, 1500);
        TituloPropiedad titulo6 = new TituloPropiedad("Ventormenta", 1500, 0.35, 600, 1750);
        TituloPropiedad titulo7 = new TituloPropiedad("Rapture", 1750, 0.4, 700, 2000);
        TituloPropiedad titulo8 = new TituloPropiedad("Ciudad Trigal", 2000, 0.45, 800, 2250);
        TituloPropiedad titulo9 = new TituloPropiedad("Costa del Sol", 2500, 0.55, 1000, 2750);
        TituloPropiedad titulo10 = new TituloPropiedad("Tallon IV", 3000, 0.65, 1200, 3250);
        TituloPropiedad titulo11 = new TituloPropiedad("Hyrule", 3500, 0.75, 1400, 3750);
        TituloPropiedad titulo12 = new TituloPropiedad("Anor Londo", 4000, 0.85, 1600, 4250);
        
        casillas.add(new OtraCasilla (0, TipoCasilla.SALIDA));
        casillas.add(new Calle (1, 2500, titulo1));
        casillas.add(new Calle (2, 3000, titulo2));
        casillas.add(new OtraCasilla (3, TipoCasilla.SORPRESA));
        casillas.add(new Calle (4, 3500, titulo3));
        casillas.add(new Calle (5, 4000, titulo4));
        casillas.add(new Calle (6, 4500, titulo5));
        casillas.add(new OtraCasilla (7,  TipoCasilla.SORPRESA));
        casillas.add(new Calle (8, 5000, titulo6));
        casillas.add(new OtraCasilla (9, TipoCasilla.CARCEL));
        casillas.add(new OtraCasilla (10, TipoCasilla.JUEZ));
        casillas.add(new Calle (11, 5500, titulo7));
        casillas.add(new Calle (12, 6000, titulo8));
        casillas.add(new OtraCasilla (13, TipoCasilla.PARKING));
        casillas.add(new Calle (14, 7000, titulo9));
        casillas.add(new Calle (15, 8000, titulo10));
        casillas.add(new OtraCasilla (16, TipoCasilla.IMPUESTO));
        casillas.add(new Calle (17, 9000, titulo11));
        casillas.add(new OtraCasilla (18, TipoCasilla.SORPRESA));
        casillas.add(new Calle (19, 10000, titulo12));

        carcel = casillas.get(9);
    }
    
    @Override
    public String toString() {
        String tablero = "Tablero: \n";
        
        for (int i = 0; i < casillas.size(); ++i)
            tablero = tablero + casillas.get(i).toString() + "\n";
            
        return tablero;
    }
 
}//class Tablero
