package br.com.peestrada.repository;

import java.util.List;

/**
 * Interface genérica para operações básicas de repositório
 * @param <T> Tipo de entidade
 * @param <ID> Tipo do identificador
 */
public interface Repository<T, ID> {

    /**
     * Salva uma entidade
     * @param entity entidade a ser salva
     * @return entidade salva
     */
    T salvar(T entity);

    /**
     * Busca uma entidade pelo ID
     * @param id identificador da entidade
     * @return entidade encontrada ou null
     */
    T buscarPorId(ID id);

    /**
     * Lista todas as entidades
     * @return lista de entidades
     */
    List<T> listarTodos();

    /**
     * Exclui uma entidade
     * @param entity entidade a ser excluída
     */
    void excluir(T entity);

    /**
     * Exclui uma entidade pelo ID
     * @param id identificador da entidade
     */
    void excluirPorId(ID id);
}