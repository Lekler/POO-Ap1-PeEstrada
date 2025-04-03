package br.com.peestrada.exemplos.db;

/**
 * EXEMPLO NÃO FUNCIONAL - Apenas para referência
 * Classe que demonstra como seria a conexão com banco de dados
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar conexões com o banco de dados
 */
public class ConnectionFactory {

    // Dados de conexão com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/peestrada";
    private static final String USER = "root";
    private static final String PASSWORD = "senha123";

    /**
     * Obtém uma conexão com o banco de dados
     * @return conexão com o banco
     * @throws SQLException em caso de erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Carregar o driver do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Criar e retornar conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado", e);
        }
    }
}