package thread.interfaces;


import enumm.EstadoFilosofoEnumm;

public interface IRetornoThread {

    void onSuccess(EstadoFilosofoEnumm estadoFilosofoEnumm) throws InterruptedException;

    void onError(Exception exception);
}
