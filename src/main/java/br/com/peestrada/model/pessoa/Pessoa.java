package br.com.peestrada.model.pessoa;

import java.time.LocalDate;

/**
 * Classe abstrata que representa uma pessoa no sistema
 * Exemplo do pilar de Abstração
 */
public abstract class Pessoa {
    // Atributos privados - encapsulamento
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    // Construtor
    public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // Métodos getters e setters - encapsulamento
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    // Método abstrato - obriga as subclasses a implementar
    public abstract String gerarIdentificacao();
}