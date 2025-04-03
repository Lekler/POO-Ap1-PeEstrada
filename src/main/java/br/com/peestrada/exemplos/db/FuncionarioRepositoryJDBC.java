package br.com.peestrada.exemplos.db;

/**
 * EXEMPLO NÃO FUNCIONAL - Apenas para referência
 * Demonstração de como seria um repositório com JDBC
 */

import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.model.pessoa.Funcionario;
import br.com.peestrada.repository.Repository;
import br.com.peestrada.repository.SetorRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de funcionários com banco de dados
 */
public class FuncionarioRepositoryJDBC implements Repository<Funcionario, String> {

    // Injeção de dependência do repositório de setores
    private final SetorRepository setorRepository;

    public FuncionarioRepositoryJDBC(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    @Override
    public Funcionario salvar(Funcionario funcionario) {
        String sql;

        // Verificar se já existe o funcionário (para decidir entre INSERT ou UPDATE)
        if (buscarPorId(funcionario.getMatricula()) == null) {
            // INSERT - Novo funcionário
            sql = "INSERT INTO funcionarios (matricula, nome, cpf, data_nascimento, " +
                    "salario_base, data_admissao, codigo_setor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            // UPDATE - Atualizar funcionário existente
            sql = "UPDATE funcionarios SET nome = ?, cpf = ?, data_nascimento = ?, " +
                    "salario_base = ?, data_admissao = ?, codigo_setor = ? WHERE matricula = ?";
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (sql.startsWith("INSERT")) {
                // Preencher parâmetros para INSERT
                stmt.setString(1, funcionario.getMatricula());
                stmt.setString(2, funcionario.getNome());
                stmt.setString(3, funcionario.getCpf());
                stmt.setDate(4, Date.valueOf(funcionario.getDataNascimento()));
                stmt.setDouble(5, funcionario.getSalarioBase());
                stmt.setDate(6, Date.valueOf(funcionario.getDataAdmissao()));
                stmt.setString(7, funcionario.getSetor().getCodigo());
            } else {
                // Preencher parâmetros para UPDATE
                stmt.setString(1, funcionario.getNome());
                stmt.setString(2, funcionario.getCpf());
                stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
                stmt.setDouble(4, funcionario.getSalarioBase());
                stmt.setDate(5, Date.valueOf(funcionario.getDataAdmissao()));
                stmt.setString(6, funcionario.getSetor().getCodigo());
                stmt.setString(7, funcionario.getMatricula());
            }

            stmt.executeUpdate();
            return funcionario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage(), e);
        }
    }

    @Override
    public Funcionario buscarPorId(String matricula) {
        String sql = "SELECT f.*, s.nome as setor_nome, s.departamento " +
                "FROM funcionarios f " +
                "JOIN setores s ON f.codigo_setor = s.codigo " +
                "WHERE f.matricula = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirFuncionarioDoResultSet(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Funcionario> listarTodos() {
        String sql = "SELECT f.*, s.nome as setor_nome, s.departamento " +
                "FROM funcionarios f " +
                "JOIN setores s ON f.codigo_setor = s.codigo";

        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                funcionarios.add(construirFuncionarioDoResultSet(rs));
            }

            return funcionarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(Funcionario funcionario) {
        if (funcionario != null && funcionario.getMatricula() != null) {
            excluirPorId(funcionario.getMatricula());
        }
    }

    @Override
    public void excluirPorId(String matricula) {
        String sql = "DELETE FROM funcionarios WHERE matricula = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir funcionário: " + e.getMessage(), e);
        }
    }

    /**
     * Busca funcionários por setor
     * @param codigoSetor código do setor
     * @return lista de funcionários do setor
     */
    public List<Funcionario> listarPorSetor(String codigoSetor) {
        String sql = "SELECT f.*, s.nome as setor_nome, s.departamento " +
                "FROM funcionarios f " +
                "JOIN setores s ON f.codigo_setor = s.codigo " +
                "WHERE f.codigo_setor = ?";

        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigoSetor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    funcionarios.add(construirFuncionarioDoResultSet(rs));
                }
            }

            return funcionarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários por setor: " + e.getMessage(), e);
        }
    }

    /**
     * Método auxiliar para construir um objeto Funcionario a partir do ResultSet
     */
    private Funcionario construirFuncionarioDoResultSet(ResultSet rs) throws SQLException {
        // Criar o setor
        Setor setor = new Setor(
                rs.getString("codigo_setor"),
                rs.getString("setor_nome"),
                rs.getString("departamento")
        );

        // Criar o funcionário
        Funcionario funcionario = new Funcionario(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getDate("data_nascimento").toLocalDate(),
                rs.getString("matricula"),
                rs.getDouble("salario_base"),
                rs.getDate("data_admissao").toLocalDate(),
                setor
        );

        return funcionario;
    }
}