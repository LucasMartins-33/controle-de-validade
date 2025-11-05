package io.github.lucasmartins33.controledevalidade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import io.github.lucasmartins33.controledevalidade.model.Produto;
import io.github.lucasmartins33.controledevalidade.dao.ProdutoDAO;

public class TelaPrincipalController {

    @FXML
    private Label labelDeStatus;

    @FXML
    private TextField campoNomeProduto;

    /**
     * Esta anotação @FXML é o que permite que o FXML
     * encontre este método, mesmo ele sendo privado.
     * O nome "botaoFoiClicado" deve ser EXATAMENTE
     * igual ao que você digitou no 'On Action' do Scene Builder.
     */
    @FXML
    private void botaoFoiClicado(ActionEvent event) {
        // Esta é a sua lógica!
        //System.out.println("FUNCIONOU! O FXML se conectou ao Controller!");

        // Aparecendo na interface
        //labelDeStatus.setText("FUNCIONOU! O FXML se conectou ao Controller!");

        // 1. LER o texto do campo de entrada
        // Usamos o método .getText() para pegar o que o usuário digitou
        //String textoDigitado = campoNomeProduto.getText();

        // 2. ATUALIZAR o label com esse texto
        // Nós vamos construir uma nova frase
        //labelDeStatus.setText("Você digitou: " + textoDigitado);

        try {
            // 1. Pegar o texto da tela
            String nomeDoProduto = campoNomeProduto.getText();

            // 2. Criar um "Molde" (Produto) e colocar o nome dentro
            Produto produtoParaSalvar = new Produto();
            produtoParaSalvar.setNome(nomeDoProduto);

            // 3. Criar um "Mensageiro" (DAO)
            ProdutoDAO meuDAO = new ProdutoDAO();

            // 4. Mandar o Mensageiro salvar o Molde!
            meuDAO.salvar(produtoParaSalvar);

            // 5. Avisar o utilizador que deu certo
            labelDeStatus.setText("Produto " + nomeDoProduto + " salvo com sucesso!");

            // 6. Limpar o campo de texto para o próximo produto
            campoNomeProduto.clear();
        } catch (Exception e) {
            // Se algo der errado (ex: falha no banco), avisamos o utilizador
            labelDeStatus.setText("Erro ao salvar o produto!");
            e.printStackTrace(); // Mostra o erro no console para nós
        }
    }

}
