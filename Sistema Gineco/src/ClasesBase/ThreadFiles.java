package ClasesBase;

import GUI.EleccionColor;
import java.util.ArrayList;

/**
 *
 * @author Denise
 */
public class ThreadFiles extends Thread {
    
    private boolean seguir =  false;
    private static ArrayList<Casilla> colores;
    private EleccionColor parent;

    public ThreadFiles(EleccionColor parent) {
        colores = new ArrayList<>();
        this.parent = parent;
    }
    
    public synchronized void run()
    {
        try {
            while(!Thread.interrupted()) {
                wait();
                ManejoArchivos.guardarColor(null, colores.get(0).color);
                colores.remove(0);
                sleep(300);
                ClasesBase.GestorEstilos.pintar(parent);
            }
        } catch (InterruptedException e) { return; }
    }
    
    public synchronized void put (int color) {
        colores.add(new Casilla(color, false));
        notifyAll();
    }

    private class Casilla {
        private int color;
        private boolean guardado;

        public Casilla(int color, boolean guardado) {
            this.color = color;
            this.guardado = guardado;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public boolean isGuardado() {
            return guardado;
        }

        public void setGuardado(boolean guardado) {
            this.guardado = guardado;
        }
    }
}
