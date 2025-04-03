package br.com.peestrada.service;

import br.com.peestrada.model.empresa.FolhaFrequencia;
import br.com.peestrada.model.pessoa.Funcionario;

/**
 * Classe específica para cálculo de salário de gerentes
 * Exemplo de Polimorfismo por sobreposição (override)
 */
public class ProcessadorFolhaPagamentoGerente extends ProcessadorFolhaPagamento {

    @Override
    public double calcularSalarioEspecifico(Funcionario funcionario, FolhaFrequencia folhaFrequencia) {
        // Gerentes recebem um bônus adicional de 10%
        double salarioBase = super.calcularSalarioEspecifico(funcionario, folhaFrequencia);
        return salarioBase * 1.10;
    }
}