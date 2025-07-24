package Model.Prodotto;

import java.math.BigDecimal;
import java.util.List;

public class ProdottoBean {
    private int codice;
    private String nome;
    private double prezzo;
    private double prezzoScontato;
    private String descrizione;
    private double peso;
    private String colore;
    private BigDecimal media_recensioni;
    private boolean isDeleted;
    private String img_path;
    private double rilevanza;
    private String categoria;
    private String condizioni;

    public int getDiscounted() {
        return discounted;
    }

    public void setDiscounted(int discounted) {
        this.discounted = discounted;
    }

    private int discounted;


    public ProdottoBean(){}

    public ProdottoBean(int codice,String nome,String descrizione,double prezzo){
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setImg_path(String path){this.img_path = path;}

    public String getImg_path(){return this.img_path;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getColore() {return colore;}

    public void setColore(String colore) {this.colore = colore;}

    public BigDecimal getMedia_recensioni() {
        return media_recensioni;
    }

    public void setMedia_recensioni(BigDecimal media_recensioni) {
        this.media_recensioni = media_recensioni;
    }

    public void setDeleted(boolean isDeleted){this.isDeleted = isDeleted;}

    public boolean getDeleted(){return this.isDeleted;}

    public double getPrezzoScontato() {
        return prezzoScontato;
    }

    public void setNewPrezzo(double prezzoScontato) {
        this.prezzoScontato = prezzoScontato;
    }

    public double getRilevanza() {
        return rilevanza;
    }

    public void setRilevanza(double rilevanza) {
        this.rilevanza = rilevanza;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCondizioni() {
        return condizioni;
    }

    public void setCondizioni(String condizioni) {
        this.condizioni = condizioni;
    }
}