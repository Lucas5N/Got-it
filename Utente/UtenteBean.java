package Model.Utente;

public class UtenteBean {
    private int ID_Utente;
    private String Nome;
    private String Cognome;
    private String Telefono;
    private String Email;
    private String Username;
    private String Password;
    private String Stato;
    private String Regione;
    private String Citta;
    private String Via;
    private String Numero_civico;
    private String Conto_corrente_addebbitabile;
    private boolean Amministratore;

    public int getID_Utente() { return ID_Utente; }
    public void setID_Utente(int ID_Utente) { this.ID_Utente = ID_Utente; }

    public String getNome() { return Nome; }
    public void setNome(String nome) { this.Nome = nome; }

    public String getCognome() { return Cognome; }
    public void setCognome(String cognome) { this.Cognome = cognome; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public String getUsername() { return Username; }
    public void setUsername(String username) { this.Username = username; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { this.Password = password; }

    public String getStato() { return Stato; }
    public void setStato(String stato) { this.Stato = stato; }

    public String getRegione() { return Regione; }
    public void setRegione(String regione) { this.Regione = regione; }

    public String getCitta() { return Citta; }
    public void setCitta(String citta) { this.Citta = citta; }

    public String getVia() { return Via; }
    public void setVia(String via) { this.Via = via; }

    public String getNumero_civico() { return Numero_civico; }
    public void setNumero_civico(String numero_civico) { this.Numero_civico = numero_civico; }

    public String getConto_corrente_addebbitabile() { return Conto_corrente_addebbitabile; }
    public void setConto_corrente_addebbitabile(String conto_corrente_addebbitabile) { this.Conto_corrente_addebbitabile = conto_corrente_addebbitabile; }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }

    public String getTelefono() { return Telefono; }

    public void setAmministratore(boolean Amministratore){this.Amministratore = Amministratore;}
    public boolean getAmministratore(){return this.Amministratore;}
}
