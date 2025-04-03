package br.com.peestrada.service;

import br.com.peestrada.model.empresa.FolhaFrequencia;
import br.com.peestrada.model.pessoa.Funcionario;

/**
 * Classe que implementa a interface CalculadoraSalario
 * Exemplo de implementação de interface (Abstração)
 */
public class ProcessadorFolhaPagamento implements CalculadoraSalario {

    @Override
    public double calcularSalario(Funcionario funcionario, FolhaFrequencia folhaFrequencia) {
        double salarioBase = funcionario.getSalarioBase();
        double valorHorasExtras = funcionario.calcularValorHorasExtras(folhaFrequencia.getHorasExtras());
        double descontosFaltas = calcularDescontosFaltas(funcionario, folhaFrequencia.getHorasNaoTrabalhadas());
        double descontosGerais = calcularDescontos(funcionario, folhaFrequencia);
        double beneficios = calcularBeneficios(funcionario);

        return salarioBase + valorHorasExtras - descontosFaltas - descontosGerais + beneficios;
    }

    @Override
    public double calcularDescontos(Funcionario funcionario, FolhaFrequencia folhaFrequencia) {
        // Implementação de cálculo de descontos (INSS, IR, etc.)
        double salarioBase = funcionario.getSalarioBase();
        // Simplificação: apenas INSS (11%) e IR (15%)
        double inss = salarioBase * 0.11;
        double ir = salarioBase * 0.15;

        return inss + ir;
    }

    @Override
    public double calcularBeneficios(Funcionario funcionario) {
        // Implementação de cálculo de benefícios
        // Exemplo: vale-refeição, vale-transporte, etc.
        return funcionario.getSalarioBase() * 0.05; // 5% de benefícios
    }

    // Método auxiliar para calcular descontos por faltas
    private double calcularDescontosFaltas(Funcionario funcionario, int horasNaoTrabalhadas) {
        return (funcionario.getSalarioBase() / 220) * horasNaoTrabalhadas;
    }

    // Polimorfismo - Sobreposição de método
    // Este método será sobreposto por subclasses específicas para diferentes tipos de funcionários
    public double calcularSalarioEspecifico(Funcionario funcionario, FolhaFrequencia folhaFrequencia) {
        return calcularSalario(funcionario, folhaFrequencia);
    }
}