package br.com.peestrada.model.financeiro;

import br.com.peestrada.model.pessoa.Funcionario;

/**
 * Classe que representa uma conta corrente
 */
public class ContaCorrente {
    private Banco banco;
    private String agencia;
    private String numero;
    private Funcionario titular;

    public ContaCorrente(Banco banco, String agencia, String numero, Funcionario titular) {
        this.banco = banco;
        this.agencia = agencia;
        this.numero = numero;
        this.titular = titular;
    }

    // Getters e setters
    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Funcionario getTitular() {
        return titular;
    }

    public void setTitular(Funcionario titular) {
        this.titular = titular;
    }
}