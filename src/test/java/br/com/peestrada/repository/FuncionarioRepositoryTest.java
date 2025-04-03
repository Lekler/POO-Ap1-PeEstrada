package br.com.peestrada.repository;

import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.model.pessoa.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FuncionarioRepositoryTest {

    private FuncionarioRepository repository;
    private Funcionario funcionario1;
    private Funcionario funcionario2;
    private Setor setor;

    @BeforeEach
    void setUp() {
        repository = new FuncionarioRepository();

        setor = new Setor("RH001", "Recursos Humanos", "Administrativo");

        funcionario1 = new Funcionario(
                "João Silva",
                "123.456.789-00",
                LocalDate.of(1980, 5, 15),
                "F001",
                5000.0,
                LocalDate.of(2010, 3, 10),
                setor
        );

        funcionario2 = new Funcionario(
                "Maria Souza",
                "987.654.321-00",
                LocalDate.of(1985, 8, 25),
                "F002",
                6000.0,
                LocalDate.of(2012, 7, 5),
                setor
        );
    }

    @Test
    void deveSalvarERecuperarFuncionario() {
        repository.salvar(funcionario1);

        Funcionario recuperado = repository.buscarPorId("F001");

        assertNotNull(recuperado);
        assertEquals("João Silva", recuperado.getNome());
        assertEquals("123.456.789-00", recuperado.getCpf());
    }

    @Test
    void deveListarTodosFuncionarios() {
        repository.salvar(funcionario1);
        repository.salvar(funcionario2);

        List<Funcionario> funcionarios = repository.listarTodos();

        assertEquals(2, funcionarios.size());
        assertTrue(funcionarios.contains(funcionario1));
        assertTrue(funcionarios.contains(funcionario2));
    }

    @Test
    void deveExcluirFuncionario() {
        repository.salvar(funcionario1);
        repository.salvar(funcionario2);

        repository.excluir(funcionario1);

        List<Funcionario> funcionarios = repository.listarTodos();
        assertEquals(1, funcionarios.size());
        assertFalse(funcionarios.contains(funcionario1));
        assertTrue(funcionarios.contains(funcionario2));
    }

    @Test
    void deveBuscarPorCpf() {
        repository.salvar(funcionario1);
        repository.salvar(funcionario2);

        Funcionario resultado = repository.buscarPorCpf("123.456.789-00");

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void deveVerificarExistenciaPorCpf() {
        repository.salvar(funcionario1);

        assertTrue(repository.existePorCpf("123.456.789-00"));
        assertFalse(repository.existePorCpf("111.222.333-44"));
    }
}