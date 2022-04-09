package model;

import enumm.EstadoFilosofoEnumm;

public class Filosofo {
    private String nome;
    private EstadoFilosofoEnumm estadoFilosofo;

    public Filosofo(String nome, EstadoFilosofoEnumm estadoFilosofo) {
        this.nome = nome;
        this.estadoFilosofo = estadoFilosofo;
    }

    public EstadoFilosofoEnumm getEstadoFilosofo() {
        return estadoFilosofo;
    }

    public void setEstadoFilosofo(EstadoFilosofoEnumm estadoFilosofo) {
        this.estadoFilosofo = estadoFilosofo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
