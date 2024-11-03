package kmusz;

public class Klient {
    private String imie;
    private String nazwisko;
    private String nr_telefony;
    private String kod_rezerwacji;

    public Klient() {
    }

    public Klient(String imie, String nazwisko, String nr_telefony, String kod_rezerwacji) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefony = nr_telefony;
        this.kod_rezerwacji = kod_rezerwacji;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNr_telefony() {
        return nr_telefony;
    }

    public void setNr_telefony(String nr_telefony) {
        this.nr_telefony = nr_telefony;
    }

    public String getKod_rezerwacji() {
        return kod_rezerwacji;
    }

    public void setKod_rezerwacji(String kod_rezerwacji) {
        this.kod_rezerwacji = kod_rezerwacji;
    }
}
