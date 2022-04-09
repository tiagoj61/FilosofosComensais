package view;

import enumm.EstadoFilosofoEnumm;
import enumm.NomeFilosofosEnumm;
import model.Filosofo;
import model.Hachi;
import thread.AcaoFilosofoThread;
import thread.interfaces.IRetornoThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    private static int QUANTIDADE_CADEIRA_FILOSOFO = 5;
    private static int TEMPO_DE_ESPERA = 3;

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Filosofo> filosofos = new ArrayList<>();
        List<AcaoFilosofoThread> filosofosThread = new ArrayList<>();

        List<Hachi> hachis = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_CADEIRA_FILOSOFO; i++) {

            Filosofo filosofo = new Filosofo(NomeFilosofosEnumm.retornaEnumNaPosicao(i).getName(), EstadoFilosofoEnumm.PENSANDO);
            AcaoFilosofoThread acaoFilosofoThread = new AcaoFilosofoThread(filosofos);
            filosofosThread.add(acaoFilosofoThread);
            filosofos.add(filosofo);
            Hachi hachi = new Hachi(1);

            hachis.add(hachi);
        }
        Queue<Filosofo> queue = new LinkedList<Filosofo>();

        List<Integer> comedor = new ArrayList<>();
        List<Integer> rodadas = new ArrayList<>();
        List<Integer> numeros = new ArrayList<>();
        numeros.add(0);
        numeros.add(1);
        numeros.add(2);
        numeros.add(3);
        numeros.add(4);
        int prioridadeComer = -1;
        int a;

        while (true) {
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            for (int i = 0; i < 5; i++) {
                System.out.println(hachis.get(i).availablePermits());
                System.out.print(filosofos.get(i).getNome() + "      -      ");
                System.out.print(filosofos.get(i).getEstadoFilosofo() + "      -      ");
                System.out.println(filosofosThread.get(i).getPriority());
            }
            System.out.println(hachis.get(0).availablePermits());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            a = (int) (Math.random() * 5);
            if (rodadas.size() == 5) {

                for (int i = 0; i < comedor.size(); i++) {
                    for (int j = numeros.size() - 1; j >= 0; j--) {
                        if (comedor.get(i).intValue() == numeros.get(j).intValue()) {
                            numeros.remove(j);
                        }
                    }
                }
                numeros.forEach(n -> {
                    filosofosThread.get(n).setPriority(10);
                    if (!queue.contains(filosofos.get(n))) {
                        System.out.println("vai pra fila-> " + filosofos.get(n).getNome());
                        filosofos.get(n).setEstadoFilosofo(EstadoFilosofoEnumm.ESPERANDO);
                        queue.add(filosofos.get(n));
                    }

                });
                numeros.add(0);
                numeros.add(1);
                numeros.add(2);
                numeros.add(3);
                numeros.add(4);
                rodadas = new ArrayList<>();
                comedor.removeAll(comedor);
            }


            int proximo = -1;
            proximo = procurarQuemPodeSair(hachis, queue, filosofos, filosofosThread);
            if (proximo != -1) {
                a = proximo;
            }

            //Thread.sleep(2000);

            if (filosofosThread.get(a).isAlive()) {
                System.out.println("runnning");
            } else {
                rodadas.add(a);
                int filosofoDaVez = a;

                filosofosThread.get(filosofoDaVez).resetFilosofos(filosofos);
                filosofosThread.get(filosofoDaVez).filosofoDaVez(filosofoDaVez);

                filosofosThread.get(filosofoDaVez).onFutureCallback(new IRetornoThread() {
                    @Override
                    public void onSuccess(EstadoFilosofoEnumm estadoAtual) throws InterruptedException {
                        if (filosofos.get(filosofoDaVez).getEstadoFilosofo() != estadoAtual) {
                            /* if(estadoAtual != EstadoFilosofoEnumm.PENSANDO){

                                if (filosofoDaVez==4){
                                    if (filosofosThread.get(filosofoDaVez - 1).getPriority() == 10 || filosofosThread.get(0).getPriority() == 10) {
                                        filosofosThread.get(filosofoDaVez).setPriority(10);
                                        estadoAtual = EstadoFilosofoEnumm.ESPERANDO;
                                    }
                                }else if(filosofoDaVez==0){
                                    if (filosofosThread.get(4).getPriority() == 10 || filosofosThread.get(filosofoDaVez + 1).getPriority() == 10) {
                                        filosofosThread.get(filosofoDaVez).setPriority(10);
                                        estadoAtual = EstadoFilosofoEnumm.ESPERANDO;
                                    }
                                }else{
                                    if (filosofosThread.get(filosofoDaVez - 1).getPriority() == 10 || filosofosThread.get(filosofoDaVez + 1).getPriority() == 10) {
                                        filosofosThread.get(filosofoDaVez).setPriority(10);
                                        estadoAtual = EstadoFilosofoEnumm.ESPERANDO;
                                    }
                                }
                            }*/
                            filosofos.get(filosofoDaVez).setEstadoFilosofo(estadoAtual);

                            if (estadoAtual == EstadoFilosofoEnumm.ESPERANDO) {
                                queue.add(filosofos.get(filosofoDaVez));
                            } else if (estadoAtual == EstadoFilosofoEnumm.PENSANDO) {
                                if (filosofoDaVez == 4) {
                                    hachis.get(filosofoDaVez).release();
                                    hachis.get(0).release();
                                } else if (filosofoDaVez == 0) {
                                    hachis.get(filosofoDaVez).release();
                                    hachis.get(1).release();
                                } else {
                                    hachis.get(filosofoDaVez).release();
                                    hachis.get(filosofoDaVez + 1).release();
                                }
                            } else {
                                if (filosofoDaVez == 4) {
                                    hachis.get(filosofoDaVez).tryAcquire();
                                    hachis.get(0).tryAcquire();
                                } else if (filosofoDaVez == 0) {
                                    hachis.get(filosofoDaVez).tryAcquire();
                                    hachis.get(1).tryAcquire();
                                } else {
                                    hachis.get(filosofoDaVez).tryAcquire();
                                    hachis.get(filosofoDaVez + 1).tryAcquire();
                                }
                                comedor.add(filosofoDaVez);
                            }
                        }
                    }

                    @Override
                    public void onError(Exception exception) {
                        System.err.println("Erro retorno thread");
                        exception.printStackTrace();
                    }

                });
                filosofosThread.get(filosofoDaVez).run();


            }

        }


    }

    public static int procurarQuemPodeSair(List<Hachi> hachis, Queue<Filosofo> queues, List<Filosofo> filosofos, List<AcaoFilosofoThread> filosofosThread) {
        Filosofo filosofoAnt;
        Filosofo filosofoProx;
        for (int i = 0; i < 5; i++) {
            Filosofo filosofo = filosofos.get(i);
           /* if (queues.contains(filosofo)) {
                if (i == 0) {
                    filosofoAnt = filosofos.get(4);
                } else {
                    filosofoAnt = filosofos.get(i - 1);
                }
                if (i == 4) {
                    filosofoProx = filosofos.get(0);
                } else {
                    filosofoProx = filosofos.get(i + 1);
                }
                AcaoFilosofoThread filosofoProxThread;
                AcaoFilosofoThread filosofoAntThread;
                if (i == 0) {
                    filosofoAntThread = filosofosThread.get(0);
                } else {
                    filosofoAntThread = filosofosThread.get(i - 1);
                }
                if (i == 4) {
                    filosofoProxThread = filosofosThread.get(0);
                } else {
                    filosofoProxThread = filosofosThread.get(i + 1);
                }


                if (queues.contains(filosofoProx) && filosofoProxThread.getPriority() > filosofosThread.get(i).getPriority()) {
                    continue;
                } else if (queues.contains(filosofoAnt) && filosofoAntThread.getPriority() > filosofosThread.get(i).getPriority()) {
                    continue;
                }
            }
*/
            if (i == 4) {
                if (hachis.get(0).availablePermits() == 1 && hachis.get(i).availablePermits() == 1) {
                    if (queues.contains(filosofos.get(i))) {
                        queues.remove(filosofos.get(i));
                        filosofosThread.get(i).setPriority(5);
                        return i;
                    }
                }
            } else if (i == 0) {

                if (hachis.get(0).availablePermits() == 1 && hachis.get(i + 1).availablePermits() == 1) {
                    if (queues.contains(filosofos.get(i))) {
                        queues.remove(filosofos.get(i));
                        filosofosThread.get(i).setPriority(5);
                        return i;
                    }
                }
            } else {

                if (hachis.get(i).availablePermits() == 1 && hachis.get(i + 1).availablePermits() == 1) {
                    if (queues.contains(filosofos.get(i))) {
                        queues.remove(filosofos.get(i));
                        filosofosThread.get(i).setPriority(5);
                        return i;
                    }

                }
            }

        }
        //preimpção
        /*if(queues.size()>0){
            for (int i = 0; i < 5; i++) {
                if(filosofos.get(i).equals(queues.toArray()[0])){
                    if(hachis.get(i).availablePermits()==0){
                        if(i==0){
                            return 4;
                        }else{
                            return i-1;
                        }

                    }else if(hachis.get(i==4? 0:i+1).availablePermits()==0){
                        if(i==4){
                            return 0;
                        }else{
                            return i+1;
                        }

                    }
                }
            }
        }*/
        return -1;
    }
}
