package Model.Wishlist;

import java.util.Date;

public class WishlistBean {
    private int ID_Utente;
    private String Nome;
    private Date Data_creazione;
    private int Numero_articoli;

    public int getID_Utente() { return ID_Utente; }
    public void setID_Utente(int ID_Utente) { this.ID_Utente = ID_Utente; }

    public String getNome() { return Nome; }
    public void setNome(String nome) { this.Nome = nome; }

    public Date getData_creazione() { return Data_creazione; }
    public void setData_creazione(Date data_creazione) { this.Data_creazione = data_creazione; }

    public int getNumero_articoli() { return Numero_articoli; }
    public void setNumero_articoli(int numero_articoli) { this.Numero_articoli = numero_articoli; }
}
