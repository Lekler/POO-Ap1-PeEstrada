package br.com.peestrada.model.financeiro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma ordem de pagamento para um banco
 */
public class OrdemPagamento {
    private Banco banco;
    private LocalDate dataPagamento;
    private double valorTotal;
    private List<ContaCorrente> contasDestino;
    private List<Double> valores;

    public OrdemPagamento(Banco banco, LocalDate dataPagamento) {
        this.banco = banco;
        this.dataPagamento = dataPagamento;
        this.valorTotal = 0;
        this.contasDestino = new ArrayList<>();
        this.valores = new ArrayList<>();
    }

    public void adicionarPagamento(ContaCorrente contaCorrente, double valor) {
        contasDestino.add(contaCorrente);
        valores.add(valor);
        valorTotal += valor;
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();

        sb.append("========== ORDEM DE PAGAMENTO ==========\n");
        sb.append(String.format("Banco: %s (Código: %s)\n", banco.getNome(), banco.getCodigo()));
        sb.append(String.format("Data de Pagamento: %s\n", dataPagamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        sb.append(String.format("Valor Total: R$ %.2f\n", valorTotal));
        sb.append("----------------------------------------\n");
        sb.append("DETALHAMENTO:\n");

        for (int i = 0; i < contasDestino.size(); i++) {
            ContaCorrente conta = contasDestino.get(i);
            double valor = valores.get(i);

            sb.append(String.format("Funcionário: %s\n", conta.getTitular().getNome()));
            sb.append(String.format("Agência: %s Conta: %s\n", conta.getAgencia(), conta.getNumero()));
            sb.append(String.format("Valor: R$ %.2f\n", valor));
            sb.append("----------------------------------------\n");
        }

        sb.append("========================================\n");

        return sb.toString();
    }

    // Getters e setters
    public Banco getBanco() {
        return banco;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public List<ContaCorrente> getContasDestino() {
        return contasDestino;
    }

    public List<Double> getValores() {
        return valores;
    }
}