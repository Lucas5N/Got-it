package Model.Carrello;

import java.util.Date;

public class CarrelloBean {
    private int ID_Utente;
    private Date Data_creazione;
    private int Numero_articoli;

    public int getID_Utente() { return ID_Utente; }
    public void setUtenteId(int ID_Utente) { this.ID_Utente = ID_Utente; }

    public Date getData_creazione() { return Data_creazione; }
    public void setData_creazione(Date Data_Creazione) { this.Data_creazione = Data_Creazione; }

    public int getNumero_articoli() { return Numero_articoli; }
    public void setNumero_articoli(int Numero_articoli) { this.Numero_articoli = Numero_articoli; }
}
