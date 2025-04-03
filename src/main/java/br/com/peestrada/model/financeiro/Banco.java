package br.com.peestrada.model.financeiro;

/**
 * Classe que representa um banco
 */
public class Banco {
    private String codigo;
    private String nome;

    public Banco(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    // Getters e setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}