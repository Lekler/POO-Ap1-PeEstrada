package br.com.peestrada.model.financeiro;

import br.com.peestrada.model.pessoa.Funcionario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um contracheque
 */
public class Contracheque {
    private Funcionario funcionario;
    private LocalDate mesReferencia;
    private LocalDate dataEmissao;
    private double salarioBruto;
    private double totalDescontos;
    private double salarioLiquido;
    private List<String> itensProventos;
    private List<String> itensDescontos;

    public Contracheque(Funcionario funcionario, LocalDate mesReferencia) {
        this.funcionario = funcionario;
        this.mesReferencia = mesReferencia;
        this.dataEmissao = LocalDate.now();
        this.itensProventos = new ArrayList<>();
        this.itensDescontos = new ArrayList<>();
    }

    public void adicionarProvento(String descricao, double valor) {
        itensProventos.add(String.format("%s: R$ %.2f", descricao, valor));
        salarioBruto += valor;
        calcularSalarioLiquido();
    }

    public void adicionarDesconto(String descricao, double valor) {
        itensDescontos.add(String.format("%s: R$ %.2f", descricao, valor));
        totalDescontos += valor;
        calcularSalarioLiquido();
    }

    private void calcularSalarioLiquido() {
        this.salarioLiquido = this.salarioBruto - this.totalDescontos;
    }

    public String imprimirContracheque() {
        StringBuilder sb = new StringBuilder();

        sb.append("============ CONTRACHEQUE ============\n");
        sb.append(String.format("Funcionário: %s\n", funcionario.getNome()));
        sb.append(String.format("Matrícula: %s\n", funcionario.getMatricula()));
        sb.append(String.format("Setor: %s\n", funcionario.getSetor().getNome()));
        sb.append(String.format("Referência: %s\n", mesReferencia.format(DateTimeFormatter.ofPattern("MM/yyyy"))));
        sb.append(String.format("Data de Emissão: %s\n", dataEmissao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        sb.append("--------------------------------------\n");
        sb.append("PROVENTOS:\n");

        for (String provento : itensProventos) {
            sb.append(provento).append("\n");
        }

        sb.append("--------------------------------------\n");
        sb.append("DESCONTOS:\n");

        for (String desconto : itensDescontos) {
            sb.append(desconto).append("\n");
        }

        sb.append("--------------------------------------\n");
        sb.append(String.format("Salário Bruto: R$ %.2f\n", salarioBruto));
        sb.append(String.format("Total de Descontos: R$ %.2f\n", totalDescontos));
        sb.append(String.format("Salário Líquido: R$ %.2f\n", salarioLiquido));
        sb.append("======================================\n");

        return sb.toString();
    }

    // Getters e setters
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public double getTotalDescontos() {
        return totalDescontos;
    }

    public double getSalarioLiquido() {
        return salarioLiquido;
    }
}