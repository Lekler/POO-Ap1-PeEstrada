package br.com.peestrada.exemplos.db;

import br.com.peestrada.model.financeiro.Banco;
import br.com.peestrada.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EXEMPLO NÃO FUNCIONAL - Apenas para referência
 * Implementação do repositório de bancos com JDBC
 */
public class BancoRepositoryJDBC implements Repository<Banco, String> {

    @Override
    public Banco salvar(Banco banco) {
        String sql;

        // Verificar se já existe o banco (para decidir entre INSERT ou UPDATE)
        if (buscarPorId(banco.getCodigo()) == null) {
            // INSERT - Novo banco
            sql = "INSERT INTO bancos (codigo, nome) VALUES (?, ?)";
        } else {
            // UPDATE - Atualizar banco existente
            sql = "UPDATE bancos SET nome = ? WHERE codigo = ?";
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (sql.startsWith("INSERT")) {
                // Preencher parâmetros para INSERT
                stmt.setString(1, banco.getCodigo());
                stmt.setString(2, banco.getNome());
            } else {
                // Preencher parâmetros para UPDATE
                stmt.setString(1, banco.getNome());
                stmt.setString(2, banco.getCodigo());
            }

            stmt.executeUpdate();
            return banco;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar banco: " + e.getMessage(), e);
        }
    }

    @Override
    public Banco buscarPorId(String codigo) {
        String sql = "SELECT * FROM bancos WHERE codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Banco(
                            rs.getString("codigo"),
                            rs.getString("nome")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar banco: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Banco> listarTodos() {
        String sql = "SELECT * FROM bancos ORDER BY nome";
        List<Banco> bancos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bancos.add(new Banco(
                        rs.getString("codigo"),
                        rs.getString("nome")
                ));
            }

            return bancos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar bancos: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(Banco banco) {
        if (banco != null && banco.getCodigo() != null) {
            excluirPorId(banco.getCodigo());
        }
    }

    @Override
    public void excluirPorId(String codigo) {
        // Verificar se há contas associadas antes de excluir
        String sqlVerificar = "SELECT COUNT(*) FROM contas_correntes WHERE banco_codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlVerificar)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Não é possível excluir o banco pois existem contas associadas");
                }
            }

            // Se não tem contas associadas, pode excluir
            String sqlExcluir = "DELETE FROM bancos WHERE codigo = ?";

            try (PreparedStatement stmtExcluir = conn.prepareStatement(sqlExcluir)) {
                stmtExcluir.setString(1, codigo);
                stmtExcluir.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir banco: " + e.getMessage(), e);
        }
    }

    /**
     * Busca banco pelo nome ou parte do nome
     * @param termo termo a ser buscado
     * @return lista de bancos que contêm o termo no nome
     */
    public List<Banco> buscarPorNome(String termo) {
        String sql = "SELECT * FROM bancos WHERE nome LIKE ? ORDER BY nome";
        List<Banco> bancos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + termo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bancos.add(new Banco(
                            rs.getString("codigo"),
                            rs.getString("nome")
                    ));
                }
            }

            return bancos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar bancos por nome: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se existe um banco com o código informado
     * @param codigo código a ser verificado
     * @return true se existir, false caso contrário
     */
    public boolean existePorCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM bancos WHERE codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de banco: " + e.getMessage(), e);
        }
    }
}