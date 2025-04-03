package br.com.peestrada.exemplos.db;

import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.repository.SetorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EXEMPLO NÃO FUNCIONAL - Apenas para referência
 * Implementação do repositório de setores com JDBC
 */
public class SetorRepositoryJDBC implements SetorRepository {

    @Override
    public Setor salvar(Setor setor) {
        String sql;

        // Verificar se já existe o setor (para decidir entre INSERT ou UPDATE)
        if (buscarPorId(setor.getCodigo()) == null) {
            // INSERT - Novo setor
            sql = "INSERT INTO setores (codigo, nome, departamento) VALUES (?, ?, ?)";
        } else {
            // UPDATE - Atualizar setor existente
            sql = "UPDATE setores SET nome = ?, departamento = ? WHERE codigo = ?";
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (sql.startsWith("INSERT")) {
                // Preencher parâmetros para INSERT
                stmt.setString(1, setor.getCodigo());
                stmt.setString(2, setor.getNome());
                stmt.setString(3, setor.getDepartamento());
            } else {
                // Preencher parâmetros para UPDATE
                stmt.setString(1, setor.getNome());
                stmt.setString(2, setor.getDepartamento());
                stmt.setString(3, setor.getCodigo());
            }

            stmt.executeUpdate();
            return setor;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar setor: " + e.getMessage(), e);
        }
    }

    @Override
    public Setor buscarPorId(String codigo) {
        String sql = "SELECT * FROM setores WHERE codigo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Setor(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("departamento")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar setor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Setor> listarTodos() {
        String sql = "SELECT * FROM setores ORDER BY nome";
        List<Setor> setores = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                setores.add(new Setor(
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("departamento")
                ));
            }

            return setores;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar setores: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(Setor setor) {
        if (setor != null && setor.getCodigo() != null) {
            excluirPorId(setor.getCodigo());
        }
    }

    @Override
    public void excluirPorId(String codigo) {
        // Verificar se há funcionários associados antes de excluir
        String sqlVerificar = "SELECT COUNT(*) FROM funcionarios WHERE codigo_setor = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlVerificar)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Não é possível excluir o setor pois existem funcionários associados");
                }
            }

            // Se não tem funcionários associados, pode excluir
            String sqlExcluir = "DELETE FROM setores WHERE codigo = ?";

            try (PreparedStatement stmtExcluir = conn.prepareStatement(sqlExcluir)) {
                stmtExcluir.setString(1, codigo);
                stmtExcluir.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir setor: " + e.getMessage(), e);
        }
    }

    @Override
    public Setor buscarPorNome(String nome) {
        String sql = "SELECT * FROM setores WHERE nome = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Setor(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("departamento")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar setor por nome: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Setor> listarPorDepartamento(String departamento) {
        String sql = "SELECT * FROM setores WHERE departamento = ? ORDER BY nome";
        List<Setor> setores = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, departamento);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    setores.add(new Setor(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("departamento")
                    ));
                }
            }

            return setores;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar setores por departamento: " + e.getMessage(), e);
        }
    }
}