package kmusz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Inputy {
    Scanner scanner = new Scanner(System.in);

    public void konsola(){
        System.out.println("Schronisko wpisz (przegladaj) zeby zobaczyc dostepne zwierzeta,(szukaj) aby wyszukac konkretne zwierze, (rezerwuj) aby zarezerwowac zwierze, (dodaj) dodac zwierze do bazy danych, (usun) aby usunac zwierze, koniec(kończy prace z aplikajca) ");
        while (scanner.hasNextLine()){
            String linia = scanner.nextLine();

            switch (linia){
                case "koniec":
                    System.out.println("dziekuje");

                    break;
                case "przegladaj":
                        przegladaj();
                    break;
                case "szukaj":
                szukaj();
                    break;
                case "rezerwuj":
                    rezerwuj();
                    break;
                case "dodaj":
                    dodaj();
                    break;
                case "usun":

                    break;
                default:
                    System.out.println("wpisano nieznaza komende");
            }
        }

    }
    private void przegladaj() {
        try (Connection connection = LaczenieBaza.getConnection()) {
            String sql = "SELECT id, rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            List<Zwierze> zwierzeta = new ArrayList<>();

            while (rs.next()) {
                Zwierze zwierze = new Zwierze(
                        rs.getInt("id"),
                        rs.getString("rodzaj"),
                        rs.getString("imie"),
                        rs.getInt("wiek"),
                        rs.getBoolean("czy_zarezerwowany")
                );
                zwierzeta.add(zwierze);
            }
            for (Zwierze z : zwierzeta) {
                String czyZarezerwowanyTekst;
                if (z.isCzyZarezerwowany()){
                    czyZarezerwowanyTekst = "tak";
                }else{
                    czyZarezerwowanyTekst = "nie";
                }
                System.out.println("Rodzaj: " + z.getRodzaj() + ", Imię: " + z.getImie() + ", Wiek: " + z.getWiek() + ", Zarezerwowany: " + (czyZarezerwowanyTekst));
            }

        } catch (SQLException e) {
            System.out.println("Błąd podczas przeglądania zwierząt: " + e.getMessage());
        }
    }
    private void przegladajRezerwacja() {
        try (Connection connection = LaczenieBaza.getConnection()) {
            String sql = "SELECT id, rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze WHERE `czy_zarezerwowany` = 0;";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            List<Zwierze> zwierzeta = new ArrayList<>();

            while (rs.next()) {
                Zwierze zwierze = new Zwierze(
                        rs.getInt("id"),
                        rs.getString("rodzaj"),
                        rs.getString("imie"),
                        rs.getInt("wiek"),
                        rs.getBoolean("czy_zarezerwowany")
                );
                zwierzeta.add(zwierze);
            }
            for (Zwierze z : zwierzeta) {
                String czyZarezerwowanyTekst;
                if (z.isCzyZarezerwowany()){
                    czyZarezerwowanyTekst = "tak";
                }else{
                    czyZarezerwowanyTekst = "nie";
                }
                System.out.println("id: "+ z.getId() +", Rodzaj: " + z.getRodzaj() + ", Imię: " + z.getImie() + ", Wiek: " + z.getWiek() + ", Zarezerwowany: " + (czyZarezerwowanyTekst));
            }

        } catch (SQLException e) {
            System.out.println("Błąd podczas przeglądania zwierząt: " + e.getMessage());
        }
    }

    private void przegladajRezerwacjaKonkretneZwierze(String idZwierzaka) {
        try (Connection connection = LaczenieBaza.getConnection()) {
            String sql = "SELECT id, rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze where `id` =" + idZwierzaka + ";";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            List<Zwierze> zwierzeta = new ArrayList<>();

            while (rs.next()) {
                Zwierze zwierze = new Zwierze(
                        rs.getInt("id"),
                        rs.getString("rodzaj"),
                        rs.getString("imie"),
                        rs.getInt("wiek"),
                        rs.getBoolean("czy_zarezerwowany")
                );
                zwierzeta.add(zwierze);
            }
            for (Zwierze z : zwierzeta) {
                String czyZarezerwowanyTekst;
                if (z.isCzyZarezerwowany()){
                    czyZarezerwowanyTekst = "tak";
                }else{
                    czyZarezerwowanyTekst = "nie";
                }
                System.out.println("id: "+ z.getId() +", Rodzaj: " + z.getRodzaj() + ", Imię: " + z.getImie() + ", Wiek: " + z.getWiek() + ", Zarezerwowany: " + (czyZarezerwowanyTekst));
            }

        } catch (SQLException e) {
            System.out.println("Błąd podczas przeglądania zwierząt: " + e.getMessage());
        }
    }
    private void szukaj(){
        try{
            Connection connection = LaczenieBaza.getConnection();
            String slowo = scanner.nextLine();

            if (slowo != null){
                slowo.trim();
            }
            String sql = "SELECT rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze WHERE "
                    + "rodzaj = '" + slowo + "' "
                    + "OR imie = '" + slowo + "' "
                    + "OR wiek = '" + slowo + "' "
                    + "OR czy_zarezerwowany = '" + slowo + "'";

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()){
                String rodzaj = rs.getString("rodzaj").toLowerCase();
                String imie = rs.getString("imie").toLowerCase();
                String wiek = rs.getString("wiek").toLowerCase();
                String czy_zarezerwowany = rs.getString("czy_zarezerwowany").toLowerCase();

                String  wynik = "Rodzaj zwierzaka: " + rodzaj + ", " + "imie zwierzaka: " + imie + ", " + "wiek zwierzaka: " + wiek + ", " + "czy Zarezerwowany : " + czy_zarezerwowany;
                System.out.println(wynik);
            }

            connection.close();
            scanner.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private String generujKodRezerwacji(){
        return UUID.randomUUID().toString().toLowerCase().substring(0,8);
    }
    private void rezerwuj(){
        
        String kod = generujKodRezerwacji();

        try{
           Connection connection = LaczenieBaza.getConnection();
            System.out.println("Podaj imie Klienta: ");
            String imie = scanner.nextLine();
            System.out.println("Podaj nazwisko Klienta: ");
            String nazwisko = scanner.nextLine();
            System.out.println("Podaj numer telefonu Klienta: ");
            String nrTelefonu = scanner.nextLine();

            if (nrTelefonu.length() != 9){
                System.out.println("podano nie własciwy nr telefonu");
                return;
            }
            System.out.println("Zwierzeta w naszym schronisku");
            System.out.println("");
            przegladajRezerwacja();
            System.out.println("wpisz id zwierzaka ktore chcesz zarezerwowac");
            String id = scanner.nextLine().toLowerCase();
            System.out.println("czy napewno chcesz zarezerwowac to zwierze tak/nie");
            przegladajRezerwacjaKonkretneZwierze(id);
            String wybor = scanner.nextLine().toLowerCase().trim();
            if (wybor.equals("nie")){
//                System.out.println("wpisales nie");
                return;
            }else{
                Klient klient = new Klient(imie,nazwisko,nrTelefonu,kod);
                String sqlKlient = "INSERT INTO klient (imie, nazwisko, nr_telefonu, kod_rezerwacji) VALUES ('"+klient.getImie()+"', '"+klient.getNazwisko()+"', "+klient.getNr_telefony()+", '"+ klient.getKod_rezerwacji()+"')";
                String sqlUpdateZwierze = "UPDATE `zwierze` SET `czy_zarezerwowany`='1',`kod_rezerwacji`='"+klient.getKod_rezerwacji()+"' WHERE `id` ="+ id+";";
                Statement statementSqlUpdateZwierze = connection.createStatement();
                Statement statementKlient = connection.createStatement();
                statementKlient.executeUpdate(sqlKlient);
                statementSqlUpdateZwierze.executeUpdate(sqlUpdateZwierze);
                System.out.println("ZAREZERWOWANO");

            }

            connection.close();
            return;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void dodaj() {
        try {
            Connection connection = LaczenieBaza.getConnection();
            System.out.println("Jakie rodzaj zwierzecia chcesz dodać?");
            String rodzaj = scanner.nextLine().toLowerCase();
            System.out.println("podaj imie zwierzecia");
            String imie = scanner.nextLine();
            System.out.println("podaj wiek zwierzecia");
            String wiek = scanner.nextLine(); // Upewnij się, że to jest liczba całkowita.
            System.out.println("podaj czy zwierze jest zarezerwowane (true/false)");
            Boolean czyRezerwacja = scanner.nextBoolean();

            String sql = "INSERT INTO zwierze (rodzaj, imie, wiek, czy_zarezerwowany) VALUES "
                    + "('" + rodzaj + "', '" + imie + "', " + wiek + ", " + czyRezerwacja + ")";

            Statement stm = connection.createStatement();
            int czy_dodane = stm.executeUpdate(sql);

            if (czy_dodane > 0) {
                System.out.println("Dodano do bazy");
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania zwierzęcia: " + e.getMessage());
        }
    }

    private void usun(){

    }
}
