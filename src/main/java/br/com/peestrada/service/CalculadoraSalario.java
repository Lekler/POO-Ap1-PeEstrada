package br.com.peestrada.service;

import br.com.peestrada.model.empresa.FolhaFrequencia;
import br.com.peestrada.model.pessoa.Funcionario;

/**
 * Interface que define o contrato para cálculo de salário
 * Exemplo do pilar de Abstração via interface
 */
public interface CalculadoraSalario {
    double calcularSalario(Funcionario funcionario, FolhaFrequencia folhaFrequencia);
    double calcularDescontos(Funcionario funcionario, FolhaFrequencia folhaFrequencia);
    double calcularBeneficios(Funcionario funcionario);
}