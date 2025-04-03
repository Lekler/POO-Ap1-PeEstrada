package br.com.peestrada.service;

import br.com.peestrada.model.empresa.FolhaFrequencia;
import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.model.pessoa.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessadorFolhaPagamentoTest {

    private ProcessadorFolhaPagamento processador;
    private Funcionario funcionario;
    private FolhaFrequencia folhaFrequencia;

    @BeforeEach
    void setUp() {
        processador = new ProcessadorFolhaPagamento();

        Setor setor = new Setor("RH001", "Recursos Humanos", "Administrativo");
        funcionario = new Funcionario(
                "João Silva",
                "123.456.789-00",
                LocalDate.of(1980, 5, 15),
                "F001",
                5000.0,
                LocalDate.of(2010, 3, 10),
                setor
        );

        folhaFrequencia = new FolhaFrequencia(funcionario, LocalDate.now().withDayOfMonth(1));
        folhaFrequencia.setHorasExtras(10);
        folhaFrequencia.setHorasNaoTrabalhadas(4);
    }

    @Test
    void deveCalcularSalarioCorretamente() {
        // Preparação dos valores esperados
        double salarioBase = 5000.0;
        double valorHorasExtras = funcionario.calcularValorHorasExtras(10);
        double descontosFaltas = (salarioBase / 220) * 4;
        double descontosGerais = salarioBase * 0.26; // 11% INSS + 15% IR
        double beneficios = salarioBase * 0.05;

        double salarioEsperado = salarioBase + valorHorasExtras - descontosFaltas - descontosGerais + beneficios;
        double salarioCalculado = processador.calcularSalario(funcionario, folhaFrequencia);

        assertEquals(salarioEsperado, salarioCalculado, 0.01);
    }

    @Test
    void deveCalcularSalarioGerenteComBonus() {
        ProcessadorFolhaPagamentoGerente processadorGerente = new ProcessadorFolhaPagamentoGerente();

        double salarioNormal = processador.calcularSalarioEspecifico(funcionario, folhaFrequencia);
        double salarioGerente = processadorGerente.calcularSalarioEspecifico(funcionario, folhaFrequencia);

        // O salário do gerente deve ser 10% maior
        assertEquals(salarioNormal * 1.1, salarioGerente, 0.01);
    }
}