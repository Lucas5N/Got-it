package Model.Carta_di_Credito;

import java.util.Date;

public class CDCBean {
    private String Numero;
    private int ID_Utente;
    private String CVV;
    private Date Data_scadenza;

    public String getNumero() { return Numero; }
    public void setNumero(String numero) { this.Numero = numero; }

    public int getID_Utente() { return ID_Utente; }
    public void setID_Utente(int ID_Utente) { this.ID_Utente = ID_Utente; }

    public String getCVV() { return CVV; }
    public void setCVV(String CVV) { this.CVV = CVV; }

    public Date getData_scadenza() { return Data_scadenza; }
    public void setData_scadenza(Date data_scadenza) { this.Data_scadenza = data_scadenza; }
}
