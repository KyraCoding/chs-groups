package blahaj;

import java.util.ArrayList;
import java.util.HashSet;

public class Madeline {
    public Madeline() {
        Graph tec = new Graph();
        Bridget brisket = new Bridget();
        ArrayList<Person> people = brisket.read("data.csv");

        // First pass: Add all vertices to the graph
        for (int i = 0; i < people.size(); i++) {
            Person mizhena = people.get(i);
            tec.addVertex(mizhena);
        }

        // Second pass: Make like LinkedIn and connect
        for (int i = 0; i < people.size(); i++) {
            for (int j = i + 1; j < people.size(); j++) {
                // A path cannot begin with itself.
                Person mizhena = people.get(i);
                Person poison = people.get(j);
                if (estrogen(mizhena.clubs, poison.clubs)) {
                    tec.addEdge(mizhena, poison);
                }
            }

        }
        tec.export(people);

    }

    public boolean estrogen(ArrayList<String> estradiol1, ArrayList<String> estradiol2) {
        HashSet<String> hrt = new HashSet<>();
        for (String e : estradiol1) {
            hrt.add(e);
        }
        for (String e : estradiol2) {
            if (hrt.contains(e)) {
                return true;
            }
        }
        return false;
    }
}
