package io.github.lucasmartins33.controledevalidade.controller;

import io.github.lucasmartins33.controledevalidade.App; // Importa a classe App para obter recursos
import io.github.lucasmartins33.controledevalidade.dao.ProdutoDAO;
import io.github.lucasmartins33.controledevalidade.model.Produto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaPrincipalController {

    @FXML
    private TableView<Produto> tableProduto;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, String> colCodigoBarras;

    @FXML
    private TableColumn<Produto, String> colDataValidade;

    @FXML
    private TableColumn<Produto, String> colStatus;

    @FXML
    private Button botaoAdicionarProduto;

    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> listaDeProdutos;

    @FXML
    public void initialize() {
        produtoDAO = new ProdutoDAO();

        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colCodigoBarras.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoBarras()));

        colDataValidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataValidade().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        colStatus.setCellValueFactory(cellData -> {
            Produto produto = cellData.getValue();
            if (produto.verificadorValidade()) {
                return new SimpleStringProperty("VENCIDO");
            } else {
                // Podemos adicionar lógica para "Vence em breve" aqui no futuro
                return new SimpleStringProperty("OK");
            }
        });

        tableProduto.setRowFactory(tv -> new TableRow<Produto>(){
            @Override
            protected void updateItem(Produto item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.verificadorValidade()) {
                    setStyle("-fx-background-color: #ffcccc;");
                } else{
                    setStyle("");
                }
            }
        });

    }

}
