//Database Systems (Module IDS) WS2018 UniVie

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

// The RandomHelper class wraps around the JAVA Random class to provide convenient access to random data as we need it
// Additionally it provides access to external single-columned files (e.g. courses.csv, names.csv, surnames.csv)
class RandomHelper {
    private final char[] alphabet = getCharSet();
    private Random rand;
    private ArrayList<String> firstNames;
    private ArrayList<String> lastNames;
    private ArrayList<String> courses;
    private ArrayList<String> categories;
    private ArrayList<String> domains;
    private ArrayList<String> cities;
    private static final String firstNameFile = "./resources/names.csv"; //todo check directory
    private static final String lastNameFile = "./resources/surnames.csv";
    private static final String coursesFile = "./resources/courses.csv";
    private static final String categoriesFile="./resources/categories.csv";
    private static final String domainsFile="./resources/domains.csv";
    private static final String citiesFile="./resources/cities.csv";

    //instantiate the Random object and store data from files in lists
    RandomHelper() {
        this.rand = new Random();
        this.lastNames = readFile(lastNameFile);
        this.firstNames = readFile(firstNameFile);
        this.courses = readFile(coursesFile);
        this.categories = readFile(categoriesFile);
        this.domains = readFile(domainsFile);
        this.cities = readFile(citiesFile);
    }

    //return random string
    String getRandomString(int minLen, int maxLen) {
        StringBuilder out = new StringBuilder();
        int len = rand.nextInt((maxLen - minLen) + 1) + minLen;
        while (out.length() < len) {
            int idx = Math.abs((rand.nextInt() % alphabet.length));
            out.append(alphabet[idx]);
        }
        return out.toString();
    }

    //returns random element from list
    String getRandomFirstName() {
        return firstNames.get(getRandomInteger(0, firstNames.size() - 1));
    }

    //returns random element from list
    String getRandomLastName() {
        return lastNames.get(getRandomInteger(0, lastNames.size() - 1));
    }

    //returns random element from list
    String getRandomCourse() {
        return courses.get(getRandomInteger(0, courses.size() - 1));
    }
    
    //returns random mail
    String getRandomMail() {
    	return getRandomString(1,25)+"@"+getRandomDomain();
    }
    
    //returns random categorie
    String getRandomCategory() {
    	return categories.get(getRandomInteger(0, categories.size() - 1));
    }
    
    //returns eMail domain for the mail generator
    String getRandomDomain() {
    	return domains.get(getRandomInteger(0, domains.size() -1));
    }
    
    //returns random gender
    String getRandomGender() {
    	Random rnd = new Random();
    	String str ="MW";
    	char temp = str.charAt(rnd.nextInt(str.length()));
    	return Character.toString(temp);
    }
    
    //returns random city
    String getRandomCity() {
    	return cities.get(getRandomInteger(0, cities.size() - 1));
    }
    
    //return random streetname
    String getRandomStreet() {
    	return getRandomString(3,20)+"straße";
    }
    
    //return random Contacdata
    String getRandomContact() {
    	return "+00"+getRandomInteger(6,9);
    }
    
    //returns random date
    String getRandomDate()	{
    	return getRandomInteger(1, 31)+"/"+getRandomInteger(1, 12)+"/"+ getRandomInteger(2015, 2019);
    }
    
    //returns random double from the Interval [min, max] and a defined precision (e.g. precision:2 => 3.14)
    Double getRandomDouble(double min, double max, int precision) {
        //Hack that is not the cleanest way to ensure a specific precision, but...
        double r = Math.pow(10, precision);
        return Math.round(min + (rand.nextDouble() * (max - min)) * r) / r;
    }

    //return random Integer from the Interval [min, max]; (min, max are possible as well)
    Integer getRandomInteger(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    //reads single-column files and stores its values as Strings in an ArraList
    private ArrayList<String> readFile(String filename) {
        String line;
        ArrayList<String> set = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                try {
                    set.add(line);
                } catch (Exception ignored) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    //defines which chars are used to create random strings
    private char[] getCharSet() { // create getCharSet char array
        StringBuffer b = new StringBuffer(128);
        for (int i = 48; i <= 57; i++) b.append((char) i);        // 0-9
        for (int i = 65; i <= 90; i++) b.append((char) i);        // A-Z
        for (int i = 97; i <= 122; i++) b.append((char) i);       // a-z
        return b.toString().toCharArray();
    }
}