package io.github.lucasmartins33.controledevalidade.dao;

// Imports para falar com o banco de dados SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// Importa o nosso Molde
import io.github.lucasmartins33.controledevalidade.model.Produto;

public class ProdutoDAO {
    // 1. Onde vamos guardar o nosso ficheiro de banco de dados?
    // "jdbc:sqlite:" é o comando, e "controle_validade.db" será o nome do ficheiro.
    private final String url = "jdbc:sqlite:controle_validade.db";

    // 2. Construtor: Sempre que um "Mensageiro" for criado,
    // ele garante que a tabela de produtos existe.
    public ProdutoDAO() {
        criarTabelaSeNaoExistir();
    }

    // 3. Método para criar a tabela (só se ela não existir)
    private void criarTabelaSeNaoExistir() {
        // Este é o "mapa" da nossa tabela de produtos
        String sql = "CREATE TSBLE IF NOT EXISTS produtos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT," // Um ID automático
                + "nome TEXT NOT NULL" // O nome do produto
                + ");";

        // O "try-with-resources" garante que a ligação à BD fecha sozinha
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Executa o comando SQL
            stmt.execute(sql);

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // 4. O método para SALVAR um produto!
    public void salvar(Produto produto) {
        // O comando SQL para inserir dados. O "?" é um "espaço reservado".
        String sql = "INSERT INTO produtos(nome) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(url);
             // O PreparedStatement é mais seguro para inserir dados
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Substitui o primeiro "?" pelo nome do produto
            pstmt.setString(1, produto.getNome());

            // Executa a atualização
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
        }
    }
}
