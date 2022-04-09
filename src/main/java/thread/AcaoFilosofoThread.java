package thread;

import java.io.IOException;
import java.util.List;

import enumm.EstadoFilosofoEnumm;
import model.Filosofo;
import thread.interfaces.IRetornoThread;

public class AcaoFilosofoThread extends Thread {

    private IRetornoThread callback;
    List<Filosofo> filosofos;
    int filosofoDaVez;

    public AcaoFilosofoThread(List<Filosofo> filosofos) throws IOException {
        this.filosofos = filosofos;

    }

    public void filosofoDaVez(int filosofoDaVez) throws IOException {

        this.filosofoDaVez = filosofoDaVez;
    }

    public void resetFilosofos(List<Filosofo> filosofos) throws IOException {

        this.filosofos = filosofos;
    }


    public void onFutureCallback(IRetornoThread callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            System.out.println("thread");
            if (filosofos.get(filosofoDaVez).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO) {
                callback.onSuccess(EstadoFilosofoEnumm.PENSANDO);
            } else {
                //vai comer
                System.out.println(filosofoDaVez+" <- filsiasifaf");
                if (filosofoDaVez == 4) {

                    if (filosofos.get(filosofoDaVez - 1).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO || filosofos.get(0).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO) {
                        System.out.println("2");
                        callback.onSuccess(EstadoFilosofoEnumm.ESPERANDO);
                    } else {
                        System.out.println("3");
                        callback.onSuccess(EstadoFilosofoEnumm.COMENDO);
                    }
                } else if (filosofoDaVez == 0) {

                    if (filosofos.get(filosofoDaVez + 1).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO || filosofos.get(4).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO) {
                        System.out.println("4");
                        callback.onSuccess(EstadoFilosofoEnumm.ESPERANDO);
                    } else {
                        System.out.println("5");
                        callback.onSuccess(EstadoFilosofoEnumm.COMENDO);
                    }
                } else {

                    if (filosofos.get(filosofoDaVez + 1).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO || filosofos.get(filosofoDaVez - 1).getEstadoFilosofo() == EstadoFilosofoEnumm.COMENDO) {
                        System.out.println("6");
                        callback.onSuccess(EstadoFilosofoEnumm.ESPERANDO);

                    } else {
                        System.out.println("7");
                        callback.onSuccess(EstadoFilosofoEnumm.COMENDO);
                    }
                }

            }
        } catch (Exception ex) {
            System.out.println("erro");
            if (callback != null) {
                callback.onError(ex);
            }
        }
    }

}
