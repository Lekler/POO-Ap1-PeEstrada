package br.com.peestrada.model.pessoa;

import br.com.peestrada.model.empresa.Setor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FuncionarioTest {

    private Funcionario funcionario;
    private Setor setor;

    @BeforeEach
    void setUp() {
        // Este método é executado antes de cada teste
        setor = new Setor("RH001", "Recursos Humanos", "Administrativo");
        funcionario = new Funcionario(
                "João Silva",
                "123.456.789-00",
                LocalDate.of(1980, 5, 15),
                "F001",
                5000.0,
                LocalDate.of(2010, 3, 10),
                setor
        );
    }

    @Test
    void deveCalcularValorHorasExtrasCorretamente() {
        // Teste do cálculo de horas extras (sobrecarga 1)
        double valorEsperado = (5000.0 / 220) * 10 * 1.5;
        double valorCalculado = funcionario.calcularValorHorasExtras(10);
        assertEquals(valorEsperado, valorCalculado, 0.01);

        // Teste do cálculo de horas extras (sobrecarga 2)
        double percentualPersonalizado = 2.0; // 200%
        valorEsperado = (5000.0 / 220) * 10 * (1 + percentualPersonalizado);
        valorCalculado = funcionario.calcularValorHorasExtras(10, percentualPersonalizado);
        assertEquals(valorEsperado, valorCalculado, 0.01);
    }

    @Test
    void deveGerarIdentificacaoCorretamente() {
        String identificacaoEsperada = "Funcionário: João Silva - Matrícula: F001";
        String identificacaoGerada = funcionario.gerarIdentificacao();
        assertEquals(identificacaoEsperada, identificacaoGerada);
    }

    @Test
    void deveLancarExcecaoQuandoSalarioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            funcionario.setSalarioBase(-1000.0);
        });
    }
}