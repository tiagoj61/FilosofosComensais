package enumm;

public enum EstadoFilosofoEnumm {
    PENSANDO("pensando"),COMENDO("comendo"),ESPERANDO("esperando");
    private final String name;

    private EstadoFilosofoEnumm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EstadoFilosofoEnumm retornaEnumNaPosicao(int posicao){
        return EstadoFilosofoEnumm.values()[posicao];
    }


}
