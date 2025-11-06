package io.github.lucasmartins33.controledevalidade.dao;

// Imports para falar com o banco de dados SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// Importa o nosso Molde
import io.github.lucasmartins33.controledevalidade.model.Produto;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet; // Importante para ler os resultados

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

    // 5. O método para ler todos os produtos!
    public  List<Produto> listarTodos() {
        // 1. O comando SQL para selecionar tudo
        String sql = "SELECT id, nome FROM produtos";

        // 2. Criamos uma lista vazia onde vamos colocar os produtos
        List<Produto> produtosEncontrados = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             // 3. O ResultSet é a "tabela de resultados" que a BD nos devolve
             ResultSet rs = stmt.executeQuery(sql)) {

            // 4. Loop "enquanto houver uma próxima linha" na tabela de resultados
            while (rs.next()) {

                // 5. Criamos um novo Molde (Produto) vazio
                Produto produto = new Produto();

                // 6. Preenchemos o molde com os dados da linha atual
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));

                // 7. Adicionamos o produto preenchido à nossa lista
                produtosEncontrados.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        // 8. Devolvemos a lista completa (pode estar vazia se não houver produtos)
        return produtosEncontrados;
    }

}
