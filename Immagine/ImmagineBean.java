package Model.Immagine;

public class ImmagineBean {
    private int codice;
    private String path;


    public ImmagineBean() {}
    public ImmagineBean(int codice, String path) {
        this.codice = codice;
        this.path = path;
    }

    public int getCodice() {return codice;}
    public String getPath() {return path;}

    public void setPath(String path) { this.path = path; }
    public void setCodice(int codice) { this.codice = codice; }
}
