package br.com.peestrada.repository;

import br.com.peestrada.model.pessoa.Funcionario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório para gerenciar funcionários
 */
public class FuncionarioRepository implements Repository<Funcionario, String> {

    // Armazenamento em memória usando um Map
    private final Map<String, Funcionario> funcionariosMap = new HashMap<>();

    @Override
    public Funcionario salvar(Funcionario funcionario) {
        // Validar dados básicos
        if (funcionario.getMatricula() == null || funcionario.getMatricula().trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula do funcionário não pode ser vazia");
        }

        // Armazenar no mapa usando a matrícula como chave
        funcionariosMap.put(funcionario.getMatricula(), funcionario);
        return funcionario;
    }

    @Override
    public Funcionario buscarPorId(String matricula) {
        return funcionariosMap.get(matricula);
    }

    @Override
    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionariosMap.values());
    }

    @Override
    public void excluir(Funcionario funcionario) {
        if (funcionario != null && funcionario.getMatricula() != null) {
            excluirPorId(funcionario.getMatricula());
        }
    }

    @Override
    public void excluirPorId(String matricula) {
        funcionariosMap.remove(matricula);
    }

    /**
     * Busca funcionário por CPF
     * @param cpf CPF do funcionário
     * @return funcionário encontrado ou null
     */
    public Funcionario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }

        // Percorre a coleção procurando pelo CPF
        for (Funcionario funcionario : funcionariosMap.values()) {
            if (cpf.equals(funcionario.getCpf())) {
                return funcionario;
            }
        }

        return null;
    }

    /**
     * Lista funcionários por setor
     * @param codigoSetor código do setor
     * @return lista de funcionários do setor
     */
    public List<Funcionario> listarPorSetor(String codigoSetor) {
        if (codigoSetor == null) {
            return new ArrayList<>();
        }

        List<Funcionario> resultado = new ArrayList<>();

        for (Funcionario funcionario : funcionariosMap.values()) {
            if (funcionario.getSetor() != null &&
                    codigoSetor.equals(funcionario.getSetor().getCodigo())) {
                resultado.add(funcionario);
            }
        }

        return resultado;
    }

    /**
     * Verifica se já existe funcionário com o CPF informado
     * @param cpf CPF a verificar
     * @return true se já existir
     */
    public boolean existePorCpf(String cpf) {
        return buscarPorCpf(cpf) != null;
    }
}