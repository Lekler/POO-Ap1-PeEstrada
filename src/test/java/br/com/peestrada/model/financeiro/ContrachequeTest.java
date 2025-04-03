package br.com.peestrada.model.financeiro;

import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.model.pessoa.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ContrachequeTest {

    private Contracheque contracheque;
    private Funcionario funcionario;
    private LocalDate mesReferencia;

    @BeforeEach
    void setUp() {
        // Configuração inicial para cada teste
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

        mesReferencia = LocalDate.now().withDayOfMonth(1);
        contracheque = new Contracheque(funcionario, mesReferencia);
    }

    @Test
    void deveAdicionarProventoCorretamente() {
        // Adicionar um provento
        contracheque.adicionarProvento("Salário Base", 5000.0);

        // Verificar se o salário bruto foi atualizado
        assertEquals(5000.0, contracheque.getSalarioBruto(), 0.01);
        // Verificar se o salário líquido foi atualizado (sem descontos ainda)
        assertEquals(5000.0, contracheque.getSalarioLiquido(), 0.01);
    }

    @Test
    void deveAdicionarDescontoCorretamente() {
        // Adicionar um salário base e um desconto
        contracheque.adicionarProvento("Salário Base", 5000.0);
        contracheque.adicionarDesconto("INSS", 550.0);

        // Verificar se o total de descontos foi atualizado
        assertEquals(550.0, contracheque.getTotalDescontos(), 0.01);
        // Verificar se o salário líquido foi atualizado corretamente
        assertEquals(4450.0, contracheque.getSalarioLiquido(), 0.01);
    }

    @Test
    void deveCalcularSalarioLiquidoCorretamente() {
        // Adicionar múltiplos proventos e descontos
        contracheque.adicionarProvento("Salário Base", 5000.0);
        contracheque.adicionarProvento("Horas Extras", 500.0);
        contracheque.adicionarProvento("Benefícios", 250.0);
        contracheque.adicionarDesconto("INSS", 550.0);
        contracheque.adicionarDesconto("IR", 750.0);
        contracheque.adicionarDesconto("Faltas", 200.0);

        // Verificar soma de proventos
        assertEquals(5750.0, contracheque.getSalarioBruto(), 0.01);
        // Verificar soma de descontos
        assertEquals(1500.0, contracheque.getTotalDescontos(), 0.01);
        // Verificar cálculo do salário líquido
        assertEquals(4250.0, contracheque.getSalarioLiquido(), 0.01);
    }

    @Test
    void deveImprimirContrachequeFormatadoCorretamente() {
        // Adicionar dados ao contracheque
        contracheque.adicionarProvento("Salário Base", 5000.0);
        contracheque.adicionarProvento("Horas Extras", 500.0);
        contracheque.adicionarDesconto("INSS", 550.0);
        contracheque.adicionarDesconto("IR", 750.0);

        // Obter a representação em string do contracheque
        String resultado = contracheque.imprimirContracheque();

        // Verificar se contém as informações básicas
        assertTrue(resultado.contains("Funcionário: João Silva"));
        assertTrue(resultado.contains("Matrícula: F001"));
        assertTrue(resultado.contains("Setor: Recursos Humanos"));

        // Verificar se contém os proventos
        assertTrue(resultado.contains("Salário Base: R$ 5000,00")
                || resultado.contains("Salário Base: R$ 5000.00"));
        assertTrue(resultado.contains("Horas Extras: R$ 500,00")
                || resultado.contains("Horas Extras: R$ 500.00"));

        // Verificar se contém os descontos
        assertTrue(resultado.contains("INSS: R$ 550,00")
                || resultado.contains("INSS: R$ 550.00"));
        assertTrue(resultado.contains("IR: R$ 750,00")
                || resultado.contains("IR: R$ 750.00"));

        // Verificar se contém os totais
        assertTrue(resultado.contains("Salário Bruto: R$ 5500,00")
                || resultado.contains("Salário Bruto: R$ 5500.00"));
        assertTrue(resultado.contains("Total de Descontos: R$ 1300,00")
                || resultado.contains("Total de Descontos: R$ 1300.00"));
        assertTrue(resultado.contains("Salário Líquido: R$ 4200,00")
                || resultado.contains("Salário Líquido: R$ 4200.00"));
    }

    @Test
    void deveMostrarDataDeReferenciaNaImpressao() {
        // Configurar contracheque com mês específico
        LocalDate mesEspecifico = LocalDate.of(2025, 3, 1);
        Contracheque contrachequeDataEspecifica = new Contracheque(funcionario, mesEspecifico);

        // Adicionar algum valor para ter o que imprimir
        contrachequeDataEspecifica.adicionarProvento("Salário Base", 5000.0);

        // Obter a representação em string
        String resultado = contrachequeDataEspecifica.imprimirContracheque();

        // Verificar se contém a data de referência no formato esperado (MM/YYYY)
        assertTrue(resultado.contains("Referência: 03/2025"));
    }
}