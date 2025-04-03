package br.com.peestrada.repository;

import br.com.peestrada.model.financeiro.Banco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório para gerenciar bancos
 */
public class BancoRepository implements Repository<Banco, String> {

    // Armazenamento em memória usando um Map
    private final Map<String, Banco> bancosMap = new HashMap<>();

    @Override
    public Banco salvar(Banco banco) {
        // Validar dados básicos
        if (banco.getCodigo() == null || banco.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código do banco não pode ser vazio");
        }

        // Armazenar no mapa usando o código como chave
        bancosMap.put(banco.getCodigo(), banco);
        return banco;
    }

    @Override
    public Banco buscarPorId(String codigo) {
        return bancosMap.get(codigo);
    }

    @Override
    public List<Banco> listarTodos() {
        return new ArrayList<>(bancosMap.values());
    }

    @Override
    public void excluir(Banco banco) {
        if (banco != null && banco.getCodigo() != null) {
            excluirPorId(banco.getCodigo());
        }
    }

    @Override
    public void excluirPorId(String codigo) {
        bancosMap.remove(codigo);
    }

    /**
     * Busca banco pelo nome
     * @param nome nome do banco
     * @return banco encontrado ou null
     */
    public Banco buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        for (Banco banco : bancosMap.values()) {
            if (nome.equalsIgnoreCase(banco.getNome())) {
                return banco;
            }
        }

        return null;
    }

    /**
     * Verifica se existe banco com o código informado
     * @param codigo código do banco
     * @return true se já existir
     */
    public boolean existePorCodigo(String codigo) {
        return buscarPorId(codigo) != null;
    }
}