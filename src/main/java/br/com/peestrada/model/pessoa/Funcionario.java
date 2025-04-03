package br.com.peestrada.model.pessoa;

import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.model.financeiro.ContaCorrente;

import java.time.LocalDate;

/**
 * Classe Funcionario que herda de Pessoa
 * Exemplo do pilar de Herança
 */
public class Funcionario extends Pessoa {
    // Atributos privados - encapsulamento
    private String matricula;
    private double salarioBase;
    private LocalDate dataAdmissao;
    private Setor setor;
    private ContaCorrente contaCorrente;

    // Construtor
    public Funcionario(String nome, String cpf, LocalDate dataNascimento,
                       String matricula, double salarioBase,
                       LocalDate dataAdmissao, Setor setor) {
        super(nome, cpf, dataNascimento); // Chamada ao construtor da superclasse
        this.matricula = matricula;
        this.salarioBase = salarioBase;
        this.dataAdmissao = dataAdmissao;
        this.setor = setor;
    }

    // Implementação do método abstrato da superclasse
    @Override
    public String gerarIdentificacao() {
        return "Funcionário: " + getNome() + " - Matrícula: " + matricula;
    }

    // Polimorfismo - Sobrecarga de método
    // Método para calcular horas extras com diferentes parâmetros
    public double calcularValorHorasExtras(int horasExtras) {
        // Cálculo básico: hora extra vale 50% a mais
        return (salarioBase / 220) * horasExtras * 1.5;
    }

    public double calcularValorHorasExtras(int horasExtras, double percentualAdicional) {
        // Cálculo com percentual personalizado
        return (salarioBase / 220) * horasExtras * (1 + percentualAdicional);
    }

    // Getters e setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        // Adicionar validação
        if (salarioBase < 0) {
            throw new IllegalArgumentException("Salário base não pode ser negativo");
        }
        this.salarioBase = salarioBase;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }
}