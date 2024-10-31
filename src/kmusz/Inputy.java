package kmusz;

import java.sql.*;
import java.util.Scanner;

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
    private void przegladaj(){
        try{
            Connection connection = LaczenieBaza.getConnection();
            String sql = "SELECT `rodzaj`,`imie`,`wiek`,`czy_zarezerwowany` FROM `zwierze`; ";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                String rodzaj = rs.getString("rodzaj").toLowerCase();
                String imie = rs.getString("imie").toLowerCase();
                String wiek = rs.getString("wiek").toLowerCase();
                String czy_zarezerwowany = rs.getString("czy_zarezerwowany");
                    System.out.println("Rodzaj zwierzaka: " + rodzaj + ", " + "imie zwierzaka: " + imie + ", " + "czy Zarezerwowany : " + czy_zarezerwowany);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void rezerwuj(){

    }
    private void dodaj(){
        try{
            Connection connection = LaczenieBaza.getConnection();
            Scanner ScannerDodaj = new Scanner(System.in);
            System.out.println("Jakie rodzaj zwierzecia chcesz dodać?");
            String rodzaj = ScannerDodaj.nextLine().toLowerCase();
            System.out.println("podaj imie zwierzecia");
            String imie = ScannerDodaj.nextLine();
            System.out.println("podaj wiek zwierzecia");
            String wiek = ScannerDodaj.nextLine().toLowerCase();
            System.out.println("podaj czy zwierze jest zarezerwowane (true/false)");
            Boolean czyRezerwacja = ScannerDodaj.nextBoolean();

            String sql = "INSERT INTO zwierze (rodzaj, imie, wiek, czy_zarezerwowany) VALUES "
                    + "('" + rodzaj + "', '" + imie + "', " + wiek + ", " + czyRezerwacja + ")";

            Statement stm = connection.createStatement();

            int czy_dodane = stm.executeUpdate(sql);
                if(czy_dodane > 0){
                    System.out.println("dodano do bazy");

                }

                ScannerDodaj.close();
                connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void usun(){

    }
}
