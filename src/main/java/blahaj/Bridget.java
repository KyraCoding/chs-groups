package blahaj;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Bridget {
    public ArrayList<Person> read(String filename) {
        ArrayList<Person> people = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Person mizhena = new Person(line);
                people.add(mizhena);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return people;
    }
}
