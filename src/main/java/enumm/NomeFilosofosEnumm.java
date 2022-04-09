package enumm;

public enum NomeFilosofosEnumm {
    NOME_UM("Protagoras"),
    NOME_DOIS("pedor"),
    NOME_TRES("joao"),
    NOME_QUATRO("tiago"),
    NOME_CINCO("belem"),
    NOME_SEIS("edu"),
    NOME_SETE("rafa"),
    NOME_OITO("gustavo"),
    NOME_NOVO("jess");

    private final String name;

    private NomeFilosofosEnumm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static NomeFilosofosEnumm retornaEnumNaPosicao(int posicao){
        return NomeFilosofosEnumm.values()[posicao];
    }


}
