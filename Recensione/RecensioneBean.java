package Model.Recensione;

import java.util.Date;

public class RecensioneBean {
    private int ID_Utente;
    private int Prodotto_Codice;
    private int Voto;
    private Date Data;
    private String Descrizione;

    public int getID_Utente() { return ID_Utente; }
    public void setID_Utente(int ID_Utente) { this.ID_Utente = ID_Utente; }

    public int getProdotto_Codice() { return Prodotto_Codice; }
    public void setProdotto_Codice(int prodotto_Codice) { this.Prodotto_Codice = prodotto_Codice; }

    public int getVoto() { return Voto; }
    public void setVoto(int voto) { this.Voto = voto; }

    public Date getData() { return Data; }
    public void setData(Date data) { this.Data = data; }

    public String getDescrizione() { return Descrizione; }
    public void setDescrizione(String descrizione) { this.Descrizione = descrizione; }
}
