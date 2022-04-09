package model;

import java.util.concurrent.Semaphore;

public class Hachi extends Semaphore {
    private Semaphore semaforo;
    private boolean emUso;

    public Hachi(int permits) {
        super(permits);
    }

    public boolean isEmUso() {
        return emUso;
    }

    public void setEmUso(boolean emUso) {
        this.emUso = emUso;
    }
}
