package kmusz;

public class Zwierze {
    private int id;
    private String rodzaj;
    private String imie;
    private int wiek;
    private boolean czyZarezerwowany;
    private String kod_rezerwacji;

    public Zwierze() {
    }

    public Zwierze(int id, String rodzaj, String imie, int wiek, boolean czyZarezerwowany, String kod_rezerwacji) {
        this.id = id;
        this.rodzaj = rodzaj;
        this.imie = imie;
        this.wiek = wiek;
        this.czyZarezerwowany = czyZarezerwowany;
        this.kod_rezerwacji = kod_rezerwacji;
    }

    public Zwierze(int id, String rodzaj, String imie, int wiek, boolean czyZarezerwowany) {
        this.id = id;
        this.rodzaj = rodzaj;
        this.imie = imie;
        this.wiek = wiek;
        this.czyZarezerwowany = czyZarezerwowany;
    }
    public Zwierze(String rodzaj, String imie, int wiek, boolean czyZarezerwowany) {
        this.rodzaj = rodzaj;
        this.imie = imie;
        this.wiek = wiek;
        this.czyZarezerwowany = czyZarezerwowany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public boolean isCzyZarezerwowany() {
        return czyZarezerwowany;
    }

    public void setCzyZarezerwowany(boolean czyZarezerwowany) {
        this.czyZarezerwowany = czyZarezerwowany;
    }

    public String getKod_rezerwacji() {
        return kod_rezerwacji;
    }

    public void setKod_rezerwacji(String kod_rezerwacji) {
        this.kod_rezerwacji = kod_rezerwacji;
    }
}
