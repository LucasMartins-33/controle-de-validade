package io.github.lucasmartins33.controledevalidade.model;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Produto {

    private int id;
    private String nome;
    private String codigoBarras;
    private LocalDate dataValidade;
    private int quantidade;

    public Produto() {

    }

    public Produto(int id,String nome, String codigoBarras, LocalDate dataValidade, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.dataValidade = dataValidade;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidade() { return quantidade;}

    public void setQuantidade(int quantidade) { this.quantidade = quantidade;}

    public boolean verificadorValidade(){
        LocalDate dataAtual = LocalDate.now();

        return dataAtual.isAfter(dataValidade);
    }

    public ObjectProperty<LocalDate> dataValidadeProperty() {
        return new SimpleObjectProperty<>(this.dataValidade);
    }

    @Override
    public String toString() {
        return "Produto [ID: " + id +
                ", Nome: " + nome +
                ", Código" + codigoBarras +
                ", Validade" + dataValidade +
                ", Quantidade" + quantidade +
                "]";
    }
}
