package io.github.lucasmartins33.controledevalidade.model;
import java.time.LocalDate;

public class Produto {

    private int id;
    private String nome;
    private String codigoBarras;
    private LocalDate dataValidade;

    public Produto() {

    }

    public Produto(int id,String nome, String codigoBarras, LocalDate dataValidade) {
        this.id = id;
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.dataValidade = dataValidade;
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

    public boolean verificadorValidade(){
        LocalDate dataAtual = LocalDate.now();

        return dataAtual.isAfter(dataValidade);
    }

    @Override
    public String toString() {
        return "Produto [ID: " + id + ", Nome: " + nome + "]";
    }
}
