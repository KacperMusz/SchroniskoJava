package kmusz;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class Inputy {
    Scanner scanner = new Scanner(System.in);

    public void konsola(){
        String wiadomosc = "System.out.println(\"Schronisko wpisz (przegladaj) zeby zobaczyc dostepne zwierzeta,(szukaj) aby wyszukac konkretne zwierze, (rezerwuj) aby zarezerwowac zwierze, (dodaj) dodac zwierze do bazy danych, (usun) aby usunac zwierze, koniec(kończy prace z aplikajca) \");";
        System.out.println(wiadomosc);
        while (scanner.hasNextLine()){
            String linia = scanner.nextLine();

            switch (linia){
                case "koniec":
                    System.out.println("dziekuje");
                    return;

                case "przegladaj":
                        przegladaj();
                    System.out.println(wiadomosc);
                    break;

                case "szukaj":
                        szukaj();
                    System.out.println(wiadomosc);
                    break;
                case "rezerwuj":
                        rezerwuj();
                    System.out.println(wiadomosc);
                    break;
                case "dodaj":
                        dodaj();
                    System.out.println(wiadomosc);
                    break;
                case "usun":
                    usun();
                    System.out.println(wiadomosc);
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

    private void przegladajKonkretneZwierze(String idZwierzaka) {
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
        System.out.println("Szukaj zwierzat: ");
        String slowo = scanner.nextLine();

        if (slowo != null){
            slowo.trim();
        }
        String sql;
        if (slowo.equals("nie")){
            sql = "SELECT rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze WHERE "
                + "rodzaj LIKE '"+slowo+"' OR imie LIKE '"+slowo+"' OR wiek LIKE '"+slowo+"' OR czy_zarezerwowany = 0;";
        } else if (slowo.equals("tak")) {
            sql = "SELECT rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze WHERE "
                    + "rodzaj LIKE '"+slowo+"' OR imie LIKE '"+slowo+"' OR wiek LIKE '"+slowo+"' OR czy_zarezerwowany = 1;";
        }else {
            sql = "SELECT rodzaj, imie, wiek, czy_zarezerwowany FROM zwierze WHERE "
                + "rodzaj LIKE '"+slowo+"' OR imie LIKE '"+slowo+"' OR wiek LIKE '"+slowo+"' OR czy_zarezerwowany LIKE '"+slowo+"';";
        }



        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        List<Zwierze> zwierzeArrayList = new ArrayList<>();
        while (rs.next()){
            Zwierze zwierze = new Zwierze(
                    rs.getString("rodzaj"),
                    rs.getString("imie"),
                    rs.getInt("wiek"),
                    rs.getBoolean("czy_zarezerwowany")
            );
            zwierzeArrayList.add(zwierze);
            String czyZarezerwowanyTekst = "";
            for(Zwierze z: zwierzeArrayList){
                if (z.isCzyZarezerwowany()){
                    czyZarezerwowanyTekst = "tak";
                }else{
                    czyZarezerwowanyTekst= "nie";
                }
            }
            String  wynik = "Rodzaj zwierzaka: " + zwierze.getRodzaj() + ", " + "imie zwierzaka: " + zwierze.getImie() + ", " + "wiek zwierzaka: " + zwierze.getWiek() + ", " + "czy Zarezerwowany : " + czyZarezerwowanyTekst;
            System.out.println(wynik);
        }

        connection.close();
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
            System.out.println("\n");
            przegladajRezerwacja();
            System.out.println("wpisz id zwierzaka ktore chcesz zarezerwowac");
            String id = scanner.nextLine().toLowerCase();
            System.out.println("czy napewno chcesz zarezerwowac to zwierze tak/nie");
            przegladajKonkretneZwierze(id);
            String wybor = scanner.nextLine().toLowerCase().trim();
            if (wybor.equals("nie")){
                return;
            }else if(wybor.equals("tak")){
                Klient klient = new Klient(imie,nazwisko,nrTelefonu,kod);
                String sqlKlient = "INSERT INTO klient (imie, nazwisko, nr_telefonu, kod_rezerwacji) VALUES ('"+klient.getImie()+"', '"+klient.getNazwisko()+"', "+klient.getNr_telefony()+", '"+ klient.getKod_rezerwacji()+"')";
                String sqlUpdateZwierze = "UPDATE `zwierze` SET `czy_zarezerwowany`='1',`kod_rezerwacji`='"+klient.getKod_rezerwacji()+"' WHERE `id` ="+ id+";";
                Statement statementSqlUpdateZwierze = connection.createStatement();
                Statement statementKlient = connection.createStatement();
                statementKlient.executeUpdate(sqlKlient);
                statementSqlUpdateZwierze.executeUpdate(sqlUpdateZwierze);
                System.out.println("ZAREZERWOWANO");

            }else{
                System.out.println("nie znana komenda");
            }

            connection.close();

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
            String imie = scanner.nextLine().toLowerCase();
            System.out.println("podaj wiek zwierzecia");
            String wiek = scanner.nextLine().toLowerCase();
            System.out.println("podaj czy zwierze jest zarezerwowane (true/false)");
            boolean czyRezerwacja = scanner.nextBoolean();

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
        try (Connection connection = LaczenieBaza.getConnection()){
            System.out.println("Usuwanie zwierzat" +
                    "\n(jakby co to login admina to login a haslo to haslo)");
            System.out.println("podaj login");
            String login = scanner.nextLine();
            System.out.println("podaj haslo");
            String haslo = scanner.nextLine();
            String hashed = hashowanie(haslo);
            Statement statement = connection.createStatement();
            String sql = "SELECT zwierze.id, zwierze.rodzaj, zwierze.imie, zwierze.wiek,zwierze.czy_zarezerwowany,zwierze.kod_rezerwacji FROM zwierze, admin WHERE 1 AND admin.login = '"+login+"' AND admin.haslo = '"+hashed+"';";

            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Zwierze> zwierzeUsun = new ArrayList<>();

            while (rs.next()){
                Zwierze zwierze = new Zwierze(
                  rs.getInt("id"),
                  rs.getString("rodzaj"),
                  rs.getString("imie"),
                  rs.getInt("wiek"),
                  rs.getBoolean("czy_zarezerwowany"),
                  rs.getString("kod_rezerwacji")
                );
                zwierzeUsun.add(zwierze);
            }
            for (Zwierze z: zwierzeUsun){
                String wynik = "id: " + z.getId() + ", "
                        + "rodzaj: " + z.getRodzaj() + ", "
                        + "imie: " + z.getImie() + ", "
                        + "wiek: " + z.getWiek() + ", "
                        + "czy zarezerwowany : " + z.isCzyZarezerwowany() + ", "
                        + "kod rezerwacji : " + z.getKod_rezerwacji();
                System.out.println(wynik);
            }
            System.out.println("podaj id zwierzecia do usuniecia");
            String id = scanner.nextLine();
            System.out.println("czy napewno chcesz usunac to zwierze? (tak/nie)");
            przegladajKonkretneZwierze(id);
            String wybor = scanner.nextLine();
            wybor.trim().toLowerCase();
            if (wybor.equals("nie")){
                System.out.println("nie usunieto");
            }else if (wybor.equals("tak")){
                String sqlUsun = "DELETE FROM `zwierze` WHERE `id` = " + id + ";";
                Statement statementUsun = connection.createStatement();
                int resultSetUsun = statementUsun.executeUpdate(sqlUsun);

                if (resultSetUsun > 0) {
                    System.out.println("usunieto Zwierze o id: " + id);
                } else {
                    System.out.println("blad");
                }
            }else {
                System.out.println("nie znana komenda");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String hashowanie(String haslo) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashedBytes = md.digest(haslo.getBytes());

        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
