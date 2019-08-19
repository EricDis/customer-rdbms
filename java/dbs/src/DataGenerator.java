//Database Systems (Module IDS) WS2018 UniVie

import java.util.ArrayList;

public class DataGenerator {
    public static void main(String args[]) {

        RandomHelper rdm = new RandomHelper();
        DatabaseHelper dbHelper = new DatabaseHelper();

        // 1a) Insert Into Kunde
        for (int i = 0; i < 1500; i++) {
            String firstName = rdm.getRandomFirstName();
            String lastName = rdm.getRandomLastName();
            String place = rdm.getRandomCity();
            int plz = rdm.getRandomInteger(10000, 99999);
            String street = rdm.getRandomStreet();
            int hnr = rdm.getRandomInteger(1, 555);
            dbHelper.insertIntoKunde(firstName, lastName, place, plz, street, hnr);
        }

        // 1b) Select KundenIds and store them in List (used to handle foreign keys for Private and Firmenkunden)
        ArrayList<Integer> kundenIDs = dbHelper.selectPersonIsFromPerson();
        System.out.println("Es gibt " + kundenIDs.size() + " Kunden in dieser Datenbank!");
        
       

        // 2a) Insert Into Privaterkunde
        for (int i = 0; i < kundenIDs.size() && i < 1050; i++) { //limit number of Privatekunden to 1050
            String eMail = rdm.getRandomMail();
            int kundenID = kundenIDs.get(i);
            int rank = rdm.getRandomInteger(1, 5);
            String sex = rdm.getRandomGender();
            dbHelper.insertIntoPrivaterkunde(eMail, kundenID, rank, sex);
        }

        // 2b) Insert Into Firmenkunde
        for (int kundenID : kundenIDs) {
            int firmenID = rdm.getRandomInteger(1000000, 9999999);
            String contact = rdm.getRandomContact();
            int frank = rdm.getRandomInteger(1, 3);
            dbHelper.insertIntoFirmenkunde(kundenID, contact, frank);
        }
/*       
        //Insert Into warenkorb
        for (int i = 0; i < kundenIDs.size() && i < 50; i++) { //limit number of warenkorb to 50
            int anzahl = rdm.getRandomInteger(1, 12);
            int kundenID = kundenIDs.get(i);
            dbHelper.insertIntoWarenkorb(kundenID, anzahl);
        }
        
        //select warenkorbIDs and store them in List, to handle foreign keys for bestellung
        ArrayList<Integer> warenkorbIDs = dbHelper.selectWarenkorbIDsFromWarenkorb();
        System.out.println("Es gibt " + warenkorbIDs.size() + " Warenkörbe in dieser Datenbank!");
        
        //Insert Into bestellung
        for (int i = 0; i < kundenIDs.size() && i < 50; i++) { //limit number of bestellung to 50
            int typ = rdm.getRandomInteger(0, 5);
            int kundenID = kundenIDs.get(i);
            int warenkorbID = warenkorbIDs.get(i);
            String date = rdm.getRandomDate();
            dbHelper.insertIntoBestellung(warenkorbID, kundenID, typ, date);
        }
        
        //Insert Into rechnung
        for (int i = 0; i < bestellungsIDs.size() && i < 50; i++) { //limit number of rechnung to 50
            int  = bestellungsIDs.get(i);
            int steuersatz = rdm.getRandomInteger(12, 20);
            dbHelper.insertIntoRechnung(bestellungsID, steuersatz);
        }

        // 3b) Select Firmenkunde_KundenIDs and store them in List (used to handle foreign keys for ATTENDs)
        ArrayList<Integer> kundenIds = dbHelper.selectFirmenkundeIdFromFirmenkunde();
        System.out.println("Es gibt " + kundenIds.size() + " Firmenkunden in dieser Datenbank!");
*/
        // 4a) Insert Into Artikel
        for (int i = 0; i < 300; i++) {
            int artnr = rdm.getRandomInteger(10000, 99999);
            String descript = rdm.getRandomString(4, 15);
            double price = rdm.getRandomDouble(1, 999, 2);
            String cat = rdm.getRandomCategory();
            dbHelper.insertIntoArtikel(descript, price, cat);
        }

        // 4b) Select CourseNo and store them in List (used to handle foreign keys for ATTENDs)
        //ArrayList<Integer> courseNo = dbHelper.selectCourseNumberFromCourse();
        //System.out.println("There are " + courseNo.size() + " courses in our database!");

        // 5a) Insert Into Attend
        //for (int i = 0; i < 4000; i++) {
            //int randomFirmenId = kundenIds.get(rdm.getRandomInteger(0, kundenIds.size() - 1));
            //int randomCourseNo = courseNo.get(rdm.getRandomInteger(0, courseNo.size() - 1));
            //dbHelper.insertIntoAttend(randomFirmenId, randomCourseNo);
        

        //}
        //select bestellungsIDs and store them in List, to handle foreign keys for rechnung
        //ArrayList<Integer> bestellungsIDs = dbHelper.selectBestellungsIDsFromBestellung();
        //System.out.println("Es gibt " + bestellungsIDs.size() + " Bestellungen in dieser Datenbank!");
        // 5b) Select Count(*) From Attend
       // int countAttend = dbHelper.selectCountAllFromAttend();
        System.out.println("finished");

        dbHelper.close();
    }
}
