package br.com.peestrada.model.empresa;

/**
 * Classe que representa um setor da empresa
 */
public class Setor {
    private String codigo;
    private String nome;
    private String departamento;

    public Setor(String codigo, String nome, String departamento) {
        this.codigo = codigo;
        this.nome = nome;
        this.departamento = departamento;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}