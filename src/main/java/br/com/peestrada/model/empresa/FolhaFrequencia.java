package br.com.peestrada.model.empresa;

import br.com.peestrada.model.pessoa.Funcionario;

import java.time.LocalDate;

/**
 * Classe que representa a folha de frequência do funcionário
 */
public class FolhaFrequencia {
    private Funcionario funcionario;
    private LocalDate mesReferencia;
    private int horasExtras;
    private int horasNaoTrabalhadas;
    private String observacoes;

    public FolhaFrequencia(Funcionario funcionario, LocalDate mesReferencia) {
        this.funcionario = funcionario;
        this.mesReferencia = mesReferencia;
        this.horasExtras = 0;
        this.horasNaoTrabalhadas = 0;
        this.observacoes = "";
    }

    // Getters e setters
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(int horasExtras) {
        this.horasExtras = horasExtras;
    }

    public int getHorasNaoTrabalhadas() {
        return horasNaoTrabalhadas;
    }

    public void setHorasNaoTrabalhadas(int horasNaoTrabalhadas) {
        this.horasNaoTrabalhadas = horasNaoTrabalhadas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}