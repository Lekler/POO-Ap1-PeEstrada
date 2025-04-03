package br.com.peestrada.repository;

import br.com.peestrada.model.empresa.Setor;

/**
 * Interface para repositório de setores
 */
public interface SetorRepository extends Repository<Setor, String> {

    /**
     * Busca setor pelo nome
     * @param nome nome do setor
     * @return setor encontrado ou null
     */
    Setor buscarPorNome(String nome);

    /**
     * Busca setores por departamento
     * @param departamento nome do departamento
     * @return lista de setores do departamento
     */
    java.util.List<Setor> listarPorDepartamento(String departamento);
}