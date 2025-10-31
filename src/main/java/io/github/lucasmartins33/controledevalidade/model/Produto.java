package io.github.lucasmartins33.controledevalidade.model;
import java.time.LocalDate;

public class Produto {

    private String nome;
    private String codigoBarras;
    private LocalDate dataValidade;

    public Produto() {

    }

    public Produto(String nome, String codigoBarras, LocalDate dataValidade) {
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.dataValidade = dataValidade;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome (String nome){
        this.nome = nome;
    }

    public String getCodigoBarras(){
        return this.codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras){
        this.codigoBarras = codigoBarras;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade){
        this.dataValidade = dataValidade;
    }

    public boolean verificadorValidade(){
        LocalDate dataAtual = LocalDate.now();

        return dataAtual.isAfter(dataValidade);
    }
}
