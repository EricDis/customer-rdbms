//Database Systems (Module IDS) WS2018 UniVie

import java.sql.*;
import java.util.ArrayList;


// The DatabaseHelper class encapsulates the communication with our database
class DatabaseHelper {
    // Database connection info
    private static final String DB_CONNECTION_URL = "jdbc:oracle:thin:@131.130.122.4:1521:lab";
    private static final String USER = "a01505069"; //a + matriculation number
    private static final String PASS = "dbs18"; //password (default: dbs18)

    // The name of the class loaded from the ojdbc14.jar driver file
    private static final String CLASSNAME = "oracle.jdbc.driver.OracleDriver";

    // We need only one Connection and one Statement during the execution => make it a class variable
    private Statement stmt;
    private Connection con;

    //CREATE CONNECTION
    DatabaseHelper() {
        try {
            //Loads the class into the memory
            Class.forName(CLASSNAME);

            // establish connection to database
            con = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASS);
            stmt = con.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //INSERT INTO
    void insertIntoKunde(String firstName, String lastName, String place, int plz, String street, int hnr) {
        try {
            String sql = "INSERT INTO kunde (vorname, nachname, ort, plz, strasse, hausNr) VALUES ('" +
                    firstName +
                    "', '" +
                    lastName +
                    "', '" +
                    place +
                    "', '" +
                    plz +
                    "', '" +
                    street +
                    "', '" +
                    hnr +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoKunde\nmessage: " + e.getMessage());
        }
    }

    void insertIntoPrivaterkunde(String eMail, int kundenID, int rank, String sex) {
        try {
            String sql = "INSERT INTO privaterKunde (eMail, kundenID, rang, gender) VALUES ('" +
            		eMail +
            		"', " +
                    kundenID +
                    ", '" +
                    rank +
                    "', '" +
                    sex +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoPrivaterkunde\nmessage: " + e.getMessage());
        }
    }

    void insertIntoFirmenkunde( int kundenID, String contact, int frank) {
        try {
            String sql = "INSERT INTO firmenKunde (kundenID, fKontakt, fRang) VALUES (" +
                    kundenID +
                    ", '" +
                    contact +
                    "', '" +
                    frank +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoFirmenkunde\nmessage: " + e.getMessage());
        }
    }

    void insertIntoArtikel(String descript, double price, String cat) {
        try {
            String sql = "INSERT INTO artikel (bezeichnung, preis, kategorie) VALUES ('" +
                    descript +
                    "', " +
                    price +
                    " , '" +
                    cat +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoArtikel\nmessage: " + e.getMessage());
        }
    }

    void insertIntoWarenkorb(int anzahl, int kundenID) {
        try {
            String sql = "INSERT INTO warenkorb (kundenID, anzahl) VALUES (" +
                    kundenID +
                    ", '" +
                    anzahl +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoWarenkorb\nmessage: " + e.getMessage());
        }
    }
    
    void insertIntoBestellung(int warenkorbID, int kundenID, int typ, String date) {
        try {
            String sql = "INSERT INTO Bestellung (warenkorbID, kundenID, typ, zeitpunkt) VALUES (" +
                    warenkorbID +
                    ", " +
                    kundenID +
                     ", '" +
                    typ +
                     "', '" +
                    date +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoBestellung\nmessage: " + e.getMessage());
        }
    }
    
    void insertIntoRechnung(int bestellungsID, int steuersatz) {
        try {
            String sql = "INSERT INTO rechnung (bestellungsID, steuersatz) VALUES (" +
                    bestellungsID +
                    ", '" +
                    steuersatz +
                    "')";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error at: insertIntoRechnung\nmessage: " + e.getMessage());
        }
    }


    //SELECT //KundenID
    ArrayList<Integer> selectPersonIsFromPerson() {
        ArrayList<Integer> IDs = new ArrayList<>();

        try {
            ResultSet rs = stmt.executeQuery("SELECT kunde.kundenID FROM kunde ORDER BY kundenID");
            while (rs.next()) {
                IDs.add(rs.getInt("kundenID"));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(("Error at: selectKundenIsFromKunde\n message: " + e.getMessage()).trim());
        }
        return IDs;
    }
    //BestellungsID
    ArrayList<Integer> selectBestellungsIDsFromBestellung() {
        ArrayList<Integer> IDs = new ArrayList<>();

        try {
            ResultSet rs = stmt.executeQuery("SELECT bestellung.bestellungsID FROM bestellung ORDER BY bestellungsID");
            while (rs.next()) {
                IDs.add(rs.getInt("bestellungsID"));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(("Error at: selectBestellungsIDsFromBestellung\n message: " + e.getMessage()).trim());
        }
        return IDs;
    }
    //warenkorbID
    ArrayList<Integer> selectWarenkorbIdFromWarenkorb() {
        ArrayList<Integer> IDs = new ArrayList<>();

        try {
            ResultSet rs = stmt.executeQuery("SELECT warenkorb.warenkorbID FROM warenkorb");
            while (rs.next()) {
                IDs.add(rs.getInt("warenkorbID"));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(("Error at: selectWarenkorbIDFromWarenkorb\n message: " + e.getMessage()).trim());
        }
        return IDs;
    }
    //artNr
    ArrayList<Integer> selectArtNrFromArt() {
        ArrayList<Integer> IDs = new ArrayList<>();

        try {
            ResultSet rs = stmt.executeQuery("SELECT artikel.artNr FROM artikel");
            while (rs.next()) {
                IDs.add(rs.getInt("artikelNr"));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(("Error at: selectArtNrFromArt\n message: " + e.getMessage()).trim());
        }
        return IDs;
    }
/*
    int selectCountAllFromAttend(){
        int count = 0;
        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ATTEND");
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(("Error at: selectCountAllFromAttend\n message: " + e.getMessage()).trim());
        }
        return count;
    }
*/
    public void close()  {
        try {
            stmt.close(); //clean up
            con.close();
        } catch (Exception ignored) {
        }
    }
}
