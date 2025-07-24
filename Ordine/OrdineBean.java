package Model.Ordine;

import java.sql.Date;

public class OrdineBean {
    private int Codice;
    private int ID_Utente;
    private String Carta_di_credito;
    private Date Data_consegna;
    private Date Data_acquisto;
    private double Costo_consegna;
    private int Quantita;
    private String Indirizzo_di_consegna;
    private String Stato_consegna;

    public int getCodice() { return Codice; }
    public void setCodice(int codice) { this.Codice = codice; }

    public int getID_Utente() { return ID_Utente; }
    public void setID_Utente(int ID_Utente) { this.ID_Utente = ID_Utente; }

    public String getCarta_di_credito() { return Carta_di_credito; }
    public void setCarta_di_credito(String carta_di_credito) { this.Carta_di_credito = carta_di_credito; }

    public Date getData_consegna() { return Data_consegna; }
    public void setData_consegna(Date data_consegna) { this.Data_consegna = data_consegna; }

    public Date getData_acquisto() { return Data_acquisto; }
    public void setData_acquisto(Date data_acquisto) { this.Data_acquisto = data_acquisto; }

    public double getCosto_consegna() { return Costo_consegna; }
    public void setCosto_consegna(double costo_consegna) { this.Costo_consegna = costo_consegna; }

    public int getQuantita() { return Quantita; }
    public void setQuantita(int quantita) { this.Quantita = quantita; }

    public String getIndirizzo_di_consegna() { return Indirizzo_di_consegna; }
    public void setIndirizzo_di_consegna(String indirizzo_di_consegna) { this.Indirizzo_di_consegna = indirizzo_di_consegna; }

    public String getStato_consegna() { return Stato_consegna; }
    public void setStato_consegna(String stato_consegna) { this.Stato_consegna = stato_consegna; }
}
