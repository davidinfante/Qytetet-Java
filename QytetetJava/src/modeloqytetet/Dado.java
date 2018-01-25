/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package modeloqytetet;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author David Infante Casas
 */
public class Dado {
    
    private Dado() {
    }
    
    public static Dado getInstance() {
        return DadoHolder.INSTANCE;
    }
    
    private static class DadoHolder {

        private static final Dado INSTANCE = new Dado();
    }
    
    int tirar() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }

    /*@Override
    public String toString() {
        return "Dado{" + tirar() + '}';
    }*/
}
