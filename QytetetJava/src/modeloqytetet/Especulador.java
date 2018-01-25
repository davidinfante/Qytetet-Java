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
public class Especulador extends Jugador {
    private int fianza;

    public Especulador(Jugador other, int fianza) {
        super(other);
        FactorEspeculador = 2;
        this.fianza = fianza;
    }
    
    @Override
    protected void pagarImpuestos(int cantidad) {
        modificarSaldo(-cantidad/2);
    }
    
    @Override
    protected void irACarcel(Casilla casilla) {
        if (!pagarFianza(fianza)) {
            setCasillaActual(casilla);
            setEncarcelado(true);
        }
    }
    
    @Override
    protected Especulador convertirme(int fianza) {
        return this;
    }
    
    private boolean pagarFianza(int cantidad) {
        boolean salirCarcel = (saldo >= cantidad);
        if (salirCarcel) modificarSaldo(-cantidad);
        return salirCarcel;
    }
    
    @Override
    public String toString() {
        //toString para la Interfaz Textual
        //return "\nEspeculador{" + nombre + ", FactorEspeculador=" + FactorEspeculador + ", fianza=" + fianza + ", encarcelado=" + encarcelado + ", saldo=" + saldo + ", cartaLibertad=" + cartaLibertad + ", casillaActual=" + casillaActual + "\npropiedades=" + propiedades;
        //toString para la GUI
        return "Especulador:\t" + nombre + "\nFactEspec:\t" + FactorEspeculador + "\nfianza:\t" + fianza + "\nencarcelado:\t" + encarcelado + "\nsaldo:\t" + saldo + "\ncartaLibertad:\t" + cartaLibertad + "\npropiedades:\t" + propiedades;
    }
    
}//class Especulador