package io.github.lucasmartins33.controledevalidade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import io.github.lucasmartins33.controledevalidade.model.Produto;
import io.github.lucasmartins33.controledevalidade.dao.ProdutoDAO;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList; // Para a lista da tabela
import javafx.collections.FXCollections; // Para criar a ObservableList
import javafx.beans.property.SimpleStringProperty; // Para configurar as colunas
import javafx.beans.property.SimpleIntegerProperty; // Para configurar a coluna de ID

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TableRow;

public class TelaPrincipalController {

    @FXML
    private Label labelDeStatus;

    @FXML
    private TextField campoNomeProduto;

    @FXML
    private TextField campoCodigoBarras;

    @FXML
    private DatePicker campoDataValidade;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, Integer> colunaId;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, String> colunaCodigoBarras;

    @FXML
    private TableColumn<Produto, LocalDate> colunaDataValidade;

    /**
     * Este é um método especial do JavaFX.
     * Ele é chamado AUTOMATICAMENTE, uma única vez,
     * logo depois de o FXML ser carregado (antes de a janela aparecer).
     * É o local perfeito para carregar dados iniciais!
     */

    // Criando um formatador de tabela
    private final DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        // 1. Configurar a Coluna de ID:
        // Diz à "colunaId" que ela deve buscar o atributo "id" do Produto.
        colunaId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject()
        );

        // 2. Configurar a Coluna de Nome:
        // Diz à "colunaNome" que ela deve buscar o atributo "nome" do Produto.
        colunaNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome())
        );

        // 3. Configurar Coluna de Código de Barras
        colunaCodigoBarras.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigoBarras())
        );

        // 4. Configurar Coluna de Data de Validade (com formatação)
        colunaDataValidade.setCellValueFactory(cellData ->
                cellData.getValue().dataValidadeProperty()
        );

        // Esta parte é um pouco mais avançada, mas faz a data parecer "dd/MM/yyyy"
        colunaDataValidade.setCellFactory(col -> new TableCell<Produto, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatadorDeData.format(item));
                }
            }
        });

        // Lógica para colorir as linhas da tabela
        tabelaProdutos.setRowFactory(tv -> new TableRow<Produto>(){

            @Override
            protected void updateItem(Produto item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {

                    LocalDate hoje = LocalDate.now();

                    LocalDate dataValidade = item.getDataValidade();
                    //Lógica das cores
                    if (dataValidade.isBefore(hoje)) {
                        // Vencido
                        setStyle("-fx-background-color: #ffcccc;");
                    } else if (dataValidade.isBefore(hoje.plusDays(7))) {
                        // Próximo de se vencer
                        setStyle("-fx-background-color: #fffbB0;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });



        // 5. Chamar o nosso novo método para carregar os dados
        carregarProdutosNaTabela();
    }

    public void carregarProdutosNaTabela() {
        System.out.println("Carregando produtos na tabela...");

        // 1. Criar um Mensageiro (DAO)
        ProdutoDAO meuDAO = new ProdutoDAO();

        // 2. Pedir ao Mensageiro a lista de todos os produtos salvos
        List<Produto> produtosSalvos = meuDAO.listarTodos();

        // 3. Converter a nossa "List" normal numa "ObservableList"
        // (A TableView só "enxerga" ObservableLists)
        ObservableList<Produto> listaObservavel = FXCollections.observableArrayList(produtosSalvos);

        // 4. Dizer à tabela para usar esta lista
        tabelaProdutos.setItems(listaObservavel);

        System.out.println("Tabela carregada com " + listaObservavel.size() + " itens.");
    }


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
            String codigoBarras = campoCodigoBarras.getText();
            LocalDate dataValidade = campoDataValidade.getValue();

            // Não deixamos salvar se os campos principais estiverem vazios
            if (nomeDoProduto.isEmpty() || dataValidade == null) {
                labelDeStatus.setText("Erro: Nome e Data de validade são obrigatórios");
                return;
            }

            // 2. Criar um "Molde" (Produto) e colocar o nome dentro
            Produto produtoParaSalvar = new Produto();
            produtoParaSalvar.setNome(nomeDoProduto);
            produtoParaSalvar.setCodigoBarras(codigoBarras);
            produtoParaSalvar.setDataValidade(dataValidade);

            // 3. Criar um "Mensageiro" (DAO)
            ProdutoDAO meuDAO = new ProdutoDAO();

            // 4. Mandar o Mensageiro salvar o Molde!
            meuDAO.salvar(produtoParaSalvar);

            // 5. Avisar o utilizador que deu certo
            labelDeStatus.setText("Produto " + nomeDoProduto + " salvo com sucesso!");

            // 6. Limpar o campo de texto para o próximo produto
            campoNomeProduto.clear();

            // Depois de salvar, mande a tabela recarregar!
            carregarProdutosNaTabela();
        } catch (Exception e) {
            // Se algo der errado (ex: falha no banco), avisamos o utilizador
            labelDeStatus.setText("Erro ao salvar o produto!");
            e.printStackTrace(); // Mostra o erro no console para nós
        }
    }

}
